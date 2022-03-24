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
    public static class Autonomous {
        //Was 8000 * 2.4
        public static final int MOVED = 19200;
        public static final double SPEED = -0.7;
        public static final int FLYWHEEL_POSITION = 400000;
        public static final long FINISH_POSITION = 2_600_000L;
    }

    public static class Drive {
        public static final int FRONT_LEFT = 4;
        public static final int FRONT_RIGHT = 2;
        public static final int BACK_LEFT = 5;
        public static final int BACK_RIGHT = 3;
    }

    public static class Climb {
        //Retract speed for the arms
        public static final double RETRACT_SPEED = 0.45;
        //Retract speed for the arms
        public static final double UNDO_SPEED = -0.5;
        //The amount to undo the arms
        public static final int UNDO_AMOUNT = 900;

        public static final int LEFT = 22;
        public static final int CENTER = 23;
        public static final int RIGHT = 21;
        
        public static final double MAX_FORWARDS = -1.0;
        public static final double MAX_BACKWARDS = 1.0;

        public static final int SOLENOID_ID = 4;
        public static final int CENTER_SOLENOID_ID = 5;

        public static final int OUTER_CLIMB_REVS = 13000;
    }

    public static class Vision {
        public static final int CAMERA_IMG_WIDTH = 480;
        public static final int CAMERA_IMG_HEIGHT = 240;
    }

    public static class Controller {
        public enum ControllerType {
            DRIVE,
            CLIMB
        }

        public static class Joystick {
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

            public static final double INTAKE = 0.85;
            public static final double GUIDE = 0.5;
        }
    }

    public static final boolean IS_DEBUGGING = false;
    public static final boolean DONT_LOG_INFO = false;
    //Whether we use 17:00 for logging or 6:00 PM for logging
    public static final boolean USE_24_HOUR_TIME = true;
}
