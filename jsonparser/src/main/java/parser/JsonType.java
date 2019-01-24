package parser;

import java.util.Collection;
import java.util.Map;

public class JsonType {
    private static final Class<?>[] massType = {Integer.class, Float.class, Double.class, Boolean.class, Long.class, Byte.class, Short.class, Character.class};

    public static boolean isCollection(Class<?> cl){
        return Collection.class.isAssignableFrom(cl);
    }

    public static boolean isMap(Class<?> cl){
        return Map.class.isAssignableFrom(cl);
    }

    public static boolean isPrimitiveHelp(Class<?> cl){
        for (Class<?> ignored :massType) {
            if (cl.equals(ignored))
                return true;
        }
        return false;
    }

    public static boolean isPrimitive(Class<?> cl){
        return cl.isPrimitive() || isPrimitiveHelp(cl);
    }

    public static boolean isArray(Class<?> cl){

        return cl.isArray();
    }
}
