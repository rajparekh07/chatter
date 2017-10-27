package chatter.requests;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JSONRequest {
    private HttpServletRequest request;

    private JSONObject jsonRequest;

    JSONRequest (HttpServletRequest request) {
        this.request = request;
        try {
            jsonRequest = (JSONObject) new JSONParser().parse(request.getReader());
            System.out.println(jsonRequest.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JSONRequest init(HttpServletRequest request) {
        return new JSONRequest(request);
    }

    public String getParameter(String key) {
        return (String) getObjectParameter(key);
    }

    public Object getObjectParameter(String key){ return jsonRequest.get(key);}
 }
