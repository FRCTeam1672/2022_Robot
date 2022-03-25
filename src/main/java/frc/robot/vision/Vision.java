package frc.robot.vision;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.vision.VisionThread;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Log;
import frc.robot.subsystems.DriveSubsystem;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

import static frc.robot.Constants.Vision.*;

/**
 * Handles the entire vision system
 * <p>
 * https://docs.wpilib.org/en/stable/docs/software/vision-processing/grip/using-generated-code-in-a-robot-program.html
 *
 * @author TJ and Ishaan
 */
public class Vision {

    private final UsbCamera camera;
    private final DriveSubsystem driveSubsystem;
    private GripPipeline visionPipeline;
    private double centerX = 0.0;
    private final Object imgLock = new Object();

    public Vision(UsbCamera camera, DriveSubsystem driveSubsystem) {
        this.camera = camera;
        this.driveSubsystem = driveSubsystem;
        this.visionPipeline = new GripPipeline();
        this.initVisionSystem();
    }

    /**
     * Init the vision system Some tasks that are handled include: - Putting the stream of the
     * camera on Shuffleboard - Creating our images and handling them using {@link VisionPipeline} -
     * Putting red rectangle - Updating current location of the center of the reflective tape
     */
    private void initVisionSystem() {

        //visionPipeline = new VisionPipeline();
        CvSink cvSink = CameraServer.getVideo(camera);
        if (!cvSink.isValid()) {
            Log.error("[VISION] Unable to validate the OpenCV video stream (cvSink). Please alert a programmer about this");
            return;
        }


        CvSource outputStream = CameraServer.putVideo("Vision Pipeline Camera", CAMERA_IMG_WIDTH,
                CAMERA_IMG_HEIGHT);

        // Reuse the same mat because creating a new one is rly costly
        Mat mat = new Mat();
        Scalar color = new Scalar(0, 0, 255);
        VisionThread visionThread = new VisionThread(camera, visionPipeline, pipeline -> {
            // If we cannot grab a frame, return and send an error message
            if (cvSink.grabFrame(mat) == 0) {
                Log.error("[VISION] Could not pull frame from the OpenCV Sink. Please alert a programmer about this");
                return;
            }
            // Copies the old mat, so we can show the rectangle on it for the driver and does not
            // interfere with the pipeline
            Mat cloneMat = mat.clone();

            if (!pipeline.filterContoursOutput().isEmpty()) {
                final ArrayList<Double> rectangleCenters = new ArrayList<>();
                final ArrayList<Double> rectangleYs = new ArrayList<>();
                pipeline.filterContoursOutput().forEach(matOfPoint -> {
                    Rect r = Imgproc.boundingRect(matOfPoint);
                    synchronized (imgLock) {
                        Log.debug(r.y+" ");
                        double recCenter = r.x + r.width / 2.0;
                        if(r.y>CONTOUR_MIN_Y && r.y<CONTOUR_MAX_Y) {
                            rectangleCenters.add(recCenter);
                            rectangleYs.add(r.y + r.height / 2.0);
                        }
                    }
                });
                for(int i=0; i<rectangleCenters.size(); i++) {
                    double recCenter = rectangleCenters.get(i);
                    double y = rectangleYs.get(i);
                    Point point1 = new Point(recCenter - 2, y - 2);
                    Point point2 = new Point(recCenter + 2, y + 2);
                    Imgproc.rectangle(cloneMat, point1, point2, color, 2);
                }

                System.out.println();
                double averageCenter = VisionMathUtils.getAverage(rectangleCenters);
                synchronized (imgLock) {
                    centerX = averageCenter;
                }
                double y = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0)).y;
                Point point1 = new Point(averageCenter - 5, y - 5);
                Point point2 = new Point(averageCenter + 5, y + 5);
                Imgproc.rectangle(cloneMat, point1, point2, new Scalar(255, 0, 0), 2);
            }
            // If there is no contour detected (pipeline doesn't see anything, we set the centerX to 0.0
            // We also reset the mat to show the current stream so the rectangle goes away
            else { 
                centerX = 0.0;
                cloneMat = mat;
            }
            outputStream.putFrame(cloneMat);
        });
        visionThread.start();
        Log.info("Vision thread created, and has been started.");
        Log.info("Vision configuration finished");
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
     * <p>
     * The turn value will also be placed on Shuffleboard for the driver (may change)
     * <p>
     * If the returned value is 0.0, then assume that the reflective tape was not found. In reality,
     * the turn amount would never be perfectly 0.0.
     *
     * @return The turn
     */
    public double getTurnAmount() {
        SmartDashboard.putNumber("X Diff", centerX - CAMERA_IMG_WIDTH/2);

        double turnAmount = 0.0;
        if (centerX == 0) {
            return turnAmount;
        }
        turnAmount = VisionMathUtils.pixelToRealWorld(centerX);
        SmartDashboard.putNumber("Vision Turn Amount ", turnAmount);
        return turnAmount;
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
        //driveSubsystem.move(0, turnAmount);
    }
}
