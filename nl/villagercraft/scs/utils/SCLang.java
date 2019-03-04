package nl.villagercraft.scs.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import nl.villagercraft.scs.SecretPresents;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class SCLang {

   public static File lang = new File(SecretPresents.p.getDataFolder(), "language.yml");
   public static FileConfiguration lcon;


   public static void load() {
      if(!lang.exists()) {
         try {
            copy(getInputFromJar("language.yml"), new File(SecretPresents.p.getDataFolder(), "language.yml"));
         } catch (IOException var1) {
            var1.printStackTrace();
         }
      }

      lcon = YamlConfiguration.loadConfiguration(lang);
   }

   public static String getString(String link) {
      return lcon.getString(link).replaceAll("&", "§");
   }

   public static List getStringList(String link) {
      ArrayList list = new ArrayList();
      Iterator var3 = lcon.getStringList(link).iterator();

      while(var3.hasNext()) {
         String s = (String)var3.next();
         list.add(s.replaceAll("&", "§"));
      }

      return list;
   }

   public static InputStream getInputFromJar(String filename) throws IOException {
      if(filename == null) {
         throw new IllegalArgumentException("The path can not be null");
      } else {
         InputStream url = SecretPresents.p.getResource(filename);
         return url == null?null:url;
      }
   }

   public static void copy(InputStream input, File target) throws IOException {
      if(target.exists()) {
         target.delete();
      }

      File parentDir = target.getParentFile();
      if(parentDir.isDirectory()) {
         if(target.createNewFile()) {
            byte[] buffer = new byte[1024];
            FileOutputStream output = new FileOutputStream(target);

            int realLength;
            while((realLength = input.read(buffer)) > 0) {
               output.write(buffer, 0, realLength);
            }

            output.flush();
            output.close();
         }
      }
   }
}
