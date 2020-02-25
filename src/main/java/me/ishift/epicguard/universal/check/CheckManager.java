package me.ishift.epicguard.universal.check;

import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.Logger;
import me.ishift.epicguard.universal.check.detection.*;

import java.util.ArrayList;
import java.util.List;

public class CheckManager {
    private static final List<Check> CHECKS = new ArrayList<>();

    public static void init() {
        final List<Check> list = new ArrayList<>();
        list.add(new BlacklistCheck());
        list.add(new GeoCheck());
        list.add(new NameContainsCheck());
        list.add(new ProxyCheck());
        list.add(new SpeedCheck());
        list.add(new VerifyCheck());

        Config.checks.forEach(name -> list.stream().filter(check -> check.getName().equalsIgnoreCase(name)).forEach(CHECKS::add));
        Logger.info("Currently active checks: " + CHECKS.toString());
    }

    public static List<Check> getChecks() {
        return CHECKS;
    }
}
