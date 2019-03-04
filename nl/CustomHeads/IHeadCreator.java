package nl.CustomHeads;

import com.mojang.authlib.GameProfile;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;

public interface IHeadCreator {

   GameProfile createGameProfile(String var1);

   ItemStack createItemStack(String var1);

   boolean updateSkull(Skull var1, String var2);
}
