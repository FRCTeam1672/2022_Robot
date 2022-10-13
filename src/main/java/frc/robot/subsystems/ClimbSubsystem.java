package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Climb;

public class ClimbSubsystem extends SubsystemBase {
    private final WPI_TalonSRX left;
    private final WPI_TalonSRX center;
    private final WPI_TalonSRX right;

    private final Solenoid solenoid;
    private final Solenoid centerSolenoid;

    public ClimbSubsystem() {
        this.left = new WPI_TalonSRX(Climb.LEFT);
        this.center = new WPI_TalonSRX(Climb.CENTER);
        this.right = new WPI_TalonSRX(Climb.RIGHT);

        this.right.setInverted(true);

        this.solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Climb.SOLENOID_ID);
        this.centerSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Climb.CENTER_SOLENOID_ID);
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

    public Solenoid getCenterSolenoid() {
        return centerSolenoid;
    }
}
