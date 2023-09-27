package me.luligabi.fuelinfo.util;

import net.minecraft.util.Pair;

public class TimerUtil {

    public static Pair<String, String> getTime(int time) {
        String minutes = format((time % 3600) / 60);
        String seconds = format(time % 60);

        return new Pair<>(minutes, seconds);
    }

    public static String format(int time) {
        return String.format("%02d", time);
    }

}
