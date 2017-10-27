package chatter.responses;

import org.json.simple.JSONObject;


public class JSONResponse {

    private JSONObject response;

    JSONResponse() {
        this.response = new JSONObject();
    }

    public static JSONResponse init () {
        return new JSONResponse();
    }

    public JSONResponse success () {

        response.put("success", true);

        return this;
    }

    public JSONResponse failed () {

        response.put("success", false);

        return this;
    }

    public JSONResponse error (String msg) {

        failed().with("error", msg);

        return this;
    }

    public JSONResponse with (String key, String msg) {

        response.put(key, msg);

        return this;
    }

    public String make () {

        return response.toJSONString();
    }
}
