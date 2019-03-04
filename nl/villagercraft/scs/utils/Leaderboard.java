package nl.villagercraft.scs.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import nl.villagercraft.scs.SecretPresents;
import nl.villagercraft.scs.utils.SCEffect;
import nl.villagercraft.scs.utils.SCLang;
import nl.villagercraft.scs.utils.SCPlayerData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Leaderboard {

   public static List leaderboard = new ArrayList();
   public static File f = new File(SecretPresents.p.getDataFolder(), "leaderboard.data");
   public static FileConfiguration data = YamlConfiguration.loadConfiguration(f);


   public static void getBest(Player p, int page) {
      String first = leaderboard.size() > 0 + 10 * page?getLName((String)leaderboard.get(0 + 10 * page)):SCLang.getString("LeaderboardNoPlayer");
      String second = leaderboard.size() > 1 + 10 * page?getLName((String)leaderboard.get(1 + 10 * page)):SCLang.getString("LeaderboardNoPlayer");
      String third = leaderboard.size() > 2 + 10 * page?getLName((String)leaderboard.get(2 + 10 * page)):SCLang.getString("LeaderboardNoPlayer");
      String p4 = leaderboard.size() > 3 + 10 * page?getLName((String)leaderboard.get(3 + 10 * page)):SCLang.getString("LeaderboardNoPlayer");
      String p5 = leaderboard.size() > 4 + 10 * page?getLName((String)leaderboard.get(4 + 10 * page)):SCLang.getString("LeaderboardNoPlayer");
      String p6 = leaderboard.size() > 5 + 10 * page?getLName((String)leaderboard.get(5 + 10 * page)):SCLang.getString("LeaderboardNoPlayer");
      String p7 = leaderboard.size() > 6 + 10 * page?getLName((String)leaderboard.get(6 + 10 * page)):SCLang.getString("LeaderboardNoPlayer");
      String p8 = leaderboard.size() > 7 + 10 * page?getLName((String)leaderboard.get(7 + 10 * page)):SCLang.getString("LeaderboardNoPlayer");
      String p9 = leaderboard.size() > 8 + 10 * page?getLName((String)leaderboard.get(8 + 10 * page)):SCLang.getString("LeaderboardNoPlayer");
      String p10 = leaderboard.size() > 9 + 10 * page?getLName((String)leaderboard.get(9 + 10 * page)):SCLang.getString("LeaderboardNoPlayer");
      String firsttime = leaderboard.size() > 0 + 10 * page?getLTime((String)leaderboard.get(0 + 10 * page)):"";
      String secondtime = leaderboard.size() > 1 + 10 * page?getLTime((String)leaderboard.get(1 + 10 * page)):"";
      String thirdtime = leaderboard.size() > 2 + 10 * page?getLTime((String)leaderboard.get(2 + 10 * page)):"";
      String p4time = leaderboard.size() > 3 + 10 * page?getLTime((String)leaderboard.get(3 + 10 * page)):"";
      String p5time = leaderboard.size() > 4 + 10 * page?getLTime((String)leaderboard.get(4 + 10 * page)):"";
      String p6time = leaderboard.size() > 5 + 10 * page?getLTime((String)leaderboard.get(5 + 10 * page)):"";
      String p7time = leaderboard.size() > 6 + 10 * page?getLTime((String)leaderboard.get(6 + 10 * page)):"";
      String p8time = leaderboard.size() > 7 + 10 * page?getLTime((String)leaderboard.get(7 + 10 * page)):"";
      String p9time = leaderboard.size() > 8 + 10 * page?getLTime((String)leaderboard.get(8 + 10 * page)):"";
      String p10time = leaderboard.size() > 9 + 10 * page?getLTime((String)leaderboard.get(9 + 10 * page)):"";
      String playertime = SCPlayerData.get(p).finish == 0L?SCLang.getString("LeaderboardNoPlayer"):gettime(p);
      Iterator var24 = SCLang.getStringList("Leaderboard").iterator();

      while(var24.hasNext()) {
         String s = (String)var24.next();
         p.sendMessage(s.replaceAll("!1!", first).replaceAll("!2!", second).replaceAll("!3!", third).replaceAll("!4!", p4).replaceAll("!5!", p5).replaceAll("!6!", p6).replaceAll("!7!", p7).replaceAll("!8!", p8).replaceAll("!9!", p9).replaceAll("!10!", p10).replaceAll("!p!", "").replaceAll("!time!", playertime).replaceAll("!time1!", firsttime).replaceAll("!time2!", secondtime).replaceAll("!time3!", thirdtime).replaceAll("!time4!", p4time).replaceAll("!time5!", p5time).replaceAll("!time6!", p6time).replaceAll("!time7!", p7time).replaceAll("!time8!", p8time).replaceAll("!time9!", p9time).replaceAll("!time10!", p10time).replaceAll("!n1!", String.valueOf(1 + 10 * page)).replaceAll("!n2!", String.valueOf(2 + 10 * page)).replaceAll("!n3!", String.valueOf(3 + 10 * page)).replaceAll("!n4!", String.valueOf(4 + 10 * page)).replaceAll("!n5!", String.valueOf(5 + 10 * page)).replaceAll("!n6!", String.valueOf(6 + 10 * page)).replaceAll("!n7!", String.valueOf(7 + 10 * page)).replaceAll("!n8!", String.valueOf(8 + 10 * page)).replaceAll("!n9!", String.valueOf(9 + 10 * page)).replaceAll("!n10!", String.valueOf(10 + 10 * page)));
      }

      if(page == 0) {
         SecretPresents.sendRaw(p, "[\"\",{\"text\":\">>>\",\"color\":\"dark_aqua\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/sps leaderboard " + (page + 1) + "\"}}]");
      } else if(page == 100) {
         SecretPresents.sendRaw(p, "[\"\",{\"text\":\"<<<\",\"color\":\"dark_aqua\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/sps leaderboard " + (page - 1) + "\"}}]");
      } else {
         SecretPresents.sendRaw(p, "[\"\",{\"text\":\"<<<\",\"color\":\"dark_aqua\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/sps leaderboard " + (page - 1) + "\"}},{\"text\":\"  \",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/mycommand\"},\"color\":\"none\",\"bold\":false},{\"text\":\">>>\",\"color\":\"dark_aqua\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/sps leaderboard " + (page + 1) + "\"}}]");
      }

   }

   public static void addResult(Player p) {
      int place = 0;
      boolean bigger = false;
      long pl = SCPlayerData.get(p).finish - SCPlayerData.get(p).start;

      for(Iterator var6 = leaderboard.iterator(); var6.hasNext(); ++place) {
         String s = (String)var6.next();
         long l = Long.parseLong(s.split(";")[1]);
         if(pl < l) {
            bigger = true;
            break;
         }
      }

      if(bigger) {
         leaderboard.add(place, p.getName() + ";" + pl);
      } else {
         leaderboard.add(p.getName() + ";" + pl);
      }

      save();
   }

   public static void load() {
      leaderboard = data.getStringList("Leaderboard");
   }

   public static void save() {
      data.set("Leaderboard", leaderboard);

      try {
         data.save(f);
      } catch (IOException var1) {
         var1.printStackTrace();
      }

   }

   public static String getLName(String s) {
      return s.split(";")[0];
   }

   public static String getLTime(String s) {
      return SCEffect.getBetween(0L, Long.parseLong(s.split(";")[1]));
   }

   public static String gettime(Player p) {
      return SCEffect.getBetween(SCPlayerData.get(p).start, SCPlayerData.get(p).finish);
   }

   public static void w() {}
}
