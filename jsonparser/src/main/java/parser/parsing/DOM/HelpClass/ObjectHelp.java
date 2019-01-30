package parser.parsing.DOM.HelpClass;

import parser.JsonConstant;
import parser.parsing.DOM.StructureJson.Node;
import parser.parsing.JsonElement;
import parser.parsing.JsonParserException;

public class ObjectHelp {

    public static String getNameField(String str, Node node) throws JsonParserException {
        if (!str.substring(0, 1).equals(JsonConstant.DILIMETER_STRING))
            throw new JsonParserException("Wron the format");
        str = str.substring(1);
        String elementName = JsonElement.getJsonElement(str);
        if (elementName.equals(""))
            throw new JsonParserException("Wron the format");
        node.setNameNode(elementName);
        str = str.substring(elementName.length()+1);
        if (!str.substring(0,1).equals(":"))
            throw new JsonParserException("Wron the format");

        return str.substring(1).trim();
    }


}
