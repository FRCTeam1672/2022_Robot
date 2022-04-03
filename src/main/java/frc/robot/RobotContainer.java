// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.auto.MoveBackwardAutoCommand;
import frc.robot.commands.auto.VisionFindAndOrientCommand;
import frc.robot.commands.climb.*;
import frc.robot.commands.shooter.*;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.vision.Vision;

import static frc.robot.Constants.Controller.ControllerType.CLIMB;
import static frc.robot.Constants.Controller.ControllerType.DRIVE;
import static frc.robot.Constants.Controller.Joystick.*;
import static frc.robot.Constants.Vision.CAMERA_IMG_HEIGHT;
import static frc.robot.Constants.Vision.CAMERA_IMG_WIDTH;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
    private final ClimbSubsystem climbSubsystem = new ClimbSubsystem();
    private final DriveSubsystem driveSubsystem = new DriveSubsystem();

    private final ToggleIntakeCommand toggleIntakeCommand = new ToggleIntakeCommand(shooterSubsystem);
    private final ShootCargoCommand shootCargoCommand = new ShootCargoCommand(shooterSubsystem);
    private final IntakeCargoCommand intakeCargoCommand = new IntakeCargoCommand(shooterSubsystem);
    private final UnclogCargoCommand unclogCargoCommand = new UnclogCargoCommand(shooterSubsystem);

    private final ExtendInnerArmCommand extendInnerArmCommand = new ExtendInnerArmCommand(climbSubsystem);
    private final ExtendOuterArmsCommand extendArmsCommand = new ExtendOuterArmsCommand(climbSubsystem);

    private final UndoInnerArmCommand undoInnerArmCommand = new UndoInnerArmCommand(climbSubsystem);
    private final UndoOuterArmsCommand undoOuterArmsCommand = new UndoOuterArmsCommand(climbSubsystem);

    private final UnlockInnerArmCommand unlockInnerArmCommand = new UnlockInnerArmCommand(climbSubsystem);
    private final UnlockOuterArmsCommand unlockOuterArmsCommand = new UnlockOuterArmsCommand(climbSubsystem);

    private final RetractInnerArmCommand retractInnerArmCommand = new RetractInnerArmCommand(climbSubsystem);
    private final RetractLeftArmCommand retractLeftArmCommand = new RetractLeftArmCommand(climbSubsystem);
    private final RetractRightArmCommand retractRightArmCommand = new RetractRightArmCommand(climbSubsystem);

    private final MoveBackwardAutoCommand moveForwardCommand = new MoveBackwardAutoCommand(driveSubsystem, shooterSubsystem);
    private final ShootLowIntakeShootCommand shootLowCommand = new ShootLowIntakeShootCommand(driveSubsystem, shooterSubsystem);

    //private final PneumaticsControlModule pcm = new PneumaticsControlModule(0);

    private final XboxController shootController = new XboxController(DRIVE.ordinal());
    private final XboxController hangController = new XboxController(CLIMB.ordinal());

    private Vision vision;
    private VisionFindAndOrientCommand visionFindAndOrientCommand;

    private final SendableChooser<CommandBase> autoChooser;

    private final Controls controls;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        long startTime = System.currentTimeMillis();
        Log.info("Started robot container");

        controls = new Controls(shootController, hangController);
        configureVision();
        configureButtonBindings();


        autoChooser = new SendableChooser<>();

        // add SendableChooser for auto command to run
        autoChooser.setDefaultOption("Back + High", moveForwardCommand);
        autoChooser.addOption("Shoot Low, Intake", shootLowCommand);

        SmartDashboard.putString("Shooter Speed", shooterSubsystem.getShooterSpeed());
        long currentTime = System.currentTimeMillis();
        long duration = currentTime - startTime;

        Log.info("Finished initialization for the RobotContainer class. Took " + duration + " ms to initialize.");
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses
     * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
     * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureVision() {
        Log.info("Starting Vision configuration");
        //Camera 1
        CameraServer.startAutomaticCapture();
        UsbCamera camera2 = CameraServer.startAutomaticCapture();
        camera2.setResolution(CAMERA_IMG_WIDTH, CAMERA_IMG_HEIGHT);
        camera2.setFPS(15);
        // camera2.setBrightness(40);
        camera2.setExposureManual(30);
        vision = new Vision(camera2, driveSubsystem);

        visionFindAndOrientCommand = new VisionFindAndOrientCommand(vision, driveSubsystem);
        //Vision config finished message is sent by Vision#initVisionSystem
    }

    private void configureButtonBindings() {
        //Configure Button Bindings Here
        Log.info("Starting configuration for button bindings. ");


        //Start binding drive buttons
        //Bumper buttons
        controls.bindButton(DRIVE, LB_BUTTON, intakeCargoCommand, true);
        controls.bindButton(DRIVE, RB_BUTTON, shootCargoCommand, true);

        //Multicolored buttons
        controls.bindButton(DRIVE, Y_BUTTON, unclogCargoCommand, true);
        controls.bindButton(DRIVE, B_BUTTON, toggleIntakeCommand, false);
        //A BUTTON HAS NO BINDING ON DRIVE CONTROLLER. Most likely to be a toggle mode button
        controls.bindButton(DRIVE, X_BUTTON, visionFindAndOrientCommand, true);

        //Stick buttons
        controls.bindButton(DRIVE, RIGHT_STICK_BUTTON, driveSubsystem::toggleDirection, false);

        //Dpad buttons for the high climb
        controls.bindPOVButton(DRIVE, DPAD_UP, extendInnerArmCommand, true);
        controls.bindPOVButton(DRIVE, DPAD_DOWN, retractInnerArmCommand, true);


        //Start climb controls
        controls.bindButton(CLIMB, LB_BUTTON, retractLeftArmCommand, true);
        controls.bindButton(CLIMB, RB_BUTTON, retractRightArmCommand, true);

        //Mutlicolored buttons
        controls.bindButton(CLIMB, Y_BUTTON, () -> driveSubsystem.changeSpeed(0.1), false);
        controls.bindButton(CLIMB, X_BUTTON, () -> driveSubsystem.changeSpeed(-0.1), false);
        controls.bindButton(CLIMB, A_BUTTON, extendArmsCommand, true);
        //B button has NO command

        controls.bindButton(CLIMB, BACK_BUTTON, undoOuterArmsCommand, true);
        controls.bindButton(CLIMB, START_BUTTON, shooterSubsystem::toggleSpeed, false);

        //Dpad buttons for the mid-climb
        controls.bindPOVButton(CLIMB, DPAD_UP, unlockInnerArmCommand, true);
        controls.bindPOVButton(CLIMB, DPAD_DOWN, unlockOuterArmsCommand, true);

        Log.info("Finished configuration for button bindings. ");
    }

    public Command getAutonomousCommand() {
        return moveForwardCommand;
    }
}

