package parser.parsing.DOM;

import parser.parsing.DOM.StructureJson.Node;
import parser.parsing.JsonParserException;

public class JsonParserDom {
    public <T>T fromJson(String str,  Class<?> myClass){
        Node root = new Node();
        CollectorJsonDomTree collectorJsonDomTree = new CollectorJsonDomTree(str, root);
        if (collectorJsonDomTree.checkEndParsing()){
            Object obj = null;
            try {
                obj = AnalysisWood.getElement(root, myClass);
            } catch (JsonParserException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                return null;
            }
            return (T)obj;
        }
        return null;
    }
}
