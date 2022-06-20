package gfb.utils;

import gfb.models.ResponseData;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.List;

public class GeneratePayload {

    private static SecureRandom secureRandom = new SecureRandom();

    public static JSONObject setAddPayload(String todoItem, String status, String id) {
        JSONObject addPayload = new JSONObject();
        addPayload.put("id", id != null ? Integer.valueOf(id) : secureRandom.nextInt(1000 - 100 + 1) + 100);
        addPayload.put("todo", todoItem);
        addPayload.put("status", status);
        return addPayload;
    }

    public static JSONObject setEditPayload(int id, String editTodoItem) {
        JSONObject editPayload = new JSONObject();
        editPayload.put("id", id);
        editPayload.put("todo", editTodoItem);
        editPayload.put("status", "inprogress");
        return editPayload;
    }

    public static JSONObject setDeletePayload(int id) {
        JSONObject delPayload = new JSONObject();
        delPayload.put("id", id);
        return delPayload;
    }
}
