package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Climb;

public class ClimbSubsystem extends SubsystemBase {
    private WPI_TalonSRX left;
    private WPI_TalonSRX center;
    private WPI_TalonSRX right;

    private Solenoid solenoid;

    public ClimbSubsystem() {
        this.left = new WPI_TalonSRX(Climb.LEFT);
        this.center = new WPI_TalonSRX(Climb.CENTER);
        this.right = new WPI_TalonSRX(Climb.RIGHT);

        this.solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Climb.SOLENOID_ID);
    }

    public WPI_TalonSRX getLeftMotor() {
        return this.left;
    }

    public WPI_TalonSRX getCenterMotor() {
        return this.center;
    }

    public WPI_TalonSRX getRightMotor() {
        return this.right;
    }

    public Solenoid getSolenoid() {
        return this.solenoid;
    }
}
