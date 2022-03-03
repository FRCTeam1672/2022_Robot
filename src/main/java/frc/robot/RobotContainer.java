// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.Shooter;
import frc.robot.Controls.Buttons;
import frc.robot.Controls.InputType;
import frc.robot.commands.MoveForwardCommand;
import frc.robot.commands.RetractOuterArmsCommand;
import frc.robot.commands.ShootCargoCommand;
import frc.robot.commands.ToggleIntakeCommand;
import frc.robot.commands.UnclogCargoCommand;
import frc.robot.commands.ExtendArmsCommand;
import frc.robot.commands.IntakeCargoCommand;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

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
  private final RetractOuterArmsCommand retractOuterArmsCommand =
      new RetractOuterArmsCommand(climbSubsystem);

  private final MoveForwardCommand moveForwardCommand = new MoveForwardCommand(driveSubsystem);

  private final UnclogCargoCommand unclogCargoCommand = new UnclogCargoCommand(shooterSubsystem);

  private final Controls controls = new Controls();

  private final PneumaticsControlModule pcm = new PneumaticsControlModule(0);

  private final XboxController controller = new XboxController(0);
  private final XboxController hangController = new XboxController(1);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    SmartDashboard.putNumber("Solenoids", pcm.getSolenoids());
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
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

    // if (controller.getAButtonPressed()) {
    // climbSubsystem.runNextCommand(false);
    // }

    if (controller.getBButtonPressed()) {
      toggleIntakeCommand.execute();
    }

    if (hangController.getAButtonPressed()) {
      extendArmsCommand.schedule();
    }

    if (hangController.getBButtonPressed()) {
      retractOuterArmsCommand.schedule();
    }
  }
}
