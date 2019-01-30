package parser;

import java.util.HashSet;
import java.util.Set;

public class JsonConstant {
    public static final String DILIMETER_FIELD = ",";
    public static final String DILIMETER_FINISH_FIELD = " ";
    public static final String DILIMETER_STRING = "\"";
    public static final String START_COLLECTION = "[";
    public static final String FINISH_COLLECTION = "]";
    public static final String START_OBJECT = "{";
    public static final String FINISH_OBJECT = "}";
    public static final String D = "\n";
    public static final String DILIMETER_TYPE_VALUE_FIELD = ":";
    public static final Set<String> setFinishSymbol ;

    static {
        setFinishSymbol =  new HashSet<String>();
        setFinishSymbol.add(DILIMETER_FIELD);
        setFinishSymbol.add(DILIMETER_FINISH_FIELD);
        setFinishSymbol.add(DILIMETER_STRING);
        setFinishSymbol.add(FINISH_COLLECTION);
        setFinishSymbol.add(D);
        setFinishSymbol.add(FINISH_OBJECT);
    }
}
