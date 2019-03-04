package nl.villagercraft.scs.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import nl.villagercraft.scs.SecretPresents;
import nl.villagercraft.scs.utils.PresentsData;
import nl.villagercraft.scs.utils.SCEffect;
import nl.villagercraft.scs.utils.SCLang;
import nl.villagercraft.scs.utils.SCPlayer;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class SCPlayerData {

   public static File f = new File(SecretPresents.p.getDataFolder(), "player.data");
   public static FileConfiguration data = YamlConfiguration.loadConfiguration(f);
   public static List players = new ArrayList();


   public static void save(Player p) {
      save(get(p));
   }

   public static void save(SCPlayer p) {
      data.set(p.uuid.toString() + ".Found", p.presents);
      data.set(p.uuid.toString() + ".Start", Long.valueOf(p.start));
      data.set(p.uuid.toString() + ".End", Long.valueOf(p.finish));
      configsave();
   }

   public static void reset(Player p) {
      get(p).presents.clear();
      save(p);
   }

   public static void add(Location l, Player p) {
      String present = PresentsData.getID(l);
      SCPlayer pl = get(p);
      pl.clearRemoved();
      if(present != null) {
         if(hasPresent(pl, present)) {
            p.sendMessage(SCLang.getString("AlreadyFound"));
         } else {
            pl.presents.add(present);

            try {
               if(pl.presents.size() == 1) {
                  SCEffect.playFound(p, l, 0);
               } else if(pl.presents.size() == PresentsData.locs.size()) {
                  SCEffect.playFound(p, l, 2);
               } else {
                  SCEffect.playFound(p, l, 1);
               }
            } catch (Exception var5) {
               var5.printStackTrace();
            }

            save(pl);
         }
      }
   }

   public static boolean hasPresent(SCPlayer p, String s) {
      return p.presents.contains(s);
   }

   public static boolean hasPresent(SCPlayer p, Location l) {
      return p.presents.contains(PresentsData.getID(l));
   }

   public static int got(Player p) {
      return get(p).presents.size();
   }

   public static SCPlayer get(Player p) {
      SCPlayer pl = fromList(p);
      if(pl != null) {
         return pl;
      } else {
         SCPlayer pla;
         if(data.get(p.getUniqueId().toString()) != null) {
            pla = new SCPlayer(p);
            Object found = new ArrayList();
            if(data.getStringList(p.getUniqueId().toString() + ".Found") != null) {
               found = data.getStringList(p.getUniqueId().toString() + ".Found");
            }

            long s = data.getLong(p.getUniqueId().toString() + ".Start");
            long e = data.getLong(p.getUniqueId().toString() + ".End");
            pla.presents = (List)found;
            pla.start = s;
            pla.finish = e;
            pl = pla;
            players.add(pla);
         }

         if(pl == null) {
            pla = new SCPlayer(p);
            players.add(pla);
            return pla;
         } else {
            return pl;
         }
      }
   }

   public static SCPlayer fromList(Player p) {
      UUID uuid = p.getUniqueId();
      Iterator var3 = players.iterator();

      while(var3.hasNext()) {
         SCPlayer pl = (SCPlayer)var3.next();
         if(pl.uuid.equals(uuid)) {
            return pl;
         }
      }

      return null;
   }

   public static void ShitFixer() {
      Iterator var1 = data.getKeys(false).iterator();

      while(var1.hasNext()) {
         String player = (String)var1.next();
         List list = data.getStringList(player + ".Found");
         Iterator var4 = data.getStringList(player + ".Found").iterator();

         while(var4.hasNext()) {
            String unl = (String)var4.next();
            if(PresentsData.IdToLocation(unl) == null) {
               list.remove(unl);
               data.set(player + ".Found", list);
            }
         }
      }

   }

   public static void configsave() {
      try {
         data.save(f);
      } catch (IOException var1) {
         var1.printStackTrace();
      }

   }

   public static void load() {}
}
