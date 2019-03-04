package nl.villagercraft.scs.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import nl.villagercraft.scs.SecretPresents;
import nl.villagercraft.scs.utils.SCLang;
import nl.villagercraft.scs.utils.SCPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PresentsData {

   public static File f = new File(SecretPresents.p.getDataFolder(), "data.data");
   public static FileConfiguration data = YamlConfiguration.loadConfiguration(f);
   public static List locs = new ArrayList();


   public static void load() {
      locs.clear();
      Object list = data.getStringList("Presents") == null?new ArrayList():data.getStringList("Presents");
      Iterator var2 = ((List)list).iterator();

      while(var2.hasNext()) {
         String str = (String)var2.next();
         String[] s = str.split(",,");
         locs.add(new Location(Bukkit.getWorld(s[1]), (double)Integer.parseInt(s[2]), (double)Integer.parseInt(s[3]), (double)Integer.parseInt(s[4])));
      }

      SCPlayerData.ShitFixer();
   }

   public static Location IdToLocation(String id) {
      Object list = data.getStringList("Presents") == null?new ArrayList():data.getStringList("Presents");
      Iterator var3 = ((List)list).iterator();

      while(var3.hasNext()) {
         String str = (String)var3.next();
         String[] s = str.split(",,");
         if(s[0].equalsIgnoreCase(id)) {
            return new Location(Bukkit.getWorld(s[1]), (double)Integer.parseInt(s[2]), (double)Integer.parseInt(s[3]), (double)Integer.parseInt(s[4]));
         }
      }

      return null;
   }

   public static void addNew(Player p, Location loc) {
      SCPlayerData.ShitFixer();
      if(existsHere(loc)) {
         remove(p, loc);
      }

      Object list = data.getStringList("Presents") == null?new ArrayList():data.getStringList("Presents");
      String id = RandomString();
      ((List)list).add(id + ",," + loc.getWorld().getName() + ",," + loc.getBlockX() + ",," + loc.getBlockY() + ",," + loc.getBlockZ() + ",,rdm,,false");
      data.set("Presents", list);
      save();
      locs.add(loc);
      p.sendMessage(SCLang.getString("NewCreated"));
   }

   public static void remove(Player p, Location loc) {
      SCPlayerData.ShitFixer();
      Object list = data.getStringList("Presents") == null?new ArrayList():data.getStringList("Presents");
      int i = 0;

      for(Iterator var5 = ((List)list).iterator(); var5.hasNext(); ++i) {
         String str = (String)var5.next();
         String[] s = str.split(",,");
         if(loc.getWorld().getName().equalsIgnoreCase(s[1]) && loc.getBlockX() == Integer.parseInt(s[2]) && loc.getBlockY() == Integer.parseInt(s[3]) && loc.getBlockZ() == Integer.parseInt(s[4])) {
            ((List)list).remove(i);
            break;
         }
      }

      data.set("Presents", list);
      save();
      locs.remove(loc);
      loc.getBlock().setType(Material.AIR);
      p.sendMessage(SCLang.getString("Removed"));
   }

   public static String getCommand(Location loc) {
      String s = getPresent(loc).split(",,")[5];
      return s == null?null:s;
   }

   public static String fromID(String s) {
      Object list = data.getStringList("Presents") == null?new ArrayList():data.getStringList("Presents");
      Iterator var3 = ((List)list).iterator();

      while(var3.hasNext()) {
         String str = (String)var3.next();
         String[] st = str.split(",,");
         if(st[0].equals(s)) {
            return str;
         }
      }

      return null;
   }

   public static boolean existsHere(Location loc) {
      Iterator var2 = locs.iterator();

      while(var2.hasNext()) {
         Location l = (Location)var2.next();
         if(l.equals(loc)) {
            return true;
         }
      }

      return false;
   }

   public static String getID(Location loc) {
      if(data.getStringList("Presents") == null) {
         new ArrayList();
      } else {
         data.getStringList("Presents");
      }

      String pr = getPresent(loc);
      return pr != null?pr.split(",,")[0]:null;
   }

   public static String getPresent(Location loc) {
      Object list = data.getStringList("Presents") == null?new ArrayList():data.getStringList("Presents");
      Iterator var3 = ((List)list).iterator();

      String str;
      String[] s;
      do {
         if(!var3.hasNext()) {
            return null;
         }

         str = (String)var3.next();
         s = str.split(",,");
      } while(!loc.getWorld().getName().equalsIgnoreCase(s[1]) || loc.getBlockX() != Integer.parseInt(s[2]) || loc.getBlockY() != Integer.parseInt(s[3]) || loc.getBlockZ() != Integer.parseInt(s[4]));

      return str;
   }

   public static void save() {
      try {
         data.save(f);
      } catch (IOException var1) {
         var1.printStackTrace();
      }

   }

   public static String RandomString() {
      char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
      StringBuilder sb = new StringBuilder();
      Random random = new Random();

      for(int output = 0; output < 5; ++output) {
         char c = chars[random.nextInt(chars.length)];
         sb.append(c);
      }

      String var5 = sb.toString();
      return var5;
   }
}
