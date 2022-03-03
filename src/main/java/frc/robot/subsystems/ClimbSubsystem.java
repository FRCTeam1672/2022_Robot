package frc.robot.subsystems;

import javax.swing.text.StyledEditorKit.BoldAction;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Climb;
import frc.robot.commands.ExtendArmsCommand;
import frc.robot.commands.ExtendCenterArmCommand;
import frc.robot.commands.RetractInnerArmCommand;
import frc.robot.commands.RetractOuterArmsCommand;
import frc.robot.commands.UnlockArmsCommand;

public class ClimbSubsystem extends SubsystemBase {
    private WPI_TalonSRX left;
    private WPI_TalonSRX center;
    private WPI_TalonSRX right;

    private Solenoid solenoid;
    private Solenoid centerSolenoid;

    private CommandBase[] commands = {new ExtendArmsCommand(this),
            new RetractOuterArmsCommand(this), new ExtendCenterArmCommand(this),
            new RetractInnerArmCommand(this), new UnlockArmsCommand(this)};
    private int nextCommand = 0;
    private final int MID_CLIMB_MAX = 1;

    private boolean running = false;
    private CommandBase current = commands[0];

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

    public CommandBase nextCommand(boolean highClimb) {
        if (!highClimb && nextCommand > MID_CLIMB_MAX)
            return null;

        return commands[nextCommand++];
    }

    public void runNextCommand(boolean high) {
        running = true;
        current = nextCommand(high);
    }

    @Override
    public void periodic() {
        if (running) {
            if (!current.isFinished())
                current.execute();

            running = !current.isFinished();
        }
    }
}
