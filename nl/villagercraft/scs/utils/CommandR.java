package nl.villagercraft.scs.utils;

import java.util.Iterator;
import nl.villagercraft.scs.SecretPresents;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandR {

   public static void runCommand(String s) {
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s);
   }

   public static void giveReward(Player p) {
      Iterator var2 = SecretPresents.p.getConfig().getStringList("Commands").iterator();

      while(var2.hasNext()) {
         String cmd = (String)var2.next();
         runCommand(cmd.replaceAll("!player!", p.getName()));
      }

   }
}
