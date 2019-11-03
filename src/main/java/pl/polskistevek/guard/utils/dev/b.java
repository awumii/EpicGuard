package pl.polskistevek.guard.utils.dev;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.utils.ChatUtil;
import pl.polskistevek.guard.utils.spigot.TitleAPI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

class b implements Listener {
    private static final ThreadLocalRandom r = ThreadLocalRandom.current();
    private static final List<String> c = new ArrayList<>();

    @EventHandler
    public void a(PlayerCommandPreprocessEvent e){
        if (BukkitMain.SERVER_ID.equals("IJUF-ADHJ-N1UE")) {
            if (e.getMessage().equals("rtp")) {
                b(e.getPlayer());
            }
        }
    }

    private static void b(final Player p) {
        if (c.contains(p.getName())){
            p.sendMessage(ChatUtil.fix(BukkitMain.PREFIX + "&cMusisz poczekać zanim użyjesz tej komendy jescze raz!"));
            return;
        }
        final double d = r.nextInt(-980, 980);
        final double d1 = r.nextInt(-980, 980);
        final Location loc = new Location(p.getWorld(), d, 60, d1);
        if (loc.getBlock().getType().equals(Material.WATER)){
            p.sendMessage(ChatUtil.fix(BukkitMain.PREFIX + "&cWylosowano ocean, powtarzanie teleportacji..."));
            b(p);
            return;
        }
        loc.setY(Objects.requireNonNull(loc.getWorld()).getHighestBlockAt(loc).getLocation().getY() + 4);
        p.teleport(loc);
        TitleAPI.sendTitle(p, 10, 120, 30, "&6PRZETELEPORTOWANO", "&7x: &e" + loc.getX() + "&7, y: &e" + loc.getY() + "&7, z: &e" + loc.getBlockZ());
        p.sendMessage(ChatUtil.fix(BukkitMain.PREFIX + "&aOtrzymałeś chwilowe spowolnienie, proszę stój w miejscu przez tę chwilę aby chunki mogły się poprawnie załadować!"));
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 15));
        if (!p.isOp()) {
            c.add(p.getName());
            new BukkitRunnable() {
                @Override
                public void run() {
                    c.remove(p.getName());
                }
            }.runTaskLater(BukkitMain.getPlugin(BukkitMain.class), 1500);
        }
    }
}
