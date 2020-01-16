package me.ishift.epicguard.bukkit.util;

import me.ishift.epicguard.bukkit.manager.FileManager;
import me.ishift.epicguard.bukkit.object.CustomFile;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;

import java.util.ArrayList;
import java.util.List;

public class LogFilter extends AbstractFilter {
    private String path;

    public LogFilter(String path) {
        this.path = path;
        FileManager.createFile(path);
        final CustomFile file = FileManager.getFile(path);
        if (!file.isExisting()) {
            file.create();
            final List<String> list = new ArrayList<>();
            list.add("Disconnecting");
            list.add("lost connection");
            list.add("authlib");
            list.add("GameProfile");
            file.getConfig().set("console-filter.enabled", true);
            file.getConfig().set("console-filter.messages", list);
            file.save();
        }
    }

    public void registerFilter() {
        Logger logger = (Logger) LogManager.getRootLogger();
        logger.addFilter(this);
    }

    @Override
    public Result filter(LogEvent event) {
        return event == null ? Result.NEUTRAL : isLoggable(event.getMessage().getFormattedMessage());
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
        return isLoggable(msg.getFormattedMessage());
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String msg, Object... params) {
        return isLoggable(msg);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
        return msg == null ? Result.NEUTRAL : isLoggable(msg.toString());
    }

    private Result isLoggable(String msg) {
        if (msg != null) {
            final CustomFile file = FileManager.getFile(path);
            if (file.getConfig().getBoolean("console-filter.enabled")) {
                for (String string : file.getConfig().getStringList("console-filter.messages")) {
                    if (msg.contains(string)) {
                        return Result.DENY;
                    }
                }
            }
        }
        return Result.NEUTRAL;
    }

}
