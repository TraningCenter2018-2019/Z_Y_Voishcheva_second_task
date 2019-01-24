package parser.parsing.SAX;

import parser.JsonConstant;
import parser.JsonType;
import parser.parsing.JsonParserException;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class JsonParserSax {

    private String str;

    public JsonParserSax(String str, Class<?> myClass) {
        this.str = str.replace("\n", "");
    }

    public Object getJsonElement(Class<?> myClass) throws JsonParserException {
        Object result = null;
        if ((str=="")|| (myClass == null))
            throw new JsonParserException("Data is not wromg!!");
        str = str.trim();
        String symb = str.substring(0, 1);
        str = str.substring(0);
        if (symb.equals(JsonConstant.START_OBJECT)) {
            result = getObject(myClass);
            return result;
        } else if (symb.equals(JsonConstant.START_COLLECTION)) {
            if (JsonType.isArray(myClass))
                result = getArray(myClass);
            if (JsonType.isCollection(myClass)){
                result = getArray(myClass);
                ArrayList<Object> ar = new ArrayList<Object>(Arrays.asList(result));
                result = ar;
            }
            return result;
        } else {
            result = getOther(myClass);
            return result;
        }
    }

    private Object getArray(Class<?> myClass) throws JsonParserException {
        str = str.substring(1);
        str = str.trim();
        Class<?>subClass=myClass.getComponentType();
        ArrayList<Object> objects=new ArrayList<Object>();
        Object val;
        while (str.length()!=0&&!str.substring(0, str.trim().length()).equals(JsonConstant.FINISH_COLLECTION)){
            val=getJsonElement(subClass);
            objects.add(val);
            if(str.length()!=0&&str.substring(0,1).equals(JsonConstant.DILIMETER_FIELD)){
                str=str.substring(1);
            }
            str = str.trim();
        }
        Object aObject = Array.newInstance(subClass, objects.size());
        for (int i=0;i<objects.size();i++){
            Array.set(aObject, i, objects.get(i));
        }
        if(str.length()!=0&&str.trim().substring(0,1).equals(JsonConstant.FINISH_COLLECTION)) {
            str=str.substring(1);
            return aObject;
        }
        return null;
    }


    private Collection whatIsCollection(Class<?> myClass){
        Collection<Object> myCollection = null;
        if (myClass.equals(ArrayList.class))
            myCollection = new ArrayList<Object>();
        else if (myClass.equals(LinkedList.class))
            myCollection = new LinkedList<Object>();
        else if (myClass.equals(LinkedHashSet.class))
            myCollection = new LinkedHashSet<Object>();
        return myCollection;
    }

    private Object getCollection(Class<?> myClass) throws JsonParserException {
        str = str.substring(1);
        str = str.trim();
        Class<?>subClass=myClass.getComponentType();
        Object obj;
        myClass.getComponentType();
        Collection<Object>objects = whatIsCollection(myClass);
        //Collection<Object> objects=new Collection<Object>() {
        //};
        Object val;
        while (str.length()!=0&&!str.substring(0, str.trim().length()).equals(JsonConstant.FINISH_COLLECTION)){
            val=getJsonElement(subClass);
            objects.add(val);
            if(str.length()!=0&&str.substring(0,1).equals(JsonConstant.DILIMETER_FIELD)){
                str=str.substring(1);
            }
            str = str.trim();
        }
        if(str.length()!=0&&str.trim().substring(0,1).equals(JsonConstant.FINISH_COLLECTION)) {
            str=str.substring(1);
            return objects;
        }
        return null;
    }

    private Object getOther(Class<?> myClass) throws JsonParserException {
        str = str.trim();
        try{
            if (myClass.equals(String.class))
                str = str.substring(1);
            str = str.trim();
            int ind = getFinishIndex();
            Constructor<?>cons = myClass.getConstructor(String.class);
            Object result = cons.newInstance(str.substring(0, ind));
            if (str.length() > ind){
                str = str.substring(ind+1);
            }else{
                str = str.substring(ind);
            }
            return result;
        }catch(NoSuchMethodException e){
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int getFinishIndex(){
        int ind = -1;
        ind = str.indexOf(JsonConstant.DILIMETER_STRING);
        if (ind == -1){
            ind = str.indexOf(JsonConstant.DILIMETER_FIELD);
            if (ind == -1){
                ind = str.indexOf(JsonConstant.D);
                if (ind == -1) {
                    ind = str.indexOf(JsonConstant.DILIMETER_FINISH_FIELD);
                    if (ind == -1) {
                        ind = str.indexOf(JsonConstant.FINISH_OBJECT);
                        if (ind == -1) {
                            ind = str.indexOf(JsonConstant.FINISH_COLLECTION);
                            if (ind == -1) {
                                ind = str.length();
                            }
                        }
                    }
                }
            }
        }
        return ind;
    }


    public Object getObject(Class<?> myClass) {
        str = str.substring(1);
        str = str.trim();
        try{
            Constructor<?> constr = myClass.getConstructor();
            Object obj = constr.newInstance();
            while (!str.substring(0,1).equals(JsonConstant.FINISH_OBJECT) && str.length()!=0){
                String nameField = getNameField();
                Field field = myClass.getDeclaredField(nameField);
                field.setAccessible(true);
                Object val = getJsonElement(field.getType());
                field.set(obj, val);
                field.setAccessible(false);
                if (str.substring(0,1).equals(JsonConstant.DILIMETER_STRING))
                    str = str.substring(1);

                if (str.substring(0,1).equals(JsonConstant.DILIMETER_FIELD))
                    str = str.substring(1);
                str = str.trim();
            }
            return obj;
        }catch(Exception e){
            return null;
        }
    }

    private String getNameField() throws JsonParserException {
        str = str.trim();
        str = str.substring(1);
        int ind = str.indexOf(JsonConstant.DILIMETER_TYPE_VALUE_FIELD);
        if (ind == -1)
            throw new JsonParserException("Record of object is not wrong");
        String nameField = str.substring(0, ind-1).trim();
        str = str.substring(ind+1);
        return nameField;
    }


}
