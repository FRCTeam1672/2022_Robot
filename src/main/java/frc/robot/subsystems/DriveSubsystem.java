package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Drive;

public class DriveSubsystem extends SubsystemBase {
  private WPI_VictorSPX backLeft;
  private WPI_VictorSPX backRight;

  private WPI_TalonSRX frontLeft;
  private WPI_TalonSRX frontRight;

  private DifferentialDrive drive;

  // Remove XboxController Later
  private XboxController xController;
  // private PIDController pidController;

  public DriveSubsystem() {
    this.frontLeft = new WPI_TalonSRX(Drive.FRONT_LEFT);
    this.frontRight = new WPI_TalonSRX(Drive.FRONT_RIGHT);

    this.backLeft = new WPI_VictorSPX(Drive.BACK_LEFT);
    this.backRight = new WPI_VictorSPX(Drive.BACK_RIGHT);

    this.frontRight.setInverted(true);
    this.backRight.setInverted(true);

    this.backLeft.follow(this.frontLeft);
    this.backRight.follow(this.frontRight);

    this.drive = new DifferentialDrive(frontLeft, frontRight);
    this.xController = new XboxController(0);
  }

  @Override
  public void periodic() {
    double x = this.xController.getLeftX();
    double y = this.xController.getRightY();

    this.drive.arcadeDrive(x, y);
  }

  public void move(double x, double z) {
    this.drive.arcadeDrive(x, z);
  }
}
