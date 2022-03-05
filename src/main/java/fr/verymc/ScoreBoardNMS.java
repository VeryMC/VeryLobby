package fr.verymc;

import fr.verymc.utils.ScoreboardSign;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class ScoreBoardNMS {

    public static Map<UUID, ScoreboardSign> boards = new HashMap<>();
    public static Map<UUID, String> preffixes = new HashMap<>();
    public static Map<UUID, String> suffixes = new HashMap<>();

    public static ScoreBoardNMS instance;

    public int online = 0;

    Jedis j = null;

    public ScoreBoardNMS() {
        instance = this;
        UpdateScoreBoard();
    }

    public void MakeScoreBoardForPlayer(Player player) {
        ScoreboardSign sb = new ScoreboardSign(player, "\u00A76\u00A7l✯ Very\u00A7f\u00A7lMc ✯");

        String Preffix = ScoreBoardNMSRanksJump.GetPrefix(player);
        String Suffix = ScoreBoardNMSRanksJump.GetSuffix(player);

        String serveurname = Bukkit.getServerName();
        sb.create();
        sb.setLine(14, "\u00A7f\u00A7lwww.\u00A76\u00A7lvery\u00A7f\u00A7lmc\u00A7f\u00A7l.fr");

        sb.setLine(13, "\u00A7f");

        sb.setLine(12, "\u00A77Lobby \u00A77▸ \u00A7a" + online);
        sb.setLine(11, "\u00A77Boutique \u00A77▸\u00A7e /boutique");
        sb.setLine(10, "\u00A77Discord \u00A77▸\u00A7d /discord");
        sb.setLine(9, "\u00A7c\u00A7l➔ Informations");

        sb.setLine(8, "\u00A71");

        sb.setLine(7, "\u00A77Hub \u00A77▸ \u00A76" + serveurname);
        sb.setLine(6, "\u00A77Verycoins \u00A77▸ \u00A7csoon");
        if (Suffix.contains("✰")) {
            sb.setLine(5, "\u00A77Premium \u00A77▸ \u00A7a\u00A7l✔");
        } else {
            sb.setLine(5, "\u00A77Premium \u00A77▸ \u00A7c\u00A7lx");
        }
        sb.setLine(4, "\u00A77Grade \u00A77▸ " + Preffix);
        sb.setLine(3, "\u00A77Pseudo \u00A77▸ \u00A7f" + player.getName());

        sb.setLine(2, "\u00A7a\u00A7l➔ Profil");

        sb.setLine(1, "\u00A73");

        boards.put(player.getUniqueId(), sb);

    }

    public void UpdateScoreBoard() {
        //int vanisheds = 0;
        /*try {
            j = main.pool.getResource();
            // If you want to use a password, use
            j.auth(System.getenv("REDIS_PASSWORD"));
            for (String van : j.keys("Mod:")) {
                vanisheds += 1;
            }
            String returned = j.get("connected:global");
            if (returned != null) {
                try {
                    online = Integer.parseInt(returned);
                } catch (NumberFormatException e) {
                    online = 0;
                }
            } else online = 0;

        } finally {
            j.close();
        }*/
        ArrayList<String> players = new ArrayList<String>();
        players.clear();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getMetadata("vanished").isEmpty()) {
                players.add(p.getName());
            }
        }
        online = players.size();
        for (Entry<UUID, ScoreboardSign> board : boards.entrySet()) {

            String Preffix = ScoreBoardNMSRanksJump.GetPrefix(Bukkit.getPlayer(board.getKey()));
            String Suffix = ScoreBoardNMSRanksJump.GetSuffix(Bukkit.getPlayer(board.getKey()));
            if(!board.getValue().getLine(12).equalsIgnoreCase("\u00A77Lobby \u00A77▸ \u00A7a" + online))
            board.getValue().setLine(12, "\u00A77Lobby \u00A77▸ \u00A7a" + online);
            if(!board.getValue().getLine(4).equalsIgnoreCase("\u00A77Grade \u00A77▸ " + Preffix))
            board.getValue().setLine(4, "\u00A77Grade \u00A77▸ " + Preffix);

            if (Suffix.contains("✰")) {
                if(!board.getValue().getLine(5).equalsIgnoreCase("\u00A77Premium \u00A77▸ \u00A7a\u00A7l✔"))
                board.getValue().setLine(5, "\u00A77Premium \u00A77▸ \u00A7a\u00A7l✔");
            } else {
                if(!board.getValue().getLine(5).equalsIgnoreCase("\u00A77Premium \u00A77▸ \u00A7c\u00A7lx"))
                board.getValue().setLine(5, "\u00A77Premium \u00A77▸ \u00A7c\u00A7lx");
            }

        }
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(main.instance, new Runnable() {
            public void run() {
                UpdateScoreBoard();
            }
        }, 20);
    }

    public void DeleteScoreBoard() {
        for (Entry<UUID, ScoreboardSign> board : boards.entrySet()) {
            board.getValue().destroy();
        }
    }
}
