package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

/*
 * shoot into low hub, move back, intake field cargo, shoot high/low
 */
public class ShootLowIntakeShootCommand extends CommandBase {

    public ShootLowIntakeShootCommand(DriveSubsystem driveSubsystem,
            ShooterSubsystem shooterSubsystem) {}

}
