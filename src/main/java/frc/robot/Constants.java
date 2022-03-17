// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static class Drive {
        public static final int FRONT_LEFT = 4;
        public static final int FRONT_RIGHT = 2;
        public static final int BACK_LEFT = 5;
        public static final int BACK_RIGHT = 3;
    }

    public static class Climb {
        public static final int LEFT = 22;
        public static final int CENTER = 23;
        public static final int RIGHT = 21;

        public static final double ZERO = 0.0;
        public static final double MAX_FORWARDS = -1.0;
        public static final double MAX_BACKWARDS = 1.0;

        // Change This Later
        public static final int SOLENOID_ID = 4;
        public static final int CENTER_SOLENOID_ID = 5;

        public static final int OUTER_CLIMB_REVS = 13000;
    }

    public static class Vision {
        // TODO Ensure camera values are correct
        public static final int CAMERA_IMG_WIDTH = 480;
        public static final int CAMERA_IMG_HEIGHT = 360;
    }

    public static class Controller {
        public static final int MAIN = 0;
        public static final int CLIMB = 1;
        public static class Joystick{
            //4 colored buttons
            public static final int A_BUTTON = 1;
            public static final int B_BUTTON = 2;
            public static final int X_BUTTON = 3;
            public static final int Y_BUTTON = 4;

            public static final int LB_BUTTON = 5;
            public static final int RB_BUTTON = 6;

            public static final int BACK_BUTTON = 7;
            public static final int START_BUTTON = 8;

            public static final int LEFT_STICK_BUTTON = 9;
            public static final int RIGHT_STICK_BUTTON = 10;
        }
    }

    public static class Shooter {
        public static final int INTAKE_MOTOR_PORT = 11;
        public static final int GUIDE_MOTOR_PORT = 12;
        public static final int FLYWHEEL_MOTOR_PORT = 13;
        public static final int SOLENOID_ID = 6;

        public static class Speed {
            public static final double SLOW = 0.5;
            public static final double MEDIUM = 0.75;
            public static final double HIGH = 1.0;
        }
    }
}
