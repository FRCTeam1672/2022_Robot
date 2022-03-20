package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

import static frc.robot.Constants.Shooter.Speed.GUIDE;
import static frc.robot.Constants.Shooter.Speed.INTAKE;

public class IntakeCargoCommand extends CommandBase {
    private final ShooterSubsystem shooter;

    public IntakeCargoCommand(ShooterSubsystem subsystem) {
        this.shooter = subsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        shooter.getIntakeMotor().set(INTAKE);
        shooter.getGuideMotor().set(GUIDE);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        shooter.getIntakeMotor().set(0);
        shooter.getGuideMotor().set(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
