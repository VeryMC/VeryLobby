package fr.verymc.utils;

import fr.verymc.main;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.entity.Player;

import java.time.Instant;

public class RankExpiry {

    public static long GetTimeLeft(Player player) {
        User user = main.api.getUserManager().getUser(player.getUniqueId());


        Instant expire = null;
        for (Node node : user.getNodes()) {
            if (node.hasExpiry()) {
                expire = node.getExpiry();
            }
        }

        long duration = -1;
        if (expire != null) {
            Instant now = Instant.now();
            long NowMillis = now.toEpochMilli();
            long expireMillis = expire.toEpochMilli();

            duration = expireMillis - NowMillis;
        }
        return duration;
    }
}
