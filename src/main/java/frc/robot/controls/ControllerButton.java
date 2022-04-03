package frc.robot.controls;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

public class ControllerButton {
    private final XboxController controller;
    private final int button;
    private Runnable runnable;
    private Command command;
    private final boolean continuous;
    private final Button joyStick;

    public ControllerButton(XboxController controller, int button, Command command, boolean continuous, boolean isPOV) {
        this.controller = controller;
        this.button = button;
        this.command = command;
        this.continuous = continuous;
        if(isPOV) joyStick = new POVButton(controller, button);
        else joyStick = new JoystickButton(controller, button);
    }
    public ControllerButton (XboxController controller, int button, Runnable runnable, boolean continuous, boolean isPOV) {
        this.controller = controller;
        this.button = button;
        this.runnable = runnable;
        this.continuous = continuous;
        if(isPOV) joyStick = new POVButton(controller, button);
        else joyStick = new JoystickButton(controller, button);
    }

    public void bindButton(){
        if(continuous) joyStick.whileHeld(command);
        else joyStick.whenPressed(command);
    }
    public XboxController getType() {
        return controller;
    }

    public int getButton() {
        return button;
    }

    public Command getCommand() {
        return command;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public boolean isContinuous() {
        return continuous;
    }

    public boolean isRunnable() {
        return runnable != null;
    }
    public Button getJoystickButton(){
        return this.joyStick;
    }

}
