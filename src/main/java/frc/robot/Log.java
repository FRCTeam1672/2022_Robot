package frc.robot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static frc.robot.Constants.*;

public class Log {

    public static void info(String msg){
        if(!IS_DEBUGGING || DONT_LOG_INFO) return;

        Date date = new Date();
        DateFormat formatter;
        if(!USE_24_HOUR_TIME) formatter = new SimpleDateFormat("h:mm:ss a");
        else formatter = new SimpleDateFormat("HH:mm:ss");

        formatter.setTimeZone(TimeZone.getTimeZone("EST"));
        String currentTime;
        currentTime = formatter.format(date);

        System.out.println("[Info " + currentTime + "]: " + msg);
    }
    public static void debug(String msg){
        if(!IS_DEBUGGING || DONT_LOG_INFO) return;

        Date date = new Date();
        DateFormat formatter;
        if(!USE_24_HOUR_TIME) formatter = new SimpleDateFormat("h:mm:ss a");
        else formatter = new SimpleDateFormat("HH:mm:ss");

        formatter.setTimeZone(TimeZone.getTimeZone("EST"));
        String currentTime;
        currentTime = formatter.format(date);
        System.out.println("[DEBUG " + currentTime + "]: " + msg);
    }
    public static void warn(String msg){
        Date date = new Date();
        DateFormat formatter;
        if(!USE_24_HOUR_TIME) formatter = new SimpleDateFormat("h:mm:ss a");
        else formatter = new SimpleDateFormat("HH:mm:ss");

        formatter.setTimeZone(TimeZone.getTimeZone("EST"));
        String currentTime;
        currentTime = formatter.format(date);
        System.out.println("[WARN " + currentTime + "]: " + msg +  "");
    }
    public static void error(String msg){
        Date date = new Date();
        DateFormat formatter;
        if(!USE_24_HOUR_TIME) formatter = new SimpleDateFormat("h:mm:ss a");
        else formatter = new SimpleDateFormat("HH:mm:ss");

        formatter.setTimeZone(TimeZone.getTimeZone("EST"));
        String currentTime;
        currentTime = formatter.format(date);
        System.out.println("<!><!> [ERROR " + currentTime + "]: " + msg +  " !><!>");
    }
}
