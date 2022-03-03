package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Shooter;
import frc.robot.Constants.Shooter.Speed;

public class ShooterSubsystem extends SubsystemBase {
    private WPI_VictorSPX intakeMotor;
    private WPI_VictorSPX guideMotor;
    private WPI_TalonSRX flywheelMotor;
    private Solenoid solenoid;

    double currentSpeed = 1;


    public ShooterSubsystem() {
        init();
    }

    private void init() {
        intakeMotor = new WPI_VictorSPX(Shooter.INTAKE_MOTOR_PORT);
        guideMotor = new WPI_VictorSPX(Shooter.GUIDE_MOTOR_PORT);
        flywheelMotor = new WPI_TalonSRX(Shooter.FLYWHEEL_MOTOR_PORT);
        solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Shooter.SOLENOID_ID);

        flywheelMotor.setInverted(true);
    }

    public void toggleSpeed() {
        currentSpeed = currentSpeed == 1 ? 0.5 : 1;
        SmartDashboard.putString("Shooter Speed", getShooterSpeed());
    }

    public WPI_VictorSPX getIntakeMotor() {
        return intakeMotor;
    }

    public WPI_VictorSPX getGuideMotor() {
        return guideMotor;
    }

    public WPI_TalonSRX getFlywheelMotor() {
        return flywheelMotor;
    }

    public Solenoid getSolenoid() {
        return solenoid;
    }

    public String getShooterSpeed() {
        return currentSpeed == 1 ? "HIGH" : "LOW";
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }
}
