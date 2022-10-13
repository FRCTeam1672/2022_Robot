package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.Constants.Controller.ControllerType;

public class Controls {
    private final XboxController driveController;
    private final XboxController climbController;

    public Controls(XboxController driveController, XboxController climbController) {
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
     *
     * @param type         Which controller to pick
     * @param buttonNumber The number of the button. You can use the {@link Constants.Controller.Joystick} class to get your bindings.
     * @param command      Command to run while the button is being held
     * @param continuous   Whether the command should be run continuously while the button is held
     */
    public void bindButton(ControllerType type, int buttonNumber, Command command, boolean continuous) {
        //dont even think about passing "null"
        if (command == null) throw new IllegalArgumentException("Command parameter cannot be null");
        if (type == null)
            throw new IllegalArgumentException("Unable to bind controller button. Invalid controller type.");

        if (type == ControllerType.DRIVE) {
            Button button = new JoystickButton(driveController, buttonNumber);
            if (continuous) {
                button.whileHeld(command);
            } else {
                button.whenPressed(command);
            }
            Log.info("Bound button " + buttonNumber + ". Set whileHeld command to " + command.getName());
        } else if (type == ControllerType.CLIMB) {
            Button button = new JoystickButton(climbController, buttonNumber);
            if (continuous) {
                button.whileHeld(command);
            } else {
                button.whenPressed(command);
            }
            Log.info("Bound button " + buttonNumber + ". Set whileHeld command to " + command.getName());
        }

    }

    /**
     * Binds a command to the DPAD button.
     * @param type Which controller to pick
     * @param dpadDir The direction of the dpad. Use the {@link Constants.Controller.Joystick} class to get your bindings.
     * @param command Command to run when the dpad is pressed
     * @param continuous Whether the command should be run continuously while the dpad is held
     */
    public void bindPOVButton(ControllerType type, int dpadDir, Command command, boolean continuous) {
        //dont even think about passing "null"
        if (command == null) throw new IllegalArgumentException("Command parameter cannot be null");
        if (type == null)
            throw new IllegalArgumentException("Unable to bind controller POVButton. Invalid controller type.");

        if (type == ControllerType.DRIVE) {
            Button button = new POVButton(driveController, dpadDir);
            if (continuous) {
                button.whileHeld(command);
                Log.info("Bound POVButton " + dpadDir + ". Set whileHeld command to " + command.getName());
            } else {
                button.whenPressed(command);
                Log.info("Bound POVButton " + dpadDir + " and will only execute once");
            }
        } else if (type == ControllerType.CLIMB) {
            Button button = new POVButton(climbController, dpadDir);
            if (continuous) {
                button.whileHeld(command);
                Log.info("Bound POVButton " + dpadDir + ". Set whileHeld command to " + command.getName());
            } else {
                button.whenPressed(command);
                Log.info("Bound POVButton " + dpadDir + "and will only execute once");
            }
        }
    }
    /**
     * Binds a command to the DPAD button.
     * @param type Which controller to pick
     * @param dpadDir The direction of the dpad. Use the {@link Constants.Controller.Joystick} class to get your bindings.
     * @param runnable Runnable to run when the dpad is pressed
     * @param continuous Whether the command should be run continuously while the dpad is held
     */
    public void bindPOVButton(ControllerType type, int dpadDir, Runnable runnable, boolean continuous) {
        //dont even think about passing "null"
        if (runnable == null) throw new IllegalArgumentException("Runnable parameter cannot be null");
        if (type == null)
            throw new IllegalArgumentException("Unable to bind controller POVButton. Invalid controller type.");

        if (type == ControllerType.DRIVE) {
            Button button = new POVButton(driveController, dpadDir);
            if (continuous) {
                button.whileHeld(runnable);
                Log.warn("Bound button " + button + " to a runnable, and is continuous");
            } else {
                button.whenPressed(runnable);
                Log.warn("Bound button " + button + " to a runnable, and is not continuous");
            }
        } else if (type == ControllerType.CLIMB) {
            Button button = new POVButton(climbController, dpadDir);
            if (continuous) {
                button.whileHeld(runnable);
                Log.warn("Bound button " + button + " to a runnable on the climb controller and is continuous.");
            } else {
                button.whenPressed(runnable);
                Log.warn("Bound button " + button + " to a runnable on the climb controller is not continuous.");
            }
        }
    }

    /**
     * Please use the normal WPILib command structure
     * <p>
     * Binds a runnable to the specified control button. You can add an optional 2nd binding which will execute when the button is released.
     *
     * @param type         Which controller to pick
     * @param buttonNumber The number of the button. You can use the {@link Constants.Controller.Joystick} class to get your bindings.
     * @param runnable     Runnable to run while the button is being held
     * @param continuous   Whether the command should be run continuously while the button is held
     * @see Controls#bindButton(ControllerType, int, Command, boolean)
     */
    public void bindButton(ControllerType type, int buttonNumber, Runnable runnable, boolean continuous) {
        //dont even think about passing "null"
        if (runnable == null) throw new IllegalArgumentException("Runnable parameter cannot be null");
        if (type == null)
            throw new IllegalArgumentException("Unable to bind controller button. Invalid controller type.");

        if (type == ControllerType.DRIVE) {
            Button button = new JoystickButton(driveController, buttonNumber);
            if (continuous) {
                button.whileHeld(runnable);
            } else {
                button.whenPressed(runnable);
            }
            Log.warn("Bound button " + button + " to a runnable.");
        } else if (type == ControllerType.CLIMB) {
            Button button = new JoystickButton(climbController, buttonNumber);
            if (continuous) {
                button.whileHeld(runnable);
            } else {
                button.whenPressed(runnable);
            }
            Log.warn("Bound button " + button + " to a runnable.");
        }
    }
}
