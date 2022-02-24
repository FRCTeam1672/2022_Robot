package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.Climb;
import frc.robot.subsystems.ClimbSubsystem;

public class RetractOuterArmsCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final ClimbSubsystem climbSystem;

    public RetractOuterArmsCommand(ClimbSubsystem subsystem) {
        this.climbSystem = subsystem;
        addRequirements(this.climbSystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        this.climbSystem.getLeftMotor().set(Climb.MAX_FORWARDS);
        this.climbSystem.getRightMotor().set(Climb.MAX_FORWARDS);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        this.climbSystem.getLeftMotor().set(Climb.ZERO);
        this.climbSystem.getRightMotor().set(Climb.ZERO);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
