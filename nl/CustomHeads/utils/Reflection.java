package nl.CustomHeads.utils;

import java.lang.reflect.Field;
import nl.CustomHeads.CustomHeadApi;

public class Reflection {

   public static Object get(Class sourceClass, Class fieldClass, String fieldName, String message) {
      return get(sourceClass, fieldClass, (Object)null, fieldName, message);
   }

   public static Object get(Object instance, Class fieldClass, String fieldName, String message) {
      return get(instance.getClass(), fieldClass, instance, fieldName, message);
   }

   public static Object get(Class sourceClass, Class fieldClass, Object instance, String fieldName, String message) {
      try {
         Field ex = sourceClass.getDeclaredField(fieldName);
         ex.setAccessible(true);
         Field modifiersField = Field.class.getDeclaredField("modifiers");
         boolean accessible = modifiersField.isAccessible();
         if(!accessible) {
            modifiersField.setAccessible(true);
         }

         Object var9;
         try {
            var9 = fieldClass.cast(ex.get(instance));
         } finally {
            if(!accessible) {
               modifiersField.setAccessible(false);
            }

         }

         return var9;
      } catch (IllegalArgumentException var17) {
         CustomHeadApi.log(message + ": unsupported WorldEdit version.");
      } catch (IllegalAccessException var18) {
         CustomHeadApi.log(message + ": security exception.");
      } catch (NoSuchFieldException var19) {
         CustomHeadApi.log(message + ": unsupported WorldEdit version, field " + fieldName + " not found.");
      } catch (SecurityException var20) {
         CustomHeadApi.log(message + ": security exception.");
      } catch (ClassCastException var21) {
         CustomHeadApi.log(message + ": unsupported WorldEdit version, unable to cast result.");
      }

      return null;
   }

   public static boolean set(Object instance, String fieldName, Object value, String message) {
      return set(instance.getClass(), instance, fieldName, value, message);
   }

   public static boolean set(Class sourceClass, String fieldName, Object value, String message) {
      return set(sourceClass, (Object)null, fieldName, value, message);
   }

   public static boolean set(Class sourceClass, Object instance, String fieldName, Object value, String message) {
      try {
         Field ex = sourceClass.getDeclaredField(fieldName);
         boolean accessible = ex.isAccessible();
         Field modifiersField = Field.class.getDeclaredField("modifiers");
         int modifiers = modifiersField.getModifiers();
         boolean isFinal = (modifiers & 16) == 16;
         if(!accessible) {
            ex.setAccessible(true);
         }

         if(isFinal) {
            modifiersField.setAccessible(true);
            modifiersField.setInt(ex, modifiers & -17);
         }

         try {
            ex.set(instance, value);
         } finally {
            if(isFinal) {
               modifiersField.setInt(ex, modifiers | 16);
            }

            if(!accessible) {
               ex.setAccessible(false);
            }

         }

         return true;
      } catch (IllegalArgumentException var17) {
         CustomHeadApi.log(message + ": unsupported WorldEdit version.");
      } catch (IllegalAccessException var18) {
         CustomHeadApi.log(message + ": security exception.");
      } catch (NoSuchFieldException var19) {
         CustomHeadApi.log(message + ": unsupported WorldEdit version, field " + fieldName + " not found.");
      } catch (SecurityException var20) {
         CustomHeadApi.log(message + ": security exception.");
      }

      return false;
   }

   public static boolean set(Object instance, Field field, Object value, String message) {
      try {
         boolean ex = field.isAccessible();
         Field modifiersField = Field.class.getDeclaredField("modifiers");
         int modifiers = modifiersField.getModifiers();
         boolean isFinal = (modifiers & 16) == 16;
         if(!ex) {
            field.setAccessible(true);
         }

         if(isFinal) {
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, modifiers & -17);
         }

         try {
            field.set(instance, value);
         } finally {
            if(isFinal) {
               modifiersField.setInt(field, modifiers | 16);
            }

            if(!ex) {
               field.setAccessible(false);
            }

         }

         return true;
      } catch (IllegalArgumentException var15) {
         CustomHeadApi.log(message + ": unsupported WorldEdit version.");
      } catch (IllegalAccessException var16) {
         CustomHeadApi.log(message + ": security exception.");
      } catch (NoSuchFieldException var17) {
         CustomHeadApi.log(message + ": unsupported WorldEdit version, field modifiers not found.");
      } catch (SecurityException var18) {
         CustomHeadApi.log(message + ": security exception.");
      }

      return false;
   }
}
