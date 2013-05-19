package klukule.core;

import klukule.core.klukuleUtil;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class SettingsHelper {

   public static int addCommentAndReturnInt(Configuration config, String cat, String s, String comment, int i) {
      Property prop = config.get(cat, s, i);
      if(!comment.equalsIgnoreCase("")) {
         prop.comment = comment;
      }

      return prop.getInt();
   }

   public static int addCommentAndReturnInt(Configuration config, String cat, String s, String comment, String value) {
      Property prop = config.get(cat, s, value);
      if(!comment.equalsIgnoreCase("")) {
         prop.comment = comment;
      }

      int val = 16777215;

      try {
         val = Integer.decode(prop.getString()).intValue();
      } catch (NumberFormatException var8) {
         klukuleUtil.console("Cannot decode colour index: " + s, true);
         var8.printStackTrace();
      }

      return val;
   }

   public static int addCommentAndReturnItemID(Configuration config, String cat, String s, String comment, int i) {
      Property prop = config.getItem(cat, s, i);
      if(!comment.equalsIgnoreCase("")) {
         prop.comment = comment;
      }

      return prop.getInt();
   }

   public static String addCommentAndReturnString(Configuration config, String cat, String s, String comment, String value) {
      Property prop = config.get(cat, s, value);
      if(!comment.equalsIgnoreCase("")) {
         prop.comment = comment;
      }

      return prop.getString();
   }
}
