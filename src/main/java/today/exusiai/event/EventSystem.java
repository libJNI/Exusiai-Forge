package today.exusiai.event;

import java.util.concurrent.*;
import java.lang.reflect.*;
import java.util.*;

public final class EventSystem
{
    private static final Map<Class<? extends Event>, List<MethodData>> REGISTRY_MAP;
    
    public static void register(final Object object) {
        for (final Method method : object.getClass().getDeclaredMethods()) {
            if (!isMethodBad(method)) {
                register(method, object);
            }
        }
    }
    
    public static void register(final Object object, final Class<? extends Event> eventClass) {
        for (final Method method : object.getClass().getDeclaredMethods()) {
            if (!isMethodBad(method, eventClass)) {
                register(method, object);
            }
        }
    }
    
    public static void unregister(final Object object) {
        for (final List<MethodData> dataList : EventSystem.REGISTRY_MAP.values()) {
            for (final MethodData data : dataList) {
                if (data.getSource().equals(object)) {
                    dataList.remove(data);
                }
            }
        }
        cleanMap(true);
    }
    
    public static void unregister(final Object object, final Class<? extends Event> eventClass) {
        if (EventSystem.REGISTRY_MAP.containsKey(eventClass)) {
            for (final MethodData data : EventSystem.REGISTRY_MAP.get(eventClass)) {
                if (data.getSource().equals(object)) {
                    EventSystem.REGISTRY_MAP.get(eventClass).remove(data);
                }
            }
            cleanMap(true);
        }
    }
    
    private static void register(final Method method, final Object object) {
        final Class<? extends Event> indexClass = (Class<? extends Event>)method.getParameterTypes()[0];
        final MethodData data = new MethodData(object, method, method.getAnnotation(ActiveEvent.class).value());
        if (!data.getTarget().isAccessible()) {
            data.getTarget().setAccessible(true);
        }
        if (EventSystem.REGISTRY_MAP.containsKey(indexClass)) {
            if (!EventSystem.REGISTRY_MAP.get(indexClass).contains(data)) {
                EventSystem.REGISTRY_MAP.get(indexClass).add(data);
                sortListValue(indexClass);
            }
        }
        else {
            EventSystem.REGISTRY_MAP.put(indexClass, new CopyOnWriteArrayList<MethodData>() {
                private static final long serialVersionUID = -1L;
                
                {
                    this.add(data);
                }
            });
        }
    }
    
    public static void removeEntry(final Class<? extends Event> indexClass) {
        final Iterator<Map.Entry<Class<? extends Event>, List<MethodData>>> mapIterator = EventSystem.REGISTRY_MAP.entrySet().iterator();
        while (mapIterator.hasNext()) {
            if (mapIterator.next().getKey().equals(indexClass)) {
                mapIterator.remove();
                break;
            }
        }
    }
    
    public static void cleanMap(final boolean onlyEmptyEntries) {
        final Iterator<Map.Entry<Class<? extends Event>, List<MethodData>>> mapIterator = EventSystem.REGISTRY_MAP.entrySet().iterator();
        while (mapIterator.hasNext()) {
            if (!onlyEmptyEntries || mapIterator.next().getValue().isEmpty()) {
                mapIterator.remove();
            }
        }
    }
    
    private static void sortListValue(final Class<? extends Event> indexClass) {
        final List<MethodData> sortedList = new CopyOnWriteArrayList<MethodData>();
        for (final byte priority : Priority.VALUE_ARRAY) {
            for (final MethodData data : EventSystem.REGISTRY_MAP.get(indexClass)) {
                if (data.getPriority() == priority) {
                    sortedList.add(data);
                }
            }
        }
        EventSystem.REGISTRY_MAP.put(indexClass, sortedList);
    }
    
    private static boolean isMethodBad(final Method method) {
        return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(ActiveEvent.class);
    }
    
    private static boolean isMethodBad(final Method method, final Class<? extends Event> eventClass) {
        return isMethodBad(method) || !method.getParameterTypes()[0].equals(eventClass);
    }
    
    public static final Event call(final Event event) {
        final List<MethodData> dataList = EventSystem.REGISTRY_MAP.get(event.getClass());
        if (dataList != null) {
            for (final MethodData data : dataList) {
                invoke(data, event);
            }
        }
        return event;
    }
    
    private static void invoke(final MethodData data, final Event argument) {
        try {
            data.getTarget().invoke(data.getSource(), argument);
        }
        catch (IllegalAccessException ex) {}
        catch (IllegalArgumentException ex2) {}
        catch (InvocationTargetException ex3) {}
    }
    
    static {
        REGISTRY_MAP = new HashMap<Class<? extends Event>, List<MethodData>>();
    }
    
    private static final class MethodData
    {
        private final Object source;
        private final Method target;
        private final byte priority;
        
        public MethodData(final Object source, final Method target, final byte priority) {
            this.source = source;
            this.target = target;
            this.priority = priority;
        }
        
        public Object getSource() {
            return this.source;
        }
        
        public Method getTarget() {
            return this.target;
        }
        
        public byte getPriority() {
            return this.priority;
        }
    }
}
