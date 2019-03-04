package nl.villagercraft.scs.events;

import nl.villagercraft.scs.utils.PresentsData;
import nl.villagercraft.scs.utils.Texture;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignEvent implements Listener {

   @EventHandler
   public void Sign(SignChangeEvent e) {
      if(e.getPlayer().hasPermission("secretpresents.access")) {
         if(e.getLine(0).equalsIgnoreCase("[present]")) {
            e.setCancelled(true);
            BlockFace face = getBlockFace((Sign)e.getBlock().getState());
            Material mat = e.getBlock().getType();
            byte b = e.getBlock().getData();
            e.getBlock().setType(Material.PLAYER_HEAD);
            Skull skull = (Skull)e.getBlock().getState();
            skull.update();
            if(mat.equals(Material.WALL_SIGN)) {
               e.getBlock().setType(Material.PLAYER_WALL_HEAD);
            } else {
               skull.setRotation(face);
               skull.update();
            }

            Texture.apply(e.getBlock(), e.getPlayer());
            PresentsData.addNew(e.getPlayer(), e.getBlock().getLocation());
         }
      }
   }

   public static BlockFace getBlockFace(Sign sign) {
      byte data = sign.getBlock().getData();
      switch(data) {
      case 0:
         return BlockFace.NORTH;
      case 1:
         return BlockFace.NORTH_NORTH_EAST;
      case 2:
         return BlockFace.NORTH_EAST;
      case 3:
         return BlockFace.EAST_NORTH_EAST;
      case 4:
         return BlockFace.EAST;
      case 5:
         return BlockFace.EAST_SOUTH_EAST;
      case 6:
         return BlockFace.SOUTH_EAST;
      case 7:
         return BlockFace.SOUTH_SOUTH_EAST;
      case 8:
         return BlockFace.SOUTH;
      case 9:
         return BlockFace.SOUTH_SOUTH_WEST;
      case 10:
         return BlockFace.SOUTH_WEST;
      case 11:
         return BlockFace.WEST_SOUTH_WEST;
      case 12:
         return BlockFace.WEST;
      case 13:
         return BlockFace.WEST_NORTH_WEST;
      case 14:
         return BlockFace.NORTH_WEST;
      case 15:
         return BlockFace.NORTH_NORTH_WEST;
      default:
         return BlockFace.SELF;
      }
   }
}
