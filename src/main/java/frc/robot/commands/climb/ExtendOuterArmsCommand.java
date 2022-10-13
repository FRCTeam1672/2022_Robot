package frc.robot.commands.climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.Climb;
import frc.robot.Log;
import frc.robot.subsystems.ClimbSubsystem;

public class ExtendOuterArmsCommand extends CommandBase {

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final ClimbSubsystem climbSystem;

    public ExtendOuterArmsCommand(ClimbSubsystem subsystem) {
        this.climbSystem = subsystem;
        //addRequirements(this.climbSystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        this.climbSystem.getLeftMotor().setSelectedSensorPosition(0);
        this.climbSystem.getRightMotor().setSelectedSensorPosition(0);
        this.climbSystem.getCenterSolenoid().set(false);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        this.climbSystem.getLeftMotor().set(Climb.MAX_FORWARDS * 0.66);
        this.climbSystem.getRightMotor().set(Climb.MAX_FORWARDS * 0.66);

        Log.debug("Climb Subsystem Left Motor: " + this.climbSystem.getLeftMotor().getSelectedSensorPosition()
                + "Climb Subsystem Right Motor: " + this.climbSystem.getRightMotor().getSelectedSensorPosition());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        this.climbSystem.getLeftMotor().set(0);
        this.climbSystem.getRightMotor().set(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Math
                .abs(this.climbSystem.getLeftMotor()
                        .getSelectedSensorPosition()) > Climb.OUTER_CLIMB_REVS
                || Math.abs(this.climbSystem.getRightMotor()
                .getSelectedSensorPosition()) > Climb.OUTER_CLIMB_REVS;
    }
}
