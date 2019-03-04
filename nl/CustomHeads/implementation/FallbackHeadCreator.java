package nl.CustomHeads.implementation;

import com.mojang.authlib.GameProfile;
import nl.CustomHeads.IHeadCreator;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;

public class FallbackHeadCreator implements IHeadCreator {

   public ItemStack createItemStack(String url) {
      return new ItemStack(Material.PLAYER_HEAD);
   }

   public GameProfile createGameProfile(String url) {
      return null;
   }

   public boolean updateSkull(Skull skull, String url) {
      return false;
   }
}
