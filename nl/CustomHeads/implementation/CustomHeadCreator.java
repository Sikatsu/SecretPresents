package nl.CustomHeads.implementation;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import java.util.UUID;
import nl.CustomHeads.CustomHeadApi;
import nl.CustomHeads.IHeadCreator;
import nl.CustomHeads.utils.Reflection;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomHeadCreator implements IHeadCreator {

   private final Base64 m_base64 = new Base64();


   public GameProfile createGameProfile(String url) {
      GameProfile profile = new GameProfile(UUID.randomUUID(), (String)null);
      PropertyMap propertyMap = profile.getProperties();
      if(propertyMap == null) {
         CustomHeadApi.log("No property map found in GameProfile, can\'t continue.");
         return null;
      } else {
         propertyMap.put("textures", new Property("textures", url));
         return profile;
      }
   }

   public ItemStack createItemStack(String url) {
      GameProfile profile = this.createGameProfile(url);
      if(profile == null) {
         return null;
      } else {
         ItemStack head = new ItemStack(Material.PLAYER_HEAD);
         ItemMeta headMeta = head.getItemMeta();
         Class headMetaClass = headMeta.getClass();
         if(!Reflection.set(headMetaClass, headMeta, "profile", profile, "Unable to inject porofile")) {
            return null;
         } else {
            head.setItemMeta(headMeta);
            return head;
         }
      }
   }

   public boolean updateSkull(Skull skull, String url) {
      if(skull == null) {
         return false;
      } else {
         GameProfile profile = this.createGameProfile(url);
         if(profile == null) {
            return false;
         } else {
            skull.setSkullType(SkullType.PLAYER);
            Class skullClass = skull.getClass();
            if(!Reflection.set(skullClass, skull, "profile", profile, "Unable to inject porofile")) {
               return false;
            } else {
               skull.update();
               return true;
            }
         }
      }
   }
}
