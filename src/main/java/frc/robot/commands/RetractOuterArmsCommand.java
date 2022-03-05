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
    public void initialize() {
        // NO MIDDLE this.climbSystem.getCenterSolenoid().toggle();
        this.climbSystem.getLeftMotor().setSelectedSensorPosition(0);
        this.climbSystem.getRightMotor().setSelectedSensorPosition(0);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        boolean left = !(Math.abs(this.climbSystem.getLeftMotor()
                .getSelectedSensorPosition()) > Climb.OUTER_CLIMB_REVS + 850);

        boolean right = !(Math.abs(this.climbSystem.getRightMotor()
                .getSelectedSensorPosition()) > Climb.OUTER_CLIMB_REVS + 1000);

        this.climbSystem.getLeftMotor().set(left ? Climb.MAX_BACKWARDS * 0.5 : 0);
        this.climbSystem.getRightMotor().set(right ? Climb.MAX_BACKWARDS * 0.5 : 0);
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
        return Math
                .abs(this.climbSystem.getLeftMotor()
                        .getSelectedSensorPosition()) > Climb.OUTER_CLIMB_REVS + 850
                && Math.abs(this.climbSystem.getRightMotor()
                        .getSelectedSensorPosition()) > Climb.OUTER_CLIMB_REVS + 1000;
    }
}
