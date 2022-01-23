package fr.verymc;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TABManager {

   public TABManager(){
      SendTab();
   }

   public void SendTab(){
      int online = ScoreBoardNMS.instance.online;
      Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Bukkit.getPluginManager().getPlugin("VeryLobby"), new Runnable() {
         public void run() {
            for(Player player : Bukkit.getOnlinePlayers()){
               BaseComponent header = new TextComponent("\n§f• §6§lVery§f§lMc §f•\n\n§7Global §7▸ §a"+online+
                 "\n§7Une question ? contacte un §9§lSTAFF §7!\n§7Tu veux le discord §d/discord\n");
               BaseComponent footer = new TextComponent(
                 "\n§7Serveur Mini-Jeux Francophone\n§7Vous êtes sur ▸ §fplay.§6§lvery§f§lmc§f.fr §7◂");
               player.setPlayerListHeaderFooter(header,footer);
            }
            SendTab();
         }
      }, 20);
   }
}
