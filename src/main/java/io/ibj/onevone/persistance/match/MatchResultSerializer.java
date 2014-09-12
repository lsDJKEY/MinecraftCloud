package io.ibj.onevone.persistance.match;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.UUID;

/**
 * Created by Joe on 9/1/2014.
 */
public class MatchResultSerializer implements JsonSerializer<MatchResult>, JsonDeserializer<MatchResult> {



    @Override
    public MatchResult deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();
        if(!obj.has("victor")){
            return null;
        }
        UUID victor = UUID.fromString(obj.get("victor").getAsString());
        return new BasicMatchResult(victor);
    }

    @Override
    public JsonElement serialize(MatchResult matchResult, Type type, JsonSerializationContext jsonSerializationContext) {
        if(matchResult == null){
            return new JsonObject();
        }
        JsonObject obj = new JsonObject();
        obj.addProperty("victor", matchResult.getVictor().getId().toString());
        return obj;
    }
}
