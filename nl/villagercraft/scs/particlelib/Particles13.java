package nl.villagercraft.scs.particlelib;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import nl.villagercraft.scs.particlelib.NewParticles;
import nl.villagercraft.scs.particlelib.ParticleEffect;
import nl.villagercraft.scs.particlelib.ReflectionUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Particles13 {

   static Method getHandle;
   static Method sendPacket;
   static Field playerConnection;
   static Constructor packetConstructor;
   static Constructor particleConstructor;
   static Constructor particleitemConstructor;
   static Object packet;
   static Object particle;
   static Object item;
   public static ArrayList materials = new ArrayList();
   static Class CraftIS = null;
   static Class particles = null;


   public static void initialize() {
      try {
         Class exception = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutWorldParticles");
         CraftIS = ReflectionUtils.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack");
         Class particleClass = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("ParticleParamRedstone");
         Class particleparamClass = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("ParticleParam");
         Class particleItemClass = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("ParticleParamItem");
         packetConstructor = exception.getConstructor(new Class[]{particleparamClass, Boolean.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Integer.TYPE});
         particleConstructor = particleClass.getConstructor(new Class[]{Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE});
         particles = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("Particles");
         particleitemConstructor = particleItemClass.getConstructor(new Class[]{ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("Particle"), ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("ItemStack")});
         particle = particleConstructor.newInstance(new Object[]{Float.valueOf(1.0F), Float.valueOf(1.0F), Float.valueOf(1.0F), Float.valueOf(0.9F)});
         packet = packetConstructor.newInstance(new Object[]{particle, Boolean.valueOf(false), Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(0.0F), Integer.valueOf(1)});
         getHandle = ReflectionUtils.getMethod("CraftPlayer", ReflectionUtils.PackageType.CRAFTBUKKIT_ENTITY, "getHandle", new Class[0]);
         playerConnection = ReflectionUtils.getField("EntityPlayer", ReflectionUtils.PackageType.MINECRAFT_SERVER, false, "playerConnection");
         sendPacket = ReflectionUtils.getMethod(playerConnection.getType(), "sendPacket", new Class[]{ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("Packet")});
         createNewNMSItem("BONE_MEAL");
         createNewNMSItem("ROSE_RED");
         createNewNMSItem("DANDELION_YELLOW");
         createNewNMSItem("CACTUS_GREEN");
         createNewNMSItem("LAPIS_LAZULI");
         createNewNMSItem("PURPLE_DYE");
         createNewNMSItem("INK_SAC");
         item = particleitemConstructor.newInstance(new Object[]{ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("Particles").getField("C").get((Object)null), materials.get(0)});
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   public static void createNewNMSItem(String itemname) {
      try {
         Class e = Class.forName("org.bukkit.Material");
         Method m = e.getMethod("getMaterial", new Class[]{String.class, Boolean.TYPE});
         Material mat = (Material)m.invoke((Object)null, new Object[]{itemname, Boolean.valueOf(false)});
         ItemStack is = new ItemStack(mat);
         Method asNMS = CraftIS.getMethod("asNMSCopy", new Class[]{ItemStack.class});
         Object nmsitemcopy = asNMS.invoke((Object)null, new Object[]{is});
         materials.add(nmsitemcopy);
      } catch (Exception var7) {
         var7.printStackTrace();
      }

   }

   public static int r(int min, int max) {
      Random rand = new Random();
      int randomNum = rand.nextInt(max - min + 1) + min;
      return randomNum;
   }

   public static void play(ParticleEffect eff, Location l, float offsetX, float offsetY, float offsetZ, float speed, int amount, Player p) {
      if(eff == ParticleEffect.REDSTONE) {
         playColoredEffect(new ParticleEffect.OrdinaryColor(r(0, 255), r(0, 255), r(0, 255)), l, offsetX, offsetY, offsetZ, speed, amount);
      } else if(eff == ParticleEffect.SNOW_SHOVEL) {
         playColoredEffect(new ParticleEffect.OrdinaryColor(10, 10, 10), l, offsetX, offsetY, offsetZ, speed, amount);
      } else {
         if(eff == ParticleEffect.NOTE) {
            speed = 1.0F;
         }

         String name = NewParticles.convertToID(eff);
         if(name != null) {
            try {
               packet = packetConstructor.newInstance(new Object[]{particle, Boolean.valueOf(false), Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(0.0F), Integer.valueOf(1)});
               ReflectionUtils.setValue(packet, true, "j", particles.getField(name).get((Object)null));
               ReflectionUtils.setValue(packet, true, "i", Boolean.valueOf(false));
               ReflectionUtils.setValue(packet, true, "a", Float.valueOf((float)l.getX()));
               ReflectionUtils.setValue(packet, true, "b", Float.valueOf((float)l.getY()));
               ReflectionUtils.setValue(packet, true, "c", Float.valueOf((float)l.getZ()));
               ReflectionUtils.setValue(packet, true, "d", Float.valueOf(offsetX));
               ReflectionUtils.setValue(packet, true, "e", Float.valueOf(offsetY));
               ReflectionUtils.setValue(packet, true, "f", Float.valueOf(offsetZ));
               ReflectionUtils.setValue(packet, true, "g", Float.valueOf(speed));
               ReflectionUtils.setValue(packet, true, "h", Integer.valueOf(amount));
               send(p, packet);
            } catch (Exception var10) {
               var10.printStackTrace();
            }

         }
      }
   }

   public static void playColoredEffect(ParticleEffect.OrdinaryColor color, Location l, float offsetX, float offsetY, float offsetZ, float speed, int amount) {
      double redd = (double)color.getRed() / 255.0D;
      double green = (double)color.getGreen() / 255.0D;
      double blue = (double)color.getBlue() / 255.0D;
      redd = redd < 0.1D?0.1D:redd;
      blue = blue < 0.1D?0.1D:blue;
      green = green < 0.1D?0.1D:green;

      try {
         particle = particleConstructor.newInstance(new Object[]{Float.valueOf(1.0F), Float.valueOf(1.0F), Float.valueOf(1.0F), Float.valueOf(0.9F)});
         ReflectionUtils.setValue(particle, true, "c", Float.valueOf((float)redd));
         ReflectionUtils.setValue(particle, true, "d", Float.valueOf((float)green));
         ReflectionUtils.setValue(particle, true, "e", Float.valueOf((float)blue));
         packet = packetConstructor.newInstance(new Object[]{particle, Boolean.valueOf(false), Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(0.0F), Integer.valueOf(1)});
         ReflectionUtils.setValue(packet, true, "j", particle);
         ReflectionUtils.setValue(packet, true, "i", Boolean.valueOf(false));
         ReflectionUtils.setValue(packet, true, "a", Float.valueOf((float)l.getX()));
         ReflectionUtils.setValue(packet, true, "b", Float.valueOf((float)l.getY()));
         ReflectionUtils.setValue(packet, true, "c", Float.valueOf((float)l.getZ()));
         ReflectionUtils.setValue(packet, true, "d", Float.valueOf(offsetX));
         ReflectionUtils.setValue(packet, true, "e", Float.valueOf(offsetY));
         ReflectionUtils.setValue(packet, true, "f", Float.valueOf(offsetZ));
         ReflectionUtils.setValue(packet, true, "g", Float.valueOf(0.0F));
         ReflectionUtils.setValue(packet, true, "h", Integer.valueOf(amount));
         sendAll(l, packet);
      } catch (Exception var14) {
         var14.printStackTrace();
      }

   }

   public static void playItemCrack(Object NMSitem, Location l, float offsetX, float offsetY, float offsetZ, float speed, int amount) {
      try {
         item = particleitemConstructor.newInstance(new Object[]{ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("Particles").getField("C").get((Object)null), NMSitem});
         packet = packetConstructor.newInstance(new Object[]{particle, Boolean.valueOf(false), Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(0.0F), Integer.valueOf(1)});
         ReflectionUtils.setValue(packet, true, "j", item);
         ReflectionUtils.setValue(packet, true, "i", Boolean.valueOf(false));
         ReflectionUtils.setValue(packet, true, "a", Float.valueOf((float)l.getX()));
         ReflectionUtils.setValue(packet, true, "b", Float.valueOf((float)l.getY()));
         ReflectionUtils.setValue(packet, true, "c", Float.valueOf((float)l.getZ()));
         ReflectionUtils.setValue(packet, true, "d", Float.valueOf(offsetX));
         ReflectionUtils.setValue(packet, true, "e", Float.valueOf(offsetY));
         ReflectionUtils.setValue(packet, true, "f", Float.valueOf(offsetZ));
         ReflectionUtils.setValue(packet, true, "g", Float.valueOf(speed));
         ReflectionUtils.setValue(packet, true, "h", Integer.valueOf(amount));
         sendAll(l, packet);
      } catch (Exception var8) {
         var8.printStackTrace();
      }

   }

   public static void sendAll(Location l, Object packet) {
      try {
         Iterator var3 = l.getWorld().getPlayers().iterator();

         while(var3.hasNext()) {
            Player e = (Player)var3.next();
            if(l.distance(e.getLocation()) < 20.0D) {
               send(e, packet);
            }
         }
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   public static void send(Player p, Object packet) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      sendPacket.invoke(playerConnection.get(getHandle.invoke(p, new Object[0])), new Object[]{packet});
   }
}
