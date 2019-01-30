package parser.parsing.DOM.StructureJson;

import parser.parsing.JsonParserException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Node {
    private Object element;
    private Class<?> type;
    private ArrayList<Node> listChild;
    private boolean isCasting = false;

    public String getNameNode() {
        return nameNode;
    }

    private String nameNode;

    public Node() {
        this.listChild = new ArrayList<Node>();
    }

    public Node(Object node, Class<?> type) {
        this.element = node;
        this.type = type;
        this.listChild = new ArrayList<Node>();
    }

    public Object getElement() {
        return element;
    }

    public Class<?> getType() {
        return type;
    }

    public void setElement(Object node) throws JsonParserException {
        this.element = node;
        if (!isCasting)
            castingElementToItsType();
    }

    public void setType(Class<?> type) throws JsonParserException {
        this.type = type;
        if (!isCasting)
            castingElementToItsType();
    }

    public void setNameNode(String nameNode) {
        this.nameNode = nameNode;
    }

    public ArrayList<Node> getListChild() {
        return listChild;
    }

    public boolean isChilds(){
        return !listChild.isEmpty();
    }

    public Node createNewChild(){
        Node newNode = new Node();
        listChild.add(newNode);
        return newNode;
    }

    private void castingElementToItsType() throws JsonParserException {
        if ((this.type != null) && (this.element != null)) {
            try {
                Constructor<?> cons = type.getConstructor(String.class);
                this.element = cons.newInstance(element);
                this.isCasting = true;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                throw new JsonParserException("Data is'nt wrong!!");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new JsonParserException("Data is'nt wrong!!");
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                throw new JsonParserException("Data is'nt wrong!!");
            }
        }
    }

}
