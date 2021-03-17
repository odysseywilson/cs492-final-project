package com.example.spillthetea;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.Serializable;
import java.lang.reflect.Type;

public class TeaItem implements Serializable {
    public int id;
    public String username;
    public String caption;
    public long time;

    public TeaItem()
    {
        this.id = 0;
        this.username = "";
        this.caption = "";
        this.time = 0;
    }

    public TeaItem(int id, String username, String caption, long time)
    {
        this.id = id;
        this.username = username;
        this.caption = caption;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getCaption() {
        return caption;
    }

    public long getTime() {
        return time;
    }

    public static class JsonDeserializer implements com.google.gson.JsonDeserializer<TeaItem> {

        @Override
        public TeaItem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject teaObj = json.getAsJsonObject();
            return new TeaItem(
                    teaObj.getAsJsonPrimitive("id").getAsInt(),
                    teaObj.getAsJsonPrimitive("username").getAsString(),
                    teaObj.getAsJsonPrimitive("caption").getAsString(),
                    teaObj.getAsJsonPrimitive("time").getAsLong()
            );
        }
    }
}
