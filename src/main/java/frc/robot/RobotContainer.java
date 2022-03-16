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
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.vision.Vision;

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

  private final MoveBackwardAutoCommand moveForwardCommand =
      new MoveBackwardAutoCommand(driveSubsystem, shooterSubsystem);

  private final ShootLowIntakeShootCommand shootLowCommand =
      new ShootLowIntakeShootCommand(driveSubsystem, shooterSubsystem);

  private final UnclogCargoCommand unclogCargoCommand = new UnclogCargoCommand(shooterSubsystem);

  private final PneumaticsControlModule pcm = new PneumaticsControlModule(0);

  private final XboxController controller = new XboxController(0);
  private final XboxController hangController = new XboxController(1);

  private Vision vision;
  private SendableChooser<CommandBase> autoChooser;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    configureVision();

    autoChooser = new SendableChooser<>();

    // add SendableChooser for auto command to run
    autoChooser.addOption("Back + High", moveForwardCommand);
    autoChooser.setDefaultOption("Shoot Low, Intake", shootLowCommand);

    SmartDashboard.putString("Shooter Speed", shooterSubsystem.getShooterSpeed());
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureVision() {
    UsbCamera camera1 = CameraServer.startAutomaticCapture();
    UsbCamera camera2 = CameraServer.startAutomaticCapture();
    camera2.setResolution(360, 240);
    camera2.setFPS(15);
    // camera2.setBrightness(40);
    camera2.setExposureManual(30);
    vision = new Vision(camera2, driveSubsystem);
  }

  private void configureButtonBindings() {
    // Configure Button Bindings Here
    // controls.bindButtonHeld(Buttons.P1_RB, shootCargoCommand);
    // controls.bindButtonHeld(Buttons.P1_LB, intakeCargoCommand);
    // controls.bindButtonHeld(Buttons.P1_A, toggleIntakeCommand);

    // controls.bindButton(Buttons.P2_LB, InputType.PRESSED,
    // () -> climbSubsystem.runNextCommand(false));
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

  public void teleopPeriodic() {
    if (controller.getLeftBumper())
      intakeCargoCommand.execute();
    if (controller.getLeftBumperReleased())
      intakeCargoCommand.end(false);

    if (controller.getRightBumperPressed())
      shootCargoCommand.initialize();
    if (controller.getRightBumper())
      shootCargoCommand.execute();
    if (controller.getRightBumperReleased())
      shootCargoCommand.end(false);

    if (controller.getYButton())
      unclogCargoCommand.execute();
    if (controller.getYButtonReleased())
      unclogCargoCommand.end(false);

    if (controller.getXButton()) {
      vision.findAndOrient();
    }

    // if (controller.getAButtonPressed()) {
    // climbSubsystem.runNextCommand(false);
    // }

    if (controller.getBButtonPressed()) {
      toggleIntakeCommand.execute();
    }

    if (hangController.getStartButtonPressed()) {
      shooterSubsystem.toggleSpeed();
    }

    if (controller.getRightStickButtonPressed()) {
      driveSubsystem.toggleDirection();
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
