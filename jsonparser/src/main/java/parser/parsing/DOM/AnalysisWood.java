package parser.parsing.DOM;

import parser.JsonType;
import parser.parsing.DOM.StructureJson.Node;
import parser.parsing.JsonParserException;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class AnalysisWood {

    public static <T>T getElement(Node node, Class<?> myClass) throws JsonParserException{
        if (node.getElement() != null)
            return (T) node.getElement();
        if (JsonType.isArray(myClass)){
            Object obj = getArray(node, myClass);
            return (T) obj;
        }

        Object obj = getObject(node, myClass);
        return (T) obj;
    }

    private  static <T>T[] getArray(Node node,Class<?>type) throws JsonParserException{
        Object aObject;
        if(node.getListChild().get(0).getElement()!=null) {
            aObject = Array.newInstance(node.getListChild().get(0).getElement().getClass(), node.getListChild().size());
            for (int j = 0; j < node.getListChild().size(); j++)
                Array.set(aObject, j, node.getListChild().get(j).getElement());
        }
        else{
            aObject = Array.newInstance(type, node.getListChild().size());
            for (int j = 0; j < node.getListChild().size(); j++)
                Array.set(aObject, j, getObject(node.getListChild().get(j),type));
        }
        return (T[])aObject;
    }

    private static <T>T getObject(Node node, Class<?> myClass) throws JsonParserException{
        try {
            Constructor<?> cons = myClass.getDeclaredConstructor();
            Object obj = cons.newInstance();
            Node item;
            Field field;
            for (int i = 0; i < node.getListChild().size(); i++) {
                item = node.getListChild().get(i);
                field = myClass.getDeclaredField(item.getNameNode());
                field.setAccessible(true);
                choiceType(item, field, myClass, obj);
            }
            return (T) obj;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void choiceType(Node node, Field field, Class<?> myClass, Object obj)  throws JsonParserException{
        try {
            if (node.getElement() != null) {
                field.set(obj, getSimple(node, field.getType()));
                field.setAccessible(false);
            } else if (node.getType() == Array.class) {
                Object aObject = getArray(node, field.getType().getComponentType());
                field.set(obj, aObject);
                field.setAccessible(false);
            } else {
                node.setType(field.getType());
                node.setElement(getElement(node, field.getType()));
                field.set(obj, node.getElement());
                field.setAccessible(false);

            }
        }catch(IllegalAccessException e){
            e.printStackTrace();
        }
    }

    private static <T>T getSimple(Node node, Class<?> type) {
        Object aObject=node.getElement();
        return (T)aObject;
    }
}
