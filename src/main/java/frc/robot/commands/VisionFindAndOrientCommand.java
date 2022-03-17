package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.vision.Vision;

public class VisionFindAndOrientCommand extends CommandBase {
    private final Vision vision;
    private final DriveSubsystem driveSubsystem;

    public VisionFindAndOrientCommand(Vision vision, DriveSubsystem driveSubsystem) {
        this.vision = vision;
        this.driveSubsystem = driveSubsystem;
        addRequirements(driveSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        driveSubsystem.move(0,0);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        vision.findAndOrient();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        driveSubsystem.move(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        double centerX = vision.getCenterX();
        return centerX >= -20 && centerX <= 20;
    }
}
