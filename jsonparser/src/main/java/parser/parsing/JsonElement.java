package parser.parsing;

import parser.JsonConstant;

// берет элемент от первого разделителя до второго
public class JsonElement {

    public static String getJsonElement(String str) throws JsonParserException {
        String result = "";
        if (str.substring(0, 1).equals(JsonConstant.DILIMETER_STRING))
            throw new JsonParserException("");
        for (int i = 0; i < str.length(); i++){
            if ((str.length()!= 0) && !JsonConstant.setFinishSymbol.contains(str.substring(i, i+1)))
                result += str.substring(i, i+1);
            else
                return result;
        }
        return result;
    }


}
