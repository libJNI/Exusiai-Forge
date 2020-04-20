package me.delphi.events

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import me.delphi.Delphi


import me.delphi.Manager
import me.delphi.mod.Mod
import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles


class MethodInfo(val method:MethodHandle,val obj: Any){
    fun invoke(param:Any){
        val a : Any = method.invoke(obj,param)
    }
}
@Suppress("UNCHECKED_CAST")
class EventManager:Manager{
    val predicate:(Any) -> Boolean = {
        if (it is Mod)
            it.enabled || it.keepReg
        true
    }
    private var subscribers: Multimap<Class<out Event?>, MethodInfo> = ArrayListMultimap.create()
    fun checkForSubscribers(obj:Any){
        if(obj.javaClass.isAnnotationPresent(SubscriberClass::class.java))
            obj.javaClass.declaredMethods.forEach {
                if(it.parameterCount == 1)
                    if(Event::class.java.isAssignableFrom(it.parameterTypes[0])){
                        if(!it.isAccessible)
                            it.isAccessible = true
                        val l: MethodHandles.Lookup = MethodHandles.lookup()
                        subscribers.put(it.parameterTypes[0] as Class<out Event?>?, MethodInfo(l.unreflect(it),obj))
                    }
            }
    }
    fun <E:Event> post(event:E){
        subscribers.get(event.javaClass).forEach {
            if(predicate(it.obj))
                it.invoke(event)
        }
    }

    /**
     * Flushes the Manager.
     * Used for dynamic reloading.
     * */
    override fun flush() {
        subscribers.clear()
    }

    override fun initiate() {

    }
}

