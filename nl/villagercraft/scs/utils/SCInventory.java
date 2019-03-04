package nl.villagercraft.scs.utils;

import com.deanveloper.skullcreator.SkullCreator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import nl.CustomHeads.implementation.CustomHeadCreator;
import nl.villagercraft.scs.SecretPresents;
import nl.villagercraft.scs.utils.PresentsData;
import nl.villagercraft.scs.utils.SCLang;
import nl.villagercraft.scs.utils.SCPlayer;
import nl.villagercraft.scs.utils.SCPlayerData;
import nl.villagercraft.scs.utils.Texture;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SCInventory implements Listener {

   public static HashMap clicked = new HashMap();
   public static CustomHeadCreator ihc = new CustomHeadCreator();


   public static void openInventory(Player p, Location l) {
      clicked.put(p, l);
      Inventory inv = Bukkit.createInventory((InventoryHolder)null, 27, "SecretPresents§r");
      inv.setItem(10, getIS(Material.ITEM_FRAME, (byte)0, SCLang.getString("ChangeSkin"), " ; " + SCLang.getString("ClickToMenu")));
      String pr = PresentsData.getPresent(l);
      String cmd = SCLang.getString("NoCmd");
      if(!pr.split(",,")[5].equalsIgnoreCase("rdm")) {
         cmd = "§f" + pr.split(",,")[5];
      }

      inv.setItem(13, getIS(Material.PAPER, (byte)0, SCLang.getString("ItemInfo"), "§7ID: " + pr.split(",,")[0] + ";" + SCLang.getString("ItLoreCommand") + " " + cmd));
      inv.setItem(16, getIS(Material.RED_WOOL, (byte)0, SCLang.getString("Remove"), " ;" + SCLang.getString("ClickToRemove")));
      p.openInventory(inv);
   }

   @EventHandler
   public void onClick(InventoryClickEvent e) {
      Player p;
      if(e.getInventory().getName().equals("SecretPresents")) {
         e.setCancelled(true);
         p = (Player)e.getWhoClicked();
         if(!p.hasPermission("secretpresents.access")) {
            return;
         }

         if(!clicked.containsKey((Player)e.getWhoClicked())) {
            e.getWhoClicked().closeInventory();
            return;
         }

         if(e.getRawSlot() == 10) {
            openInventorySkins(p);
         } else if(e.getRawSlot() == 16) {
            PresentsData.remove(p, (Location)clicked.get(p));
            p.closeInventory();
            Iterator var4 = SCPlayerData.players.iterator();

            while(var4.hasNext()) {
               SCPlayer list = (SCPlayer)var4.next();
               list.clearRemoved();
            }
         }
      } else if(e.getInventory().getName().equals("SecretPresents: Skin")) {
         p = (Player)e.getWhoClicked();
         if(!p.hasPermission("secretpresents.access")) {
            return;
         }

         e.setCancelled(true);
         if(!clicked.containsKey(p)) {
            p.closeInventory();
            return;
         }

         List list1 = SecretPresents.p.getConfig().getStringList("Textures");
         if(e.getRawSlot() > list1.size() - 1) {
            return;
         }

         Texture.apply(((Location)clicked.get(p)).getBlock(), (String)list1.get(e.getRawSlot()), p);
      }

   }

   public static void openInventorySkins(Player p) {
      Inventory inv = Bukkit.createInventory((InventoryHolder)null, 54, "SecretPresents: Skin");
      int i = 0;

      for(Iterator var4 = SecretPresents.p.getConfig().getStringList("Textures").iterator(); var4.hasNext(); ++i) {
         String s = (String)var4.next();
         ItemStack is = SkullCreator.itemFromBase64(s);
         inv.setItem(i, is);
      }

      p.openInventory(inv);
   }

   public static ItemStack getIS(Material m, byte b, String name, String lor) {
      ItemStack is = new ItemStack(m, 1, b);
      ItemMeta im = is.getItemMeta();
      im.setDisplayName(name);
      ArrayList lorlist = new ArrayList();
      String[] var10;
      int var9 = (var10 = lor.split(";")).length;

      for(int var8 = 0; var8 < var9; ++var8) {
         String strlor = var10[var8];
         lorlist.add(strlor);
      }

      im.setLore(lorlist);
      is.setItemMeta(im);
      return is;
   }
}
