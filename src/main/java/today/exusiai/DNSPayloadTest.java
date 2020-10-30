package today.exusiai;

import java.lang.reflect.*;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import today.exusiai.utils.Wrapper;

public class DNSPayloadTest
{
    private static final String CLASS_NAME = "net.minecraft.network.play.client.C17PacketCustomPayload";
    private static final Constructor<?> CLASS_CONSTRUCTOR;
    private static final Field NETWORK_MANAGER_FIELD;
    private static final Field NET_HANDLER_FIELD;
    private static final Field CHANNEL_FIELD;
    private final Object instance;
    
    static {
        Class<?> clazz;
        try {
            clazz = Class.forName("net.minecraft.network.play.client.C17PacketCustomPayload");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Fuck");
        }
        Constructor<?> constructor = null;
        try {
            constructor = clazz.getConstructor(String.class, PacketBuffer.class);
        }
        catch (NoSuchMethodException e2) {
            e2.printStackTrace();
        }
        CLASS_CONSTRUCTOR = constructor;
        try {
            clazz = Class.forName("net.minecraft.network.NetworkManager");
        }
        catch (ClassNotFoundException e3) {
            e3.printStackTrace();
        }
        Field fidChannel = null;
        Field[] declaredFields;
        for (int length = (declaredFields = clazz.getDeclaredFields()).length, i = 0; i < length; ++i) {
            final Field field = declaredFields[i];
            try {
                if (field.getType() == Class.forName("io.netty.channel.Channel")) {
                    fidChannel = field;
                    break;
                }
            }
            catch (ClassNotFoundException e4) {
                e4.printStackTrace();
            }
        }
        CHANNEL_FIELD = fidChannel;
        Field fidNetworkManager = null;
        Field fidNetHandler = null;
        try {
            final Class<?> clsNetHandler = Class.forName("net.minecraft.client.network.NetHandlerPlayClient");
            final Class<?> clsNetworkManager = Class.forName("net.minecraft.network.NetworkManager");
            final Class<?> clsPlayer = Class.forName("net.minecraft.network.play.client.C17PacketCustomPayload");
            Field[] declaredFields2;
            for (int length2 = (declaredFields2 = clsPlayer.getDeclaredFields()).length, j = 0; j < length2; ++j) {
                final Field field2 = declaredFields2[j];
                if (field2.getType() == clsNetHandler) {
                    fidNetHandler = field2;
                    break;
                }
            }
            Field[] declaredFields3;
            for (int length3 = (declaredFields3 = clsNetHandler.getDeclaredFields()).length, k = 0; k < length3; ++k) {
                final Field field2 = declaredFields3[k];
                if (field2.getType() == clsNetworkManager) {
                    fidNetworkManager = field2;
                    break;
                }
            }
        }
        catch (Exception e5) {
            e5.printStackTrace();
        }
        NETWORK_MANAGER_FIELD = fidNetworkManager;
        NET_HANDLER_FIELD = fidNetHandler;
    }
    
    public DNSPayloadTest(final Object instance) {
        this.instance = instance;
    }
    
    protected Object getFieldValue(final Field field) {
        return this.getFieldValue(field, this.instance);
    }
    
    protected Object getFieldValue(final Field field, final Object instance) {
        try {
            return field.get(instance);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    protected void setFieldValue(final Field field, final Object value) {
        try {
            field.set(this.instance, value);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    public static Object createPacket(String channel, String message) {
        try {
           return CLASS_CONSTRUCTOR.newInstance(channel, message.getBytes());
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException var3) {
           var3.printStackTrace();
           return null;
        }
     }
    
    public static Object getChannel() {
        try {
            final Object objNetHandler = DNSPayloadTest.NET_HANDLER_FIELD.get(Wrapper.getPlayer());
            final Object objNetManager = DNSPayloadTest.NETWORK_MANAGER_FIELD.get(objNetHandler);
            return DNSPayloadTest.CHANNEL_FIELD.get(objNetManager);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void sendPacket(Object packet) {
        try {
           Method method = Class.forName("io.netty.channel.Channel").getDeclaredMethod("writeAndFlush", Object.class);
           method.invoke(getChannel(), packet);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException var2) {
           var2.printStackTrace();
        }

     }
}
