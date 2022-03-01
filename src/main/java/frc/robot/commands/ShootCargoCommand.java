package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootCargoCommand extends CommandBase {
    private final ShooterSubsystem shooter;
    private boolean aimAssist = true;

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

    public void toggleAimAssist() {
        if (aimAssist) {
            aimAssist = false;
        } else {
            aimAssist = true;
        }
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (aimAssist) {

        }
        shooter.getFlywheelMotor().set(Constants.Shooter.Speed.HIGH);
        System.out.println(shooter.getFlywheelMotor().getSelectedSensorPosition());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        shooter.getFlywheelMotor().set(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
