package xyz.gucciclient.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.Timer;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectionHelper {

    public static MethodHandles.Lookup lookup;

    public static Field getField(Class class_, String... arrstring) {
        for (Field field : class_.getDeclaredFields()) {
            field.setAccessible(true);
            for (String string : arrstring) {
                if (!field.getName().equals(string)) {
                    continue;
                }
                return field;
            }
        }
        return null;
    }

    public static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }

    public static MethodHandle getLookupMethod(Class class_, MethodType methodType, String... arrstring) throws Throwable {
        for (String string : arrstring) {
            MethodHandle methodHandle = xyz.gucciclient.utils.ReflectionHelper.lookup().findVirtual(class_, string, methodType);
            if (methodHandle == null) {
                continue;
            }
            return methodHandle;
        }
        return null;
    }

    public static Method getMethod(Class class_, String... arrstring) {
        for (Method method : class_.getDeclaredMethods()) {
            method.setAccessible(true);
            for (String string : arrstring) {
                if (!method.getName().equals(string)) {
                    continue;
                }
                return method;
            }
        }
        return null;
    }

    public static float getPartialTicks() throws IllegalAccessException, Throwable {
        Field field = xyz.gucciclient.utils.ReflectionHelper.getField(Minecraft.class, "timer", "field_71428_T", "Y");
        Timer timer = (Timer) xyz.gucciclient.utils.ReflectionHelper.lookup().unreflectGetter(field).invoke(Minecraft.getMinecraft());
        return timer.renderPartialTicks;
    }

    public static double getRenderPosX() throws IllegalAccessException, Throwable {
        RenderManager rm = Wrapper.getMinecraft().getRenderManager();
        Field field = xyz.gucciclient.utils.ReflectionHelper.getField(RenderManager.class, "renderPosX", "field_78725_b", "o");
        field.setAccessible(true);
        return field.getDouble(rm);
    }

    public static double getRenderPosY() throws IllegalAccessException, Throwable {
        RenderManager rm = Wrapper.getMinecraft().getRenderManager();
        Field field = xyz.gucciclient.utils.ReflectionHelper.getField(RenderManager.class, "renderPosY", "field_78726_c", "p");
        field.setAccessible(true);
        return field.getDouble(rm);
    }

    public static double getRenderPosZ() throws IllegalAccessException, Throwable {
        RenderManager rm = Wrapper.getMinecraft().getRenderManager();
        Field field = xyz.gucciclient.utils.ReflectionHelper.getField(RenderManager.class, "renderPosZ", "field_78723_d", "q");
        field.setAccessible(true);
        return field.getDouble(rm);
    }

    /*public static double getRenderPosX() throws Throwable, IllegalAccessException {
        return (double) xyz.gucciclient.utils.ReflectionHelper.lookup().unreflectGetter(xyz.gucciclient.utils.ReflectionHelper.getField(RenderManager.class, "renderPosX", "field_78725_b", "o")).invoke(Minecraft.getMinecraft().getRenderManager());
    }

    public static double getRenderPosY() throws IllegalAccessException, Throwable {
        return (double) xyz.gucciclient.utils.ReflectionHelper.lookup().unreflectGetter(xyz.gucciclient.utils.ReflectionHelper.getField(RenderManager.class, "renderPosY", "field_78726_c", "p")).invoke(Minecraft.getMinecraft().getRenderManager());
    }

    public static double getRenderPosZ() throws IllegalAccessException, Throwable {
        return (double) xyz.gucciclient.utils.ReflectionHelper.lookup().unreflectGetter(xyz.gucciclient.utils.ReflectionHelper.getField(RenderManager.class, "renderPosZ", "field_78723_d", "q")).invoke(Minecraft.getMinecraft().getRenderManager());
    }*/

    public static void hookField(Field field, Object object, Object object2) throws IllegalAccessException, Throwable {
        xyz.gucciclient.utils.ReflectionHelper.lookup().unreflectSetter(field).invoke(object, object2);
    }

    public static Object invoke(Method method, Object... arrobject) throws Throwable {
        return xyz.gucciclient.utils.ReflectionHelper.lookup().unreflect(method).invoke(arrobject);
    }

    public static MethodHandles.Lookup lookup() {
        return lookup;
    }

    public static MethodHandle unreflect(Method method) throws IllegalAccessException {
        return xyz.gucciclient.utils.ReflectionHelper.lookup().unreflect(method);
    }
}
