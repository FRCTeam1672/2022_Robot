package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Shooter;
import frc.robot.Constants.Shooter.Speed;

public class ShooterSubsystem extends SubsystemBase {
    private WPI_VictorSPX intakeMotor;
    private WPI_VictorSPX guideMotor;
    private WPI_TalonSRX flywheelMotor;
    private Solenoid solenoid;

    int currentSpeed = 0;
    final double[] speeds = {Speed.SLOW, Speed.MEDIUM, Speed.HIGH};

    public ShooterSubsystem() {
        init();
    }

    private void init() {
        intakeMotor = new WPI_VictorSPX(Shooter.INTAKE_MOTOR_PORT);
        guideMotor = new WPI_VictorSPX(Shooter.GUIDE_MOTOR_PORT);
        flywheelMotor = new WPI_TalonSRX(Shooter.FLYWHEEL_MOTOR_PORT);
        solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Shooter.SOLENOID_ID);
    }

    public void toggleSpeed() {
        if (currentSpeed < 2)
            currentSpeed++;
        else
            currentSpeed = 0;
    }

    public void shoot() {
        shoot(speeds[currentSpeed]);
    }

    public void shoot(double speed) {
        flywheelMotor.set(speed);
        // TODO Wait for motor to get up to speed
    }

    /**
     * Extends the solenoid which extends the arm. Nothing will happen if it is already extended
     */
    public void extendArm() {
        solenoid.set(true);
    }

    /**
     * Retracts the solenoid which extends the arm. Nothing will happen if it is already retracted
     */
    public void retractArm() {
        solenoid.set(false);
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

}
