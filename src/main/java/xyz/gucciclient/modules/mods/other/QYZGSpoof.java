package xyz.gucciclient.modules.mods.other;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.ReflectionHelper;
import xyz.gucciclient.utils.Wrapper;
import xyz.gucciclient.values.BooleanValue;

import java.lang.reflect.Field;

public class QYZGSpoof extends Module {
    private BooleanValue Debug = new BooleanValue("DebugMessage", false);
    public QYZGSpoof() {
        super("QYZGSpoof", 0, Module.Category.Other);
        addBoolean(Debug);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        Bypass();
    }

    public void Bypass() {
        try{
            Field mglfield = Class.forName("cn.margele.noreachhack.NoReachHack").getDeclaredField("怎么开始猴急了呢在这样我把你打的抛尸街头了不服输只会狗急跳墙死皮赖脸的请把你第2层脸皮撕下来好吗什么处女膜草泥马的三字经给爹爹我滚远点好使什么优美词语的健忘的脑子的一群亡命匪徒的废柴拿什么和我打NMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSL      \u200D");
            ReflectionHelper.setFinalStatic(mglfield,true);
            mglfield.setBoolean(null,true);
            if(Debug.getState()) {
                Wrapper.printChat("trigged" + mglfield.getBoolean(null));
            }
        }catch (Exception e){
            Wrapper.printChat(e.toString());
            System.out.println(e);
        }
    }
}
