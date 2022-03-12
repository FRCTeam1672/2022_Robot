package frc.robot.vision;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.vision.VisionThread;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.DriveSubsystem;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

import static frc.robot.Constants.Vision.CAMERA_IMG_HEIGHT;
import static frc.robot.Constants.Vision.CAMERA_IMG_WIDTH;

/**
 * Handles the entire vision system
 *
 * https://docs.wpilib.org/en/stable/docs/software/vision-processing/grip/using-generated-code-in-a-robot-program.html
 * 
 * @author TJ & Ishaan
 */
public class Vision {

    private final UsbCamera camera;
    private final DriveSubsystem driveSubsystem;
    private VisionPipeline visionPipeline;
    private double centerX = 0.0;
    private final Object imgLock = new Object();

    public Vision(UsbCamera camera, DriveSubsystem driveSubsystem) {
        this.camera = camera;
        this.driveSubsystem = driveSubsystem;
        this.visionPipeline = new VisionPipeline();
        this.initVisionSystem();
    }

    /**
     * Init the vision system Some tasks that are handled include: - Putting the stream of the
     * camera on Shuffleboard - Creating our images and handling them using {@link VisionPipeline} -
     * Putting red rectangle - Updating current location of the center of the reflective tape
     */
    private void initVisionSystem() {
        visionPipeline = new VisionPipeline();
        CvSink cvSink = CameraServer.getVideo(camera);
        CvSource outputStream = CameraServer.putVideo("Vision Pipeline Camera", CAMERA_IMG_WIDTH,
                CAMERA_IMG_HEIGHT);

        // Reuse the same mat because creating a new one is rly costly
        Mat mat = new Mat();

        VisionThread visionThread = new VisionThread(camera, visionPipeline, pipeline -> {
            // If we cannot grab a frame, return and send an error message
            if (cvSink.grabFrame(mat) == 0) {
                System.out.println(
                        "<!> [VISION SYSTEM] Could not pull frame from the CameraServer (cvSink) <!>");
                return;
            }
            // Copies the old mat, so we can show the rectangle on it for the driver and does not
            // interfere with the pipeline
            Mat cloneMat = mat.clone();

            if (!pipeline.filterContoursOutput().isEmpty()) {
                final ArrayList<Double> rectangleCenters = new ArrayList<>();
                pipeline.filterContoursOutput().forEach(matOfPoint -> {
                    Rect r = Imgproc.boundingRect(matOfPoint);
                    synchronized (imgLock){
                        double recCenter = r.x + r.width / 2.0;
                        rectangleCenters.add(recCenter);
                    }
                });
                double averageCenter = Math.getDeviationWeightedAverage(rectangleCenters);
                synchronized (imgLock){
                    centerX = averageCenter;
                }
                //CANNOT PUT POINT ONTO THE SMART DASHBOARD
                //RIP
            }
            // If there is no contour detected (pipeline doesn't see anything, we set the centerX to
            // 0.0
            // We also reset the mat to show the current stream so the rectangle goes away
            else {
                centerX = 0.0;
                cloneMat = mat;
            }
            outputStream.putFrame(cloneMat);
        });
        visionThread.start();
    }

    /**
     * This method returns the currently detected centerX. This is calculated to be the center of
     * the reflective tape. This is purely meant to be utility for support because we calculate the
     * turning for you already.
     * 
     * @return The center X
     */
    @Deprecated
    public double getCenterX() {
        return centerX;
    }

    /**
     * This will return a double which is used in {@link DriveSubsystem#move(double, double)}, and
     * should be used for the 'z' direction. (ex) move(0, getTurnAmount());
     *
     * The turn value will also be placed on Shuffleboard for the driver (may change)
     *
     * If the returned value is 0.0, then assume that the reflective tape was not found. In reality,
     * the turn amount would never be perfectly 0.0.
     * 
     * @return The turn
     */
    public double getTurnAmount() {
        double turnAmount = 0.0;
        if (centerX == 0) {
            return turnAmount;
        }
        double turn = centerX - (CAMERA_IMG_WIDTH / 2.0);
        double final_turn = (turn * 0.0051) + 0.00075;

        SmartDashboard.putNumber("turn", turn);
        SmartDashboard.putNumber("turn_final", final_turn);

        return final_turn;
    }

    /**
     * Uses the {@link Vision#getTurnAmount()} method, and executes it for you.
     */
    public void findAndOrient() {
        double turnAmount = getTurnAmount();
        if (turnAmount == 0.0) {
            driveSubsystem.move(0, 0);
            return;
        }
        driveSubsystem.move(0, turnAmount);
    }
}
