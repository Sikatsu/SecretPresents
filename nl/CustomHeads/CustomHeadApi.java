package nl.CustomHeads;

import java.util.logging.Level;
import java.util.logging.Logger;
import nl.CustomHeads.IHeadCreator;
import nl.CustomHeads.implementation.CustomHeadCreator;
import nl.CustomHeads.implementation.FallbackHeadCreator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomHeadApi extends JavaPlugin {

   private static final Logger s_log = Logger.getLogger("Minecraft.CustomHeadApi");
   private static String s_prefix = null;
   private static final String s_logFormat = "%s %s";
   private static CustomHeadApi s_instance;
   private IHeadCreator m_headCreator;


   public static CustomHeadApi getInstance() {
      return s_instance;
   }

   static String getPrefix() {
      return s_prefix;
   }

   public IHeadCreator getHeadCreator() {
      return this.m_headCreator;
   }

   public static void log(String msg) {
      if(s_log != null && msg != null && s_prefix != null) {
         s_log.log(Level.INFO, String.format("%s %s", new Object[]{s_prefix, msg}));
      }
   }

   public void onEnable() {
      s_instance = this;
      PluginDescriptionFile desc = this.getDescription();
      s_prefix = String.format("[%s]", new Object[]{desc.getName()});

      try {
         this.m_headCreator = new CustomHeadCreator();
         ItemStack er = this.m_headCreator.createItemStack("foo.org");
         if(er == null) {
            log("Something went wrong, using the fallback head creator. No custom heads available :(");
            log("Send the above message to the author of the plugin.");
            this.m_headCreator = new FallbackHeadCreator();
         }
      } catch (Error var7) {
         log("Something went wrong, using the fallback head creator. No custom heads available :(");
         log("----------------------------------------------------------------");
         log("Message: " + var7.getMessage());
         log("Stack: ");
         StackTraceElement[] var6;
         int var5 = (var6 = var7.getStackTrace()).length;

         for(int var4 = 0; var4 < var5; ++var4) {
            StackTraceElement element = var6[var4];
            log(" " + element.toString());
         }

         log("Send the above message to the author of the plugin.");
         log("----------------------------------------------------------------");
         this.m_headCreator = new FallbackHeadCreator();
      }

      log("Enabled");
   }

   public void onDisable() {
      log("Disabled");
   }
}
