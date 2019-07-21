package Util;

import com.google.gson.Gson;
import org.restlet.data.MediaType;
import org.restlet.representation.WriterRepresentation;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonMapRepresentation extends WriterRepresentation {

    private final Map map;
    public static final JsonMapRepresentation SUCCESS_JSON;


    static {   // static class initialization
        Map<String, Object> m = new HashMap<>();
        m.put("success", "success");
        SUCCESS_JSON = new JsonMapRepresentation(m);
    }

    public JsonMapRepresentation(Map map) {
        super(MediaType.APPLICATION_JSON);
        this.map = map;
    }

    @Override
    public void write(Writer writer) throws IOException {
        Gson gson = new Gson();
        writer.write(gson.toJson(map));
    }

    public static JsonMapRepresentation getJSONforMap(Map data) {
        return new JsonMapRepresentation(data);
    }

    public static JsonMapRepresentation getJSONforList(String fieldName, List list) {
        Map<String, Object> map = new HashMap<>();
        map.put(fieldName, list);
        return new JsonMapRepresentation(map);
    }

    public static JsonMapRepresentation getJSONforObject(String fieldName, Object o) {
        Map<String, Object> map = new HashMap<>();
        map.put(fieldName, o);
        return new JsonMapRepresentation(map);
    }

    public static JsonMapRepresentation getJSONforError(String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("error", "error");
        map.put("message", message);
        return new JsonMapRepresentation(map);
    }

}
