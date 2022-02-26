package frc.robot.commands;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ShooterSubsystem;

public class IntakeCargoCommand extends CommandBase {
    private final ShooterSubsystem shooter;
    private final Constants constants;
    private final Solenoid solenoid;

    public IntakeCargoCommand(ShooterSubsystem subsystem) {
        this.shooter = subsystem;
        this.solenoid = shooter.getSolenoid();
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
        constants = new Constants();
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        shooter.getIntakeMotor().set(Constants.Shooter.Speed.MEDIUM);
        shooter.getGuideMotor().set(Constants.Shooter.Speed.MEDIUM);
        solenoid.toggle();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        shooter.getIntakeMotor().set(0);
        shooter.getGuideMotor().set(0);
        solenoid.toggle();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
