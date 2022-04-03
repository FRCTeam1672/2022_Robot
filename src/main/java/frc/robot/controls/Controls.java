package frc.robot.controls;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants;
import frc.robot.Constants.Controller.ControllerType;
import frc.robot.Log;

import java.util.ArrayList;
import java.util.HashMap;

import static frc.robot.Constants.Controller.ControllerType.DRIVE;

public class Controls {
    private final XboxController driveController;
    private final XboxController climbController;

    //The Integer represents the mode, while the controller button represents the command to run
    private final HashMap<Integer, ArrayList<ControllerButton>> driveControllerMap = new HashMap<>();
    private final HashMap<Integer, ArrayList<ControllerButton>> climbControllerMap = new HashMap<>();


    private int driveMode = 0;
    private int climbMode = 0;

    public Controls(XboxController driveController, XboxController climbController) {
        this.driveController = driveController;
        this.climbController = climbController;
        Log.info("Controls system has been initialized and is ready.");

        driveControllerMap.put(1, new ArrayList<>());
        driveControllerMap.put(2, new ArrayList<>());

        climbControllerMap.put(1, new ArrayList<>());
        climbControllerMap.put(2, new ArrayList<>());
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
     * @param mode         The mode to bind the command to
     */
    public void bindButton(ControllerType type, int buttonNumber, Command command, boolean continuous, int mode) {
        //dont even think about passing "null"
        if (command == null) throw new IllegalArgumentException("Command parameter cannot be null");
        if (type == null)
            throw new IllegalArgumentException("Unable to bind controller button. Invalid controller type.");


        if (type == DRIVE) {
            ControllerButton button = new ControllerButton(driveController, buttonNumber, command, continuous, false);
            this.driveControllerMap.get(mode).add(button);
            Log.info("Bound button " + buttonNumber + ".");
        } else if (type == ControllerType.CLIMB) {
            ControllerButton button = new ControllerButton(climbController, buttonNumber, command, continuous, false);
            this.climbControllerMap.get(mode).add(button);
            Log.info("Bound button " + buttonNumber + ".");
        }

    }

    /**
     * Binds a runnable to the specified control button.
     *
     * @param type         Which controller to pick
     * @param buttonNumber The number of the button. You can use the {@link Constants.Controller.Joystick} class to get your bindings.
     * @param runnable     Runnable to run while the button is being held
     * @param continuous   Whether the command should be run continuously while the button is held
     * @param mode The mode to bind the command to
     * @see Controls#bindButton(ControllerType, int, Command, boolean, int)
     */
    public void bindButton(ControllerType type, int buttonNumber, Runnable runnable, boolean continuous, int mode) {
        //dont even think about passing "null"
        if (runnable == null) throw new IllegalArgumentException("Runnable parameter cannot be null");
        if (type == null)
            throw new IllegalArgumentException("Unable to bind controller button. Invalid controller type.");


        if (type == DRIVE) {
            ControllerButton button = new ControllerButton(driveController, buttonNumber, runnable, continuous, false);
            this.driveControllerMap.get(mode).add(button);
            Log.info("Bound button " + buttonNumber + ".");
        } else if (type == ControllerType.CLIMB) {
            ControllerButton button = new ControllerButton(climbController, buttonNumber, runnable, continuous, false);
            this.climbControllerMap.get(mode).add(button);
            Log.info("Bound button " + buttonNumber + ".");
        }
    }

    /**
     * Binds a command to the DPAD button.
     *
     * @param type       Which controller to pick
     * @param dpadDir    The direction of the dpad. Use the {@link Constants.Controller.Joystick} class to get your bindings.
     * @param command    Command to run when the dpad is pressed
     * @param continuous Whether the command should be run continuously while the dpad is held
     * @param mode The mode to bind the command to
     */
    public void bindPOVButton(ControllerType type, int dpadDir, Command command, boolean continuous, int mode) {
        //dont even think about passing "null"
        if (command == null) throw new IllegalArgumentException("Command parameter cannot be null");
        if (type == null)
            throw new IllegalArgumentException("Unable to bind controller POVButton. Invalid controller type.");

        if (type == DRIVE) {
            ControllerButton button = new ControllerButton(driveController, dpadDir, command, continuous, true);
            this.driveControllerMap.get(mode).add(button);
        } else if (type == ControllerType.CLIMB) {
            ControllerButton button = new ControllerButton(climbController, dpadDir, command, continuous, true);
            this.climbControllerMap.get(mode).add(button);
        }
    }

    /**
     * Binds a command to the DPAD button.
     *
     * @param type       Which controller to pick
     * @param dpadDir    The direction of the dpad. Use the {@link Constants.Controller.Joystick} class to get your bindings.
     * @param runnable   Runnable to run when the dpad is pressed
     * @param continuous Whether the command should be run continuously while the dpad is held
     * @param mode The mode to bind the command to
     */
    public void bindPOVButton(ControllerType type, int dpadDir, Runnable runnable, boolean continuous, int mode) {
        //dont even think about passing "null"
        if (runnable == null) throw new IllegalArgumentException("Runnable parameter cannot be null");
        if (type == null)
            throw new IllegalArgumentException("Unable to bind controller POVButton. Invalid controller type.");

        if (type == DRIVE) {
            ControllerButton button = new ControllerButton(driveController, dpadDir, runnable, continuous, true);
            this.driveControllerMap.get(mode).add(button);
        } else if (type == ControllerType.CLIMB) {
            ControllerButton button = new ControllerButton(climbController, dpadDir, runnable, continuous, true);
            this.climbControllerMap.get(mode).add(button);
        }
    }


    /**
     * Set the climb mode to 1 or 2.
     * It will also set the button bindings for the specified controller.
     * @param climbMode The mode to set the climber to.
     */
    public void setClimbMode(int climbMode) {
        //check if climb mode is valid
        if (climbMode != 1 && climbMode != 2) {
            throw new IllegalArgumentException("Climb mode must be 1 or 2");
        }
        this.climbMode = climbMode;
        this.climbControllerMap.get(climbMode).forEach(ControllerButton::bindButton);
    }

    /**
     * Set the drive mode to 1 or 2.
     * It will also set the button bindings for the specified controller.
     * @param driveMode The drive mode to set
     * @throws IllegalArgumentException If the drive mode is not 1 or 2
     */
    public void setDriveMode(int driveMode) {
        if (driveMode != 1 && driveMode != 2) {
            throw new IllegalArgumentException("Invalid drive mode. Must be 1 or 2");
        }
        this.driveMode = driveMode;
        this.driveControllerMap.get(driveMode).forEach(ControllerButton::bindButton);
    }

    /**
     * @return The current controller mode. 0 if no mode is set, and no commands are binded.
     */
    public int getDriveMode() {
        return driveMode;
    }

    /**
     * @return The current controller mode. 0 if no mode is set and no commands are binded.
     */
    public int getClimbMode() {
        return climbMode;
    }

    public void removeBindings() {
        CommandScheduler.getInstance().clearButtons();
    }
}
