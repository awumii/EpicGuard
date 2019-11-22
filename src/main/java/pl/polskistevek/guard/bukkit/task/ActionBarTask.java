package pl.polskistevek.guard.bukkit.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.polskistevek.guard.bukkit.gui.GuiMain;
import pl.polskistevek.guard.bukkit.listener.PreLoginListener;
import pl.polskistevek.guard.bukkit.manager.MessageFileManager;
import pl.polskistevek.guard.bukkit.utils.Notificator;

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
                Notificator.action(MessageFileManager.ACTIONBAR_NO_ATTACK.replace("{ANIM}", "&8O&7oo"));
                animation = 1;
                return;
            }
            if (animation == 1) {
                Notificator.action(MessageFileManager.ACTIONBAR_NO_ATTACK.replace("{ANIM}", "&7o&8O&7o"));
                animation = 2;
                return;
            }
            if (animation == 2) {
                Notificator.action(MessageFileManager.ACTIONBAR_NO_ATTACK.replace("{ANIM}", "&7oo&8O"));
                animation = 3;
                return;
            }
            if (animation == 3) {
                Notificator.action(MessageFileManager.ACTIONBAR_NO_ATTACK.replace("{ANIM}", "&7o&8O&7o"));
                animation = 0;
            }
        }
    }
}
