package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.Controller.ControllerType;

public class Controls {
    private final XboxController driveController;
    private final XboxController climbController;

    public Controls(XboxController driveController, XboxController climbController){
        this.driveController = driveController;
        this.climbController = climbController;
        Log.info("Controls system has been initialized and is ready.");
    }

    public XboxController getDriveController() {
        return driveController;
    }

    public XboxController getClimbController() {
        return climbController;
    }

    /**
     * Binds a command to the specified control button. You can add an optional 2nd binding which will execute when the button is released.
     * @param type Which controller to pick
     * @param buttonNumber The number of the button. You can use the {@link Constants.Controller.Joystick} class to get your bindings.
     * @param command Command to run while the button is being held
     * @param continuous Whether or not the command should be run continuously while the button is held
     */
    public void bindButton(ControllerType type, int buttonNumber, Command command, boolean continuous){
        //dont even think about passing "null"
        if(command == null) throw new IllegalArgumentException("Command parameter cannot be null");
        if(type == null) throw new IllegalArgumentException("Unable to bind controller button. Invalid controller type.");

        if(type == ControllerType.DRIVE){
            Button button = new JoystickButton(driveController, buttonNumber);
            if(continuous) {
                button.whileHeld(command);
            } else {
                button.whenPressed(command);
            }
            Log.info("Bound button " + buttonNumber + ". Set whileHeld command to " + command.getName());
        }
        else if(type == ControllerType.CLIMB){
            Button button = new JoystickButton(climbController, buttonNumber);
            if(continuous) {
                button.whileHeld(command);
            } else {
                button.whenPressed(command);
            }
            Log.info("Bound button " + buttonNumber + ". Set whileHeld command to " + command.getName());
        }

    }
    /**
     * Please use the normal WPILib command structure
     *
     * Binds a runnable to the specified control button. You can add an optional 2nd binding which will execute when the button is released.
     * @see Controls#bindButton(ControllerType, int, Command, boolean)
     * @param type Which controller to pick
     * @param buttonNumber The number of the button. You can use the {@link Constants.Controller.Joystick} class to get your bindings.
     * @param runnable Runnable to run while the button is being held
     * @param continuous Whether or not the command should be run continuously while the button is held
     */
    @Deprecated()
    public void bindButton(ControllerType type, int buttonNumber, Runnable runnable, boolean continuous){
        //dont even think about passing "null"
        if(runnable == null) throw new IllegalArgumentException("Runnable parameter cannot be null");
        if(type == null) throw new IllegalArgumentException("Unable to bind controller button. Invalid controller type.");

        if(type == ControllerType.DRIVE){
            Button button = new JoystickButton(driveController, buttonNumber);
            if(continuous) {
                button.whileHeld(runnable);
            } else {
                button.whenPressed(runnable);
            }
            Log.warn("Bound button " + button + " to a runnable.");
        }
        else if(type == ControllerType.CLIMB){
            Button button = new JoystickButton(climbController, buttonNumber);
            if(continuous) {
                button.whileHeld(runnable);
            } else {
                button.whenPressed(runnable);
            }
            Log.warn("Bound button " + button + " to a runnable.");
        }
    }
}
