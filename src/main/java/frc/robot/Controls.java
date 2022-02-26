package frc.robot;

import java.util.Map.Entry;
import java.util.HashMap;
import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.Controller;

public class Controls {

    static XboxController player1 = new XboxController(Controller.MAIN);
    static XboxController player2 = new XboxController(Controller.CLIMB);

    HashMap<Buttons, Runnable> onPress = new HashMap<Buttons, Runnable>();
    HashMap<Buttons, Runnable> onHold = new HashMap<Buttons, Runnable>();
    HashMap<Buttons, Runnable> onRelease = new HashMap<Buttons, Runnable>();

    public void bindButton(Buttons button, InputType inputType, Runnable command) {
        if (inputType == InputType.HELD) {
            onHold.put(button, command);
        } else if (inputType == InputType.PRESSED) {
            onPress.put(button, command);
        } else if (inputType == InputType.RELEASED) {
            onRelease.put(button, command);
        }
    }

    public void bindButton(Buttons button, InputType inputType, Command command) {
        bindButton(button, inputType, () -> command.execute());
    }

    public void periodic() {
        for (Entry<Buttons, Runnable> entry : onPress.entrySet()) {
            if (entry.getKey().pressed.getAsBoolean()) {
                entry.getValue().run();
            }
        }

        for (Entry<Buttons, Runnable> entry : onHold.entrySet()) {
            if (entry.getKey().held.getAsBoolean()) {
                entry.getValue().run();
            }
        }

        for (Entry<Buttons, Runnable> entry : onRelease.entrySet()) {
            if (entry.getKey().released.getAsBoolean()) {
                entry.getValue().run();
            }
        }
    }

    public enum InputType {
        PRESSED, HELD, RELEASED;
    }

    public enum Buttons {
        P1_A(() -> player1.getAButtonPressed(), () -> player1.getAButton(),
                () -> player1.getAButtonReleased()), P1_B(() -> player1.getBButtonPressed(),
                        () -> player1.getBButton(), () -> player1.getBButtonReleased()), P1_X(
                                () -> player1.getXButtonPressed(), () -> player1.getXButton(),
                                () -> player1.getXButtonReleased()), P1_Y(
                                        () -> player1.getYButtonPressed(),
                                        () -> player1.getYButton(),
                                        () -> player1.getYButtonReleased()), P1_LB(
                                                () -> player1.getLeftBumperPressed(),
                                                () -> player1.getLeftBumper(),
                                                () -> player1.getLeftBumperReleased()), P1_RB(
                                                        () -> player1.getRightBumperPressed(),
                                                        () -> player1.getRightBumper(),
                                                        () -> player1
                                                                .getRightBumperReleased()), P1_LJ(
                                                                        () -> player1
                                                                                .getLeftStickButtonPressed(),
                                                                        () -> player1
                                                                                .getLeftStickButton(),
                                                                        () -> player1
                                                                                .getLeftStickButtonReleased()), P1_RJ(
                                                                                        () -> player1
                                                                                                .getRightStickButtonPressed(),
                                                                                        () -> player1
                                                                                                .getRightStickButton(),
                                                                                        () -> player1
                                                                                                .getRightStickButtonReleased()),

        P2_A(() -> player2.getAButtonPressed(), () -> player2.getAButton(),
                () -> player2.getAButtonReleased()), P2_B(() -> player2.getBButtonPressed(),
                        () -> player2.getBButton(), () -> player2.getBButtonReleased()), P2_X(
                                () -> player2.getXButtonPressed(), () -> player2.getXButton(),
                                () -> player2.getXButtonReleased()), P2_Y(
                                        () -> player2.getYButtonPressed(),
                                        () -> player2.getYButton(),
                                        () -> player2.getYButtonReleased()), P2_LB(
                                                () -> player2.getLeftBumperPressed(),
                                                () -> player2.getLeftBumper(),
                                                () -> player2.getLeftBumperReleased()), P2_RB(
                                                        () -> player2.getRightBumperPressed(),
                                                        () -> player2.getRightBumper(),
                                                        () -> player2
                                                                .getRightBumperReleased()), P2_LJ(
                                                                        () -> player2
                                                                                .getLeftStickButtonPressed(),
                                                                        () -> player2
                                                                                .getLeftStickButton(),
                                                                        () -> player2
                                                                                .getLeftStickButtonReleased()), P2_RJ(
                                                                                        () -> player2
                                                                                                .getRightStickButtonPressed(),
                                                                                        () -> player2
                                                                                                .getRightStickButton(),
                                                                                        () -> player2
                                                                                                .getRightStickButtonReleased());

        private BooleanSupplier pressed, held, released;

        Buttons(BooleanSupplier pressed, BooleanSupplier held, BooleanSupplier released) {
            this.pressed = pressed;
            this.held = held;
            this.released = released;
        }
    }
}
