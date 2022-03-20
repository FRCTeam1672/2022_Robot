package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Log;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootCargoCommand extends CommandBase {
    private final ShooterSubsystem shooter;

    public ShootCargoCommand(ShooterSubsystem subsystem) {
        this.shooter = subsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        shooter.getFlywheelMotor().setSelectedSensorPosition(0);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        /*
         if (shooter.getFlywheelMotor().getSelectedSensorPosition() < -4096 * 15)
         shooter.getGuideMotor().set(Constants.Shooter.Speed.HIGH);
        */
        shooter.getFlywheelMotor().set(shooter.getCurrentSpeed());
        Log.debug("Shooter speed: " + shooter.getFlywheelMotor().getSelectedSensorVelocity());
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
