package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

/*
 * Move not forward and intake and shoot
 */

import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveNotForwardAndIntakeShootCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final DriveSubsystem driveSystem;
    private final ShooterSubsystem shooter;
    private boolean set = false;
    int stage = 0;

    public MoveNotForwardAndIntakeShootCommand(DriveSubsystem subsystem, ShooterSubsystem shooter) {
        this.driveSystem = subsystem;
        this.shooter = shooter;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        this.driveSystem.getFrontLeft().setSelectedSensorPosition(0);
        this.shooter.getFlywheelMotor().setSelectedSensorPosition(0);
        this.shooter.getSolenoid().set(false);
    }


    private final int MOVED = 8000;
    private final int STAGE2MOVED = 1000;
    private boolean stage2Started = false;
    private boolean stage3Started = false;

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (stage == 0) {
            // Move backward and shoot section
            if (Math.abs(this.driveSystem.getFrontLeft().getSelectedSensorPosition()) < MOVED
                    * 2.4) { // was=3
                this.driveSystem.move(-0.7, 0);
            } else {
                shooter.getFlywheelMotor().set(shooter.getCurrentSpeed());
                if (!set) {
                    shooter.getFlywheelMotor().setSelectedSensorPosition(0);
                    set = true;
                }
                this.shooter.getSolenoid().set(true);

                if (set && Math
                        .abs(shooter.getFlywheelMotor().getSelectedSensorPosition()) > 400000) {
                    shooter.getGuideMotor().set(Constants.Shooter.Speed.MEDIUM);
                } else {
                    stage = 1;
                }
            }
        } else if (stage == 1) {
            // Turn left
            if (!this.stage2Started) {
                this.driveSystem.getFrontLeft().setSelectedSensorPosition(0);
                this.driveSystem.getFrontRight().setSelectedSensorPosition(0);
                this.stage2Started = true;
            }
            if (Math.abs(
                    this.driveSystem.getFrontLeft().getSelectedSensorPosition()) < STAGE2MOVED) {
                this.driveSystem.move(0, -0.33);
            } else {
                stage = 3;
            }

        } else if (stage == 3) {
            if (!this.stage3Started) {
                this.driveSystem.getFrontLeft().setSelectedSensorPosition(0);
                this.driveSystem.getFrontRight().setSelectedSensorPosition(0);
                this.stage3Started = true;
            }
        }

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        shooter.getFlywheelMotor().set(0);
        shooter.getGuideMotor().set(0);
        shooter.toggleSpeed();
    }

    // Returns true when the command should end.
    // Stage 4 means that it is finished
    @Override
    public boolean isFinished() {
        return stage == 4;
    }

}
