package by.radioegor146.interop;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import xyz.gucciclient.Client;

@Mod(modid = ForgeMod.MODID, version = ForgeMod.VERSION)
public class ForgeMod {

    public static final String MODID = "betterfps";
    public static final String VERSION = "0.1";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) throws Throwable {
        new Client();
    }
}
