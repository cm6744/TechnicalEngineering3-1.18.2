package ten3.core.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class JsonParser {

    public static JsonObject getJsonObject(JsonObject json, String memberName) {
        if (json.has(memberName)) {
            return json.getAsJsonObject(memberName);
        } else {
            throw new JsonSyntaxException("Missing " + memberName + ", expected to find a JsonObject");
        }
    }

    public static String getStringOr(JsonObject p_90163_, String n, String fb) {
        JsonElement jsonelement = p_90163_.get(n);
        if (jsonelement != null) {
            return jsonelement.isJsonNull() ? fb : jsonelement.getAsString();
        } else {
            return fb;
        }
    }

    public static String getString(JsonObject p_90163_, String n) {
        return getStringOr(p_90163_, n, "");
    }

    public static int getIntOr(JsonObject p_90155_, String n, int fb) {
        JsonElement jsonelement = p_90155_.get(n);
        if (jsonelement != null) {
            return jsonelement.isJsonNull() ? fb : jsonelement.getAsInt();
        } else {
            return fb;
        }
    }

    public static int getInt(JsonObject p_90155_, String n) {
        return getIntOr(p_90155_, n, 1);
    }

    public static float getFloatOr(JsonObject p_90155_, String n, float fb) {
        JsonElement jsonelement = p_90155_.get(n);
        if (jsonelement != null) {
            return jsonelement.isJsonNull() ? fb : jsonelement.getAsFloat();
        } else {
            return fb;
        }
    }

    public static float getFloat(JsonObject p_90155_, String n) {
        return getFloatOr(p_90155_, n, 1f);
    }

    public static boolean getBooleanOr(JsonObject p_90167_, String n, boolean fb) {
        JsonElement jsonelement = p_90167_.get(n);
        if (jsonelement != null) {
            return jsonelement.isJsonNull() ? fb : jsonelement.getAsBoolean();
        } else {
            return fb;
        }
    }

}
