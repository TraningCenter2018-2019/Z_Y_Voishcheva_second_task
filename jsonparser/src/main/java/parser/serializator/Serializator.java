package parser.serializator;

import parser.JsonConstant;
import parser.JsonType;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class Serializator {

    private static String toStringArray( Object obj, Class<?> cl){
        if (obj == null)
            return "";
        StringBuffer output = new StringBuffer();
        output.append(JsonConstant.START_COLLECTION);
        output.append(JsonConstant.DILIMETER_FINISH_FIELD);
        Object[] array = (Object[])obj;
        int numberLastSymbol = array.length-1;
        for(int i = 0; i < array.length;i++){
            output.append(toStringType(array[i], cl));
            if (i != numberLastSymbol){
                output.append(JsonConstant.DILIMETER_FIELD);
                output.append(JsonConstant.DILIMETER_FINISH_FIELD);
            }
        }
        output.append(JsonConstant.DILIMETER_FINISH_FIELD);
        output.append(JsonConstant.FINISH_COLLECTION);
        return output.toString();
    }

    private static String toStringCollection( Object obj, Class<?> cl){
        if (obj == null)
            return "";

        Collection collection =  (Collection)obj;
        StringBuffer output = new StringBuffer();
        output.append(JsonConstant.START_COLLECTION);
        output.append(JsonConstant.DILIMETER_FINISH_FIELD);
        int i = collection.size();
        Object item;
        Class<?> classElement;
        for (Iterator<?> iter = collection.iterator(); iter.hasNext(); i--) {
            item = iter.next();
            classElement = item.getClass();
            String s = toStringType(item, classElement);
            output.append(s);
            if (i != 1){
                output.append(JsonConstant.DILIMETER_FIELD);
                output.append(JsonConstant.DILIMETER_FINISH_FIELD);
            }
        }
        output.append(JsonConstant.DILIMETER_FINISH_FIELD);
        output.append(JsonConstant.FINISH_COLLECTION);
        return output.toString();
    }


    private static String toStringMap( Object obj, Class<?> cl){
        if (obj == null)
            return "";

        Map<?,?> map = (Map)obj;
        StringBuffer output = new StringBuffer();
        output.append(JsonConstant.START_COLLECTION);
        output.append(JsonConstant.DILIMETER_FINISH_FIELD);
        int i = map.size();
        Object key;
        Object val;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            key = entry.getKey();
            String k = toStringType(key, key.getClass());
            output.append(k);
            output.append(JsonConstant.DILIMETER_TYPE_VALUE_FIELD);
            output.append(JsonConstant.DILIMETER_TYPE_VALUE_FIELD);
            val = entry.getValue();
            String s = toStringType(val, val.getClass());
            output.append(s);
            if (i != 1){
                output.append(JsonConstant.DILIMETER_FIELD);
                output.append(JsonConstant.DILIMETER_FINISH_FIELD);
            }
            i--;
        }
        output.append(JsonConstant.DILIMETER_FINISH_FIELD);
        output.append(JsonConstant.FINISH_COLLECTION);
        return output.toString();
    }

    private static String toStringObject(Object obj, Class<?> currentClass) {
        Field[] fields = currentClass.getDeclaredFields();
        StringBuffer output = new StringBuffer();
        output.append(JsonConstant.START_OBJECT);
        output.append(JsonConstant.D);
        int numberLastSymbol = fields.length-1;
        for(int i = 0; i < fields.length;i++){
            try {
                fields[i].setAccessible(true);
                String value = toStringType(fields[i].get(obj), fields[i].getType());
                output.append("\""+fields[i].getName()+"\""+JsonConstant.DILIMETER_TYPE_VALUE_FIELD + JsonConstant.DILIMETER_FINISH_FIELD + value );
                if (i != numberLastSymbol){
                    output.append(JsonConstant.DILIMETER_FIELD);
                    output.append(JsonConstant.DILIMETER_FINISH_FIELD);
                }
                output.append(JsonConstant.D);
            }catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(currentClass.getGenericSuperclass()!=Object.class){
            Class<?> superClass = currentClass.getSuperclass();
            output.append(JsonConstant.DILIMETER_FIELD);
            output.append(JsonConstant.DILIMETER_FINISH_FIELD);
            output.append(JsonConstant.D);
            output.append(toStringObject(obj, superClass));
        }
        output.append(JsonConstant.FINISH_OBJECT);
        return output.toString();
    }

    private static String toStringType(Object obj, Class<?> myClass){
        String result="";
        if (JsonType.isPrimitive(myClass) && !myClass.equals(String.class))
            result = obj.toString();
        else if (myClass.equals(String.class))
            result = "\"" + obj.toString() + "\"";
        else if (myClass.isArray() )
            result =  toStringArray(obj, myClass.getComponentType());
        else if (JsonType.isCollection(myClass))
            result = toStringCollection(obj, myClass.getComponentType());
        else if (JsonType.isMap(myClass))
            result = toStringMap(obj, myClass.getComponentType());
        else
            result = toStringObject(obj, myClass);
        return result;
    }

    public static String toJson(Object obj){
        if (obj == null)
            return "";
        else{
            String s = toStringType(obj, obj.getClass());
            return s;
        }
    }
}
