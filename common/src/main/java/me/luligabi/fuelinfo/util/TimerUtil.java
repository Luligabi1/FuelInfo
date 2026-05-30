package me.luligabi.fuelinfo.util;

import net.minecraft.util.Tuple;

public class TimerUtil {

    public static Tuple<String, String> getTime(int time) {
        String minutes = format((time % 3600) / 60);
        String seconds = format(time % 60);

        return new Tuple<>(minutes, seconds);
    }

    public static String format(int time) {
        return String.format("%02d", time);
    }
}