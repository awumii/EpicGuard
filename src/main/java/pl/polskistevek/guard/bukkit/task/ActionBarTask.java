package pl.polskistevek.guard.bukkit.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.polskistevek.guard.bukkit.gui.GuiMain;
import pl.polskistevek.guard.bukkit.listener.PreLoginListener;
import pl.polskistevek.guard.bukkit.util.MessagesBukkit;
import pl.polskistevek.guard.bukkit.util.Notificator;

public class ActionBarTask implements Runnable {
    private static int animation = 0;

    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()){
            if (p.getOpenInventory().getTitle().equals("EpicGuard Menu")){
                GuiMain.show(p);
            }
        }
        if (PreLoginListener.cps == 0) {
            if (animation == 0) {
                Notificator.action(MessagesBukkit.ACTIONBAR_NO_ATTACK.replace("{ANIM}", "&8O&7oo").replace("{CPS}", String.valueOf(PreLoginListener.cps)));
                animation = 1;
                return;
            }
            if (animation == 1) {
                Notificator.action(MessagesBukkit.ACTIONBAR_NO_ATTACK.replace("{ANIM}", "&7o&8O&7o").replace("{CPS}", String.valueOf(PreLoginListener.cps)));
                animation = 2;
                return;
            }
            if (animation == 2) {
                Notificator.action(MessagesBukkit.ACTIONBAR_NO_ATTACK.replace("{ANIM}", "&7oo&8O").replace("{CPS}", String.valueOf(PreLoginListener.cps)));
                animation = 3;
                return;
            }
            if (animation == 3) {
                Notificator.action(MessagesBukkit.ACTIONBAR_NO_ATTACK.replace("{ANIM}", "&7o&8O&7o").replace("{CPS}", String.valueOf(PreLoginListener.cps)));
                animation = 0;
            }
        }
    }
}
