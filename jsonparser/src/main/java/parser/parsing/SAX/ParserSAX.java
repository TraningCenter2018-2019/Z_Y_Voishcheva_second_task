package parser.parsing.SAX;

import parser.parsing.JsonParserException;

public class ParserSAX {
    public <T>T fromJson(String str,  Class<?>cl){
        try {
            JsonParserSax jParse = new JsonParserSax(str, cl);
            Object obj = jParse.getJsonElement(cl);
            return (T)obj;
        } catch (JsonParserException e) {
            e.printStackTrace();
            return null;
        }
    }
}
