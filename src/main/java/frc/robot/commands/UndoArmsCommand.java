package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.Climb;
import frc.robot.subsystems.ClimbSubsystem;

public class UndoArmsCommand extends CommandBase {

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final ClimbSubsystem climbSystem;

    public UndoArmsCommand(ClimbSubsystem subsystem) {
        this.climbSystem = subsystem;
        addRequirements(this.climbSystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        this.climbSystem.getLeftMotor().setSelectedSensorPosition(0);
        this.climbSystem.getRightMotor().setSelectedSensorPosition(0);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        this.climbSystem.getLeftMotor().set(Climb.MAX_FORWARDS / 2);
        this.climbSystem.getRightMotor().set(Climb.MAX_FORWARDS / 2);

        System.out.print(this.climbSystem.getLeftMotor().getSelectedSensorPosition() + " ");
        System.out.println(this.climbSystem.getRightMotor().getSelectedSensorPosition());
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
        return Math.abs(this.climbSystem.getLeftMotor().getSelectedSensorPosition()) > 900
                || Math.abs(this.climbSystem.getRightMotor().getSelectedSensorPosition()) > 900;
    }
}
