package today.exusiai.utils;

import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.GL11;

public class GLUtil {
   private static final Map<Integer, Boolean> glCapMap = new HashMap();

   public static void setGLCap(int cap, boolean flag) {
      glCapMap.put(Integer.valueOf(cap), Boolean.valueOf(GL11.glGetBoolean(cap)));
      if(flag) {
         GL11.glEnable(cap);
      } else {
         GL11.glDisable(cap);
      }

   }

   private static void revertGLCap(int cap) {
      Boolean obool = (Boolean)glCapMap.get(Integer.valueOf(cap));
      if(obool != null) {
         if(obool.booleanValue()) {
            GL11.glEnable(cap);
         } else {
            GL11.glDisable(cap);
         }
      }

   }

   public static void glEnable(int cap) {
      setGLCap(cap, true);
   }

   public static void glDisable(int cap) {
      setGLCap(cap, false);
   }

   public static void revertAllCaps() {
      for(Integer integer : glCapMap.keySet()) {
         revertGLCap(integer.intValue());
      }

   }
}
