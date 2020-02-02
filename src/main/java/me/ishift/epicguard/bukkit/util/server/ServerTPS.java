package me.ishift.epicguard.bukkit.util.server;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;

public class ServerTPS {
    public static String getTPS() {
        try {
            final DecimalFormat format = new DecimalFormat("##.##");
            final Object serverInstance;
            final Field tpsField;
            serverInstance = Reflection.getNMSClass("MinecraftServer").getMethod("getServer").invoke(null);
            tpsField = serverInstance.getClass().getField("recentTps");

            double[] tps = ((double[]) tpsField.get(serverInstance));
            return format.format(tps[0]);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return "20.0";
    }
}
