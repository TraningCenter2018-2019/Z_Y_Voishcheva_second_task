package parser.parsing.DOM;

import parser.JsonConstant;
import parser.parsing.DOM.HelpClass.ObjectHelp;
import parser.parsing.DOM.HelpClass.WhatIsPrimitiveType;
import parser.parsing.DOM.StructureJson.Node;
import parser.parsing.JsonElement;
import parser.parsing.JsonParserException;

import java.lang.reflect.Array;

public class CollectorJsonDomTree {
    private String str;
    private Node node;
    public CollectorJsonDomTree (String str, Node node){
        this.str = str;
        this.node = node;
    }

    public boolean checkEndParsing(){
        boolean result = true;
        try {
            String newStr = parsing(str, node);
            if (newStr.trim().length() != 0)
                result = false;
        } catch (JsonParserException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }


    private String parsing(String str, Node node) throws JsonParserException {
        String symbol = str.substring(0,1);
        switch(symbol){
            case JsonConstant.DILIMETER_STRING:
                str = parsingString(str, node);
                break;
            case JsonConstant.START_OBJECT:
                str = parsingObject(str, node);
                break;
            case JsonConstant.START_COLLECTION:
                str = parsingArray(str, node);
                break;
            default:
                str = parsingOther(str, node);
        }
        return str;
    }

    private String parsingString(String str, Node node) throws JsonParserException {
        str = str.substring(1);
        str = str.trim();
        String element = JsonElement.getJsonElement(str);
        str = str.substring(element.length());
        if (!str.substring(0,1).equals(JsonConstant.DILIMETER_STRING))
            throw new JsonParserException("Dilimiter string is not founded!");
        node.setElement(element);
        node.setType(String.class);
        str = str.substring(1);
        str = str.trim();
        return str;
    }

    private String parsingArray(String str, Node node) throws JsonParserException {
        str = str.substring(1);
        str = str.trim();
        while (!str.substring(0,1).equals(JsonConstant.FINISH_COLLECTION)) {
            Node child = node.createNewChild();
            str = str.trim();
            str = parsing(str, child);
            if (str.substring(0, 1).equals(JsonConstant.DILIMETER_FIELD))
                str = str.substring(1);
            str = str.trim();
        }
        str = str.substring(1);
        node.setType(Array.class);
        return str;
    }

    private String parsingObject(String str, Node node) throws JsonParserException {
        str=str.substring(1);
        str = str.trim();
        while (str.length()!=0&& !str.substring(0, 1).equals(JsonConstant.FINISH_OBJECT)) {
            Node child = node.createNewChild();
            str = str.trim();
            str = ObjectHelp.getNameField(str, child);
            str = parsing(str, child);
            if (str.length()!=0&&str.substring(0,1).equals(JsonConstant.DILIMETER_FIELD))
                str=str.substring(1);
        }
        node.setType(Object.class);
        str=str.substring(1);
        str = str.trim();
        return str;
    }

    private String parsingOther(String str, Node node) throws JsonParserException {
        str = str.trim();
        String element = JsonElement.getJsonElement(str);
        str = str.substring(element.length());
        str = str.trim();
        if (str.length()!= 0){
            if (!(str.substring(0, 1).equals(JsonConstant.FINISH_COLLECTION))&&
                    !(str.substring(0, 1).equals(JsonConstant.DILIMETER_FIELD ))&&
                    !(str.substring(0, 1).equals(JsonConstant.FINISH_OBJECT )) &&
                    !(str.substring(0, 1).equals(JsonConstant.DILIMETER_FINISH_FIELD )))
                          throw new  JsonParserException("");
        }
        Class<?> type = WhatIsPrimitiveType.getType(element);
        node.setElement(element);
        node.setType(type);
        return str;
    }
}
