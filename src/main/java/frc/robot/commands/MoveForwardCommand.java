// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class MoveForwardCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveSubsystem driveSystem;
  private final ShooterSubsystem shooter;

  public MoveForwardCommand(DriveSubsystem subsystem, ShooterSubsystem shooter) {
    this.driveSystem = subsystem;
    this.shooter = shooter;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.driveSystem.getFrontLeft().setSelectedSensorPosition(0);
    this.shooter.getFlywheelMotor().setSelectedSensorPosition(0);
    this.shooter.getSolenoid().set(false);
  }


  private final int MOVED = 8000;

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (Math.abs(this.driveSystem.getFrontLeft().getSelectedSensorPosition()) < MOVED * 2.5) {
      this.driveSystem.move(-0.7, 0);
    } else {
      shooter.getFlywheelMotor().set(shooter.getCurrentSpeed());
      shooter.getFlywheelMotor().setSelectedSensorPosition(0);
      this.shooter.getSolenoid().set(true);

      if (Math.abs(shooter.getFlywheelMotor().getSelectedSensorPosition()) > 2000) {
        shooter.getGuideMotor().set(Constants.Shooter.Speed.MEDIUM);
      }
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.getFlywheelMotor().set(0);
    shooter.getGuideMotor().set(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
