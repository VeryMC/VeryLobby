package fr.verymc.jump;

import org.bukkit.Location;

import java.util.ArrayList;

public class JumpParticleManager {

    public static ArrayList<Location> locs = new ArrayList<Location>();

    public static void CheckForParticuleApply() {
        /*for (Location loc : locs) {
            double radius = 1.0;
            for (double y = 0; y <= 6.4; y += 0.1) {
                double x = radius * Math.cos(y);
                double z = radius * Math.sin(y);
                PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, true, (float) (loc.getX() + x),
                        (float) (loc.getY()), (float) (loc.getZ() + z), 0, 0, 0, 1, 0);
                for (Player online : Bukkit.getOnlinePlayers()) {
                    ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
                }
            }
        }
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(main.instance, new Runnable() {
            public void run() {
                CheckForParticuleApply();
            }
        }, 20);*/
    }

}
