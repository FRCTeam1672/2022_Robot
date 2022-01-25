package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
  private VictorSP left;
  private VictorSP right;
  private DifferentialDrive drive;
  private XboxController xController;
  // private PIDController pidController;

  public DriveSubsystem() {
    this.left = new VictorSP(0);
    this.right = new VictorSP(1);
    this.drive = new DifferentialDrive(left, right);
    this.xController = new XboxController(0);

    // Constructor W/out Period Also Exists
    // this.pidController = new PIDController(kp, ki, kd, period);
  }

  @Override
  public void periodic() {
    double x = this.xController.getLeftX();
    double y = this.xController.getRightY();

    this.drive.arcadeDrive(x, y);
  }
}
