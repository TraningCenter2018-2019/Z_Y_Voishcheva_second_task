package parser.parsing;


public class JsonParserException extends Exception{
    private String errStr;  //  Описание ошибки

    public JsonParserException(String errStr) {
        super();
        this.errStr = errStr;
    }

    public String toString(){
        return this.errStr;
    }
}

