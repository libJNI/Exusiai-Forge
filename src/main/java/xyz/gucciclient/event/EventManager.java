package xyz.gucciclient.event;


// import me.enterman.materialClient.module.management.Mod;

import xyz.gucciclient.modules.Module;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class EventManager<T,O> {
	Predicate<O> postPredicate;
	public EventManager(Predicate<O> postPredicate){
		this.postPredicate = postPredicate;
	}
	public EventManager(){
		this.postPredicate = (o)->true;
	}
	private Map<Class<? extends T>, Map<Method,O>> objs = new HashMap<>();
	private static EventManager<Event, Module> instance = new EventManager<>((mod -> mod.getState()));
	private static EventManager<Event,Object> other = new EventManager<>((o) -> true);
	public void flushEvents(){
		objs.clear();
	}
	public static EventManager<Event,Object> getOtherEventManager(){
		return other;
	}
	public void register(O obj){
		for (Method method : obj.getClass().getDeclaredMethods()) {
			if (method.isAnnotationPresent(Sub.class)){

				if(method.getParameterCount() == 1){

					if (Event.class.isAssignableFrom(method.getParameterTypes()[0])){

						if (!method.isAccessible()){
							method.setAccessible(true);
						}
						Map<Method,O> l;
						if((l = objs.getOrDefault(method.getParameterTypes()[0],null)) != null){
							l.put(method,obj);
						} else {
							l = new HashMap<>();
							l.put(method,obj);
							objs.put((Class<? extends T>) method.getParameterTypes()[0],l);
						}
					}
				}
			}
		}
	}

	public static EventManager<Event, Module> getInstance() {
		return instance;
	}

	public static <P extends Event> P postAll(P event){

		// FIXED: 2020/2/8 Events not working as expected
		// FIXME: 2020/3/14 Events not working as expected
		instance.post(event);
		other.post(event);
		return event;
	}
	public void post(Event event){
		Map<Method,O> l;
		if((l = objs.getOrDefault(event.getClass(),null)) != null){
			l.forEach((method,obj) -> {
				if(postPredicate.test(obj)){
					try {
						method.invoke(obj,event);
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

}
