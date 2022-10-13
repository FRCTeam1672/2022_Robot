package frc.robot.commands.climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbSubsystem;

import static frc.robot.Constants.Climb.RETRACT_SPEED;

public class RetractRightArmCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final ClimbSubsystem climbSystem;

    public RetractRightArmCommand(ClimbSubsystem subsystem) {
        this.climbSystem = subsystem;
        //addRequirements(this.climbSystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        this.climbSystem.getRightMotor().setSelectedSensorPosition(0);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        this.climbSystem.getRightMotor().set(RETRACT_SPEED);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        this.climbSystem.getRightMotor().set(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
