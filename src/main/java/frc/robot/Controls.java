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
     * @param commandOnEnd A 2nd command to run when the user stops pressing it (can be NULL if there is none)
     */
    public void bindButton(ControllerType type, int buttonNumber, Command command, Command commandOnEnd){
        //dont even think about passing "null"
        if(command == null) throw new IllegalArgumentException("Command parameter cannot be null");
        if(type == null) throw new IllegalArgumentException("Unable to bind controller button. Invalid controller type.");

        if(type == ControllerType.DRIVE){
            Button button = new JoystickButton(driveController, buttonNumber);
            button.whileHeld(command);
            if(commandOnEnd != null){
                button.whenReleased(commandOnEnd);
                Log.info("Bound button " + buttonNumber + ". Set whileHeld command to " + command.getName() + ", and set the 2nd command to " + commandOnEnd.getName());
                return;
            }
            Log.info("Bound button " + buttonNumber + ". Set whileHeld command to " + command.getName());
        }
        else if(type == ControllerType.CLIMB){
            Button button = new JoystickButton(climbController, buttonNumber);
            button.whileHeld(command);
            if(commandOnEnd != null){
                button.whenReleased(commandOnEnd);
                Log.info("Bound button " + buttonNumber + ". Set whileHeld command to " + command.getName() + ", and set the 2nd command to " + commandOnEnd.getName());
                return;
            }
            Log.info("Bound button " + buttonNumber + ". Set whileHeld command to " + command.getName());
        }

    }
    /**
     * <!> Please use the normal WPILib command structure<!>
     * Binds a runnable to the specified control button. You can add an optional 2nd binding which will execute when the button is released.
     * @see Controls#bindButton(ControllerType, int, Command, Command)
     * @param type Which controller to pick
     * @param buttonNumber The number of the button. You can use the {@link Constants.Controller.Joystick} class to get your bindings.
     * @param runnable Runnable to run while the button is being held
     * @param runnableOnEnd A 2nd runnable to run when the user stops pressing it (can be NULL if there is none)
     */
    @Deprecated()
    public void bindButton(ControllerType type, int buttonNumber, Runnable runnable, Runnable runnableOnEnd){
        //dont even think about passing "null"
        if(runnable == null) throw new IllegalArgumentException("Runnable parameter cannot be null");
        if(type == null) throw new IllegalArgumentException("Unable to bind controller button. Invalid controller type.");

        if(type == ControllerType.DRIVE){
            Button button = new JoystickButton(driveController, buttonNumber);
            button.whileHeld(runnable);

            if(runnableOnEnd != null){
                button.whenReleased(runnableOnEnd);
                Log.warn("Bound button " + button + " to a runnable, with a 2nd runnable being assigned.");
                return;
            }
            Log.warn("Bound button " + button + " to a runnable.");
        }
        else if(type == ControllerType.CLIMB){
            Button button = new JoystickButton(climbController, buttonNumber);
            button.whileHeld(runnable);
            if(runnableOnEnd != null){
                button.whenReleased(runnableOnEnd);
                Log.warn("Bound button " + button + " to a runnable, with a 2nd runnable being assigned.");
                return;
            }
            Log.warn("Bound button " + button + " to a runnable.");
        }
    }
}
