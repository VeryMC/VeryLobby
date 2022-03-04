package fr.verymc;

import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ScoreBoardNMSRanksJump {

    public static void AutoUpdate() {
        ScoreBoardNMS.preffixes.clear();
        ScoreBoardNMS.suffixes.clear();
        for (Player player : Bukkit.getOnlinePlayers()) {
            String Preffix = "§7N/A";
            String Suffix = "x";

            User user = main.api.getUserManager().getUser(player.getUniqueId());
            if (user.getCachedData().getMetaData().getPrefix() != null) {
                Preffix = user.getCachedData().getMetaData().getPrefix().replace("&l", "").replace("&", "§").replace("&d✯", "");
            }
            if (user.getCachedData().getMetaData().getSuffix() != null) {
                Suffix = user.getCachedData().getMetaData().getSuffix().replace("&l", "").replace("&", "§");
            }
            ScoreBoardNMS.preffixes.put(player.getUniqueId(), Preffix);
            ScoreBoardNMS.suffixes.put(player.getUniqueId(), Suffix);
        }
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(main.instance, new Runnable() {
            public void run() {
                AutoUpdate();
            }
        }, 20);
    }

    public static String GetPrefix(Player player) {

        String Prefix = "§7N/A";

        if (!ScoreBoardNMS.preffixes.containsKey(player.getUniqueId())) {
            User user = main.api.getUserManager().getUser(player.getUniqueId());
            if (user.getCachedData().getMetaData().getPrefix() != null) {
                Prefix = user.getCachedData().getMetaData().getPrefix().replace("&l", "").replace("&", "§").replace("&d✯", "");
            }
        } else {
            Prefix = ScoreBoardNMS.preffixes.get(player.getUniqueId());
        }

        return Prefix;
    }

    public static String GetSuffix(Player player) {

        String Suffix = "§7N/A";

        if (!ScoreBoardNMS.suffixes.containsKey(player.getUniqueId())) {
            User user = main.api.getUserManager().getUser(player.getUniqueId());
            if (user.getCachedData().getMetaData().getSuffix() != null) {
                Suffix = user.getCachedData().getMetaData().getSuffix().replace("&l", "").replace("&", "§").replace("&d✯", "");
            }
        } else {
            Suffix = ScoreBoardNMS.suffixes.get(player.getUniqueId());
        }

        return Suffix;
    }

}
