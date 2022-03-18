// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.auto.MoveBackwardAutoCommand;
import frc.robot.commands.auto.VisionFindAndOrientCommand;
import frc.robot.commands.climb.ExtendArmsCommand;
import frc.robot.commands.climb.RetractInnerArmCommand;
import frc.robot.commands.climb.RetractLeftArmCommand;
import frc.robot.commands.climb.RetractRightArmCommand;
import frc.robot.commands.climb.UndoArmsCommand;
import frc.robot.commands.shooter.*;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.vision.Vision;

import static frc.robot.Constants.Controller.Joystick.*;
import static frc.robot.enums.ControllerType.CLIMB;
import static frc.robot.enums.ControllerType.DRIVE;

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

  private final ExtendArmsCommand extendArmsCommand = new ExtendArmsCommand(climbSubsystem);
  private final UndoArmsCommand undoArmsCommand = new UndoArmsCommand(climbSubsystem);
  private final RetractInnerArmCommand retractInnerArmCommand =
      new RetractInnerArmCommand(climbSubsystem);
  private final RetractLeftArmCommand retractLeftArmCommand = new RetractLeftArmCommand(climbSubsystem);
  private final RetractRightArmCommand retractRightArmCommand = new RetractRightArmCommand(climbSubsystem);

  private final MoveBackwardAutoCommand moveForwardCommand =
      new MoveBackwardAutoCommand(driveSubsystem, shooterSubsystem);

  private final ShootLowIntakeShootCommand shootLowCommand =
      new ShootLowIntakeShootCommand(driveSubsystem, shooterSubsystem);

  private final UnclogCargoCommand unclogCargoCommand = new UnclogCargoCommand(shooterSubsystem);

  private final PneumaticsControlModule pcm = new PneumaticsControlModule(0);

  private final XboxController shootController = new XboxController(0);
  private final XboxController hangController = new XboxController(1);

  private Vision vision;
  private final VisionFindAndOrientCommand visionFindAndOrientCommand = new VisionFindAndOrientCommand(vision, driveSubsystem);

  private SendableChooser<CommandBase> autoChooser;

  private final Controls controls;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    long startTime = System.currentTimeMillis();
    Log.info("Started robot container");

    controls = new Controls(shootController, hangController);
    // Configure the button bindings
    configureButtonBindings();
    configureVision();

    autoChooser = new SendableChooser<>();

    // add SendableChooser for auto command to run
    autoChooser.addOption("Back + High", moveForwardCommand);
    autoChooser.setDefaultOption("Shoot Low, Intake", shootLowCommand);

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
    camera2.setResolution(360, 240);
    camera2.setFPS(15);
    // camera2.setBrightness(40);
    camera2.setExposureManual(30);
    vision = new Vision(camera2, driveSubsystem);
    //Vision config finished message is sent by Vision#initVisionSystem
  }

  private void configureButtonBindings() {
    //Configure Button Bindings Here
    Log.info("Starting configuration for button bindings. ");
    Log.info("NOTICE: If you see any warnings for button bindings using runnable, you can ignore it, but do bug programming to move to the WPILib commands system.");

    //Bind drive buttons first
    controls.bindButton(DRIVE, LB_BUTTON, intakeCargoCommand, null);
    controls.bindButton(DRIVE, RB_BUTTON, shootCargoCommand, null);
    controls.bindButton(DRIVE, Y_BUTTON, unclogCargoCommand, null);
    controls.bindButton(DRIVE, X_BUTTON, visionFindAndOrientCommand, null);
    controls.bindButton(DRIVE, B_BUTTON, toggleIntakeCommand, null);
    controls.bindButton(DRIVE, RIGHT_STICK_BUTTON, driveSubsystem::toggleDirection, null);

    //Do shooter button now
    controls.bindButton(CLIMB, START_BUTTON, shooterSubsystem::toggleSpeed, null);
    controls.bindButton(CLIMB, A_BUTTON, extendArmsCommand, null);
    controls.bindButton(CLIMB, B_BUTTON, retractInnerArmCommand, null);
    controls.bindButton(CLIMB, BACK_BUTTON, undoArmsCommand, null);
    controls.bindButton(CLIMB, LB_BUTTON, retractLeftArmCommand, null);
    controls.bindButton(CLIMB, RB_BUTTON, retractRightArmCommand, null);
    controls.bindButton(CLIMB, START_BUTTON, () -> driveSubsystem.changeSpeed(0.1), null);
    controls.bindButton(CLIMB, START_BUTTON, () -> driveSubsystem.changeSpeed(-0.1), null);
    Log.info("Finished configuration for button bindings. ");
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return moveForwardCommand;
  }

  /**
   * This method will never run
   */
  @Deprecated
  public void teleopPeriodic() {
    if(true) return;
    Log.error("Old controls system called (RobotContainer#teleopPeriodic). The new system should be used instead.");
    if (shootController.getLeftBumper())
      intakeCargoCommand.execute();
    if (shootController.getLeftBumperReleased())
      intakeCargoCommand.end(false);

    if (shootController.getRightBumperPressed())
      shootCargoCommand.initialize();
    if (shootController.getRightBumper())
      shootCargoCommand.execute();
    if (shootController.getRightBumperReleased())
      shootCargoCommand.end(false);

    if (shootController.getYButton())
      unclogCargoCommand.execute();
    if (shootController.getYButtonReleased())
      unclogCargoCommand.end(false);

    if (shootController.getXButton()) {
      vision.findAndOrient();
    }

    // if (controller.getAButtonPressed()) {
    // climbSubsystem.runNextCommand(false);
    // }

    if (shootController.getBButtonPressed()) {
      toggleIntakeCommand.execute();
    }
    if (shootController.getRightStickButtonPressed()) {
      driveSubsystem.toggleDirection();
    }

//Hang controller start
    if (hangController.getStartButtonPressed()) {
      shooterSubsystem.toggleSpeed();
    }



    if (hangController.getAButtonPressed()) {
      extendArmsCommand.schedule();
    }

    if (hangController.getBButtonPressed()) {
      retractInnerArmCommand.schedule();
    }

    if (hangController.getBackButtonPressed()) {
      undoArmsCommand.schedule();
    }

    if (hangController.getLeftBumper()) {
      climbSubsystem.getLeftMotor().set(0.45);
    }
    if (hangController.getLeftBumperReleased()) {
      climbSubsystem.getLeftMotor().set(0);
    }

    if (hangController.getRightBumper()) {
      climbSubsystem.getRightMotor().set(0.45);
    }
    if (hangController.getRightBumperReleased()) {
      climbSubsystem.getRightMotor().set(0);
    }

    if (hangController.getLeftBumperPressed() || hangController.getRightBumperPressed()) {
      // climbSubsystem.getCenterSolenoid().set(true);
    }

    if (hangController.getXButton()) {
      driveSubsystem.changeSpeed(0.1);
    }
    if (hangController.getYButton()) {
      driveSubsystem.changeSpeed(-0.1);
    }
  }
}
