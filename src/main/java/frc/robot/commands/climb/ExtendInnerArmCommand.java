package frc.robot.commands.climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Log;
import frc.robot.Constants.Climb;
import frc.robot.subsystems.ClimbSubsystem;

public class ExtendInnerArmCommand extends CommandBase {

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final ClimbSubsystem climbSystem;

    public ExtendInnerArmCommand(ClimbSubsystem subsystem) {
        this.climbSystem = subsystem;
        //addRequirements(this.climbSystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        this.climbSystem.getCenterMotor().set(Climb.MAX_BACKWARDS);
        Log.info(Double.toString(this.climbSystem.getCenterMotor().getSelectedSensorPosition()));
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        this.climbSystem.getCenterMotor().set(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
