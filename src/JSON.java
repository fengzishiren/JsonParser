public class JSON {

    private static Parser parser = new Parser();

    public static Object fromString(String json) {
        return parser.parse(json);
    }
    
    public static String toJson(Object javaObject) {
        return JsonUtil.toJson(javaObject);
    }
    

}