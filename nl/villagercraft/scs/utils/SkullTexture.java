package nl.villagercraft.scs.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.UUID;
import nl.villagercraft.scs.SecretPresents;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.event.Listener;

public class SkullTexture implements Listener {

   private static final Random random = new Random();
   private static final String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
   private static Method getWorldHandle;
   private static Method getWorldTileEntity;
   private static Method setGameProfile;
   private static Constructor getBlockPositionConst;
   private static Object getBlockPosition;


   public void load() {
      SecretPresents.p.getServer().getPluginManager().registerEvents(this, SecretPresents.p);
      if(getWorldHandle == null || getWorldTileEntity == null || setGameProfile == null) {
         try {
            getWorldHandle = getCraftClass("CraftWorld").getMethod("getHandle", new Class[0]);
            getBlockPositionConst = getMCClass("BlockPosition").getConstructor(new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE});
            getWorldTileEntity = getMCClass("WorldServer").getMethod("getTileEntity", new Class[]{getMCClass("BlockPosition")});
            setGameProfile = getMCClass("TileEntitySkull").getMethod("setGameProfile", new Class[]{GameProfile.class});
         } catch (SecurityException var2) {
            var2.printStackTrace();
         }
      }

   }

   public static void setSkullWithNonPlayerProfile(String skinURL, boolean randomName, Block skull) {
      if(skull.getType() != Material.PLAYER_HEAD) {
         throw new IllegalArgumentException("Block must be a skull.");
      } else {
         Skull s = (Skull)skull.getState();

         try {
            setSkullProfile(s, getNonPlayerProfile(skinURL, randomName));
         } catch (IllegalAccessException var5) {
            var5.printStackTrace();
         } catch (IllegalArgumentException var6) {
            var6.printStackTrace();
         } catch (InvocationTargetException var7) {
            var7.printStackTrace();
         } catch (SecurityException var8) {
            var8.printStackTrace();
         }

         skull.getWorld().refreshChunk(skull.getChunk().getX(), skull.getChunk().getZ());
      }
   }

   private static void setSkullProfile(Skull skull, GameProfile someGameprofile) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      Object world = getWorldHandle.invoke(skull.getWorld(), new Object[0]);
      Object tileSkull = null;

      try {
         Object e = getBlockPositionConst.newInstance(new Object[]{Integer.valueOf(skull.getX()), Integer.valueOf(skull.getY()), Integer.valueOf(skull.getZ())});
         tileSkull = getWorldTileEntity.invoke(world, new Object[]{e});
      } catch (InstantiationException var5) {
         var5.printStackTrace();
      }

      setGameProfile.invoke(tileSkull, new Object[]{someGameprofile});
   }

   public static GameProfile getNonPlayerProfile(String skinURL, boolean randomName) {
      GameProfile newSkinProfile = new GameProfile(UUID.randomUUID(), randomName?getRandomString(16):null);
      newSkinProfile.getProperties().put("textures", new Property("textures", skinURL));
      return newSkinProfile;
   }

   public static String getRandomString(int length) {
      StringBuilder b = new StringBuilder(length);

      for(int j = 0; j < length; ++j) {
         b.append("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(random.nextInt("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".length())));
      }

      return b.toString();
   }

   private static Class getMCClass(String name) {
      String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
      String className = "net.minecraft.server." + version + name;
      Class clazz = null;

      try {
         clazz = Class.forName(className);
      } catch (ClassNotFoundException var5) {
         var5.printStackTrace();
      }

      return clazz;
   }

   private static Class getCraftClass(String name) {
      String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
      String className = "org.bukkit.craftbukkit." + version + name;
      Class clazz = null;

      try {
         clazz = Class.forName(className);
      } catch (ClassNotFoundException var5) {
         var5.printStackTrace();
      }

      return clazz;
   }
}
