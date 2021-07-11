package cc.neckbeard.mcapilib.json;

import cc.neckbeard.mcapilib.profiles.Profile;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ProfileDeserializer implements JsonDeserializer<Profile> {

    @Override
    public Profile deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!json.isJsonObject()) throw new JsonParseException("invalid profile object");
        final JsonObject jsonObject = json.getAsJsonObject();
        // id (required)
        if (jsonObject.get("id") == null) throw new JsonParseException("id not found");
        final String id = jsonObject.get("id").getAsString();
        // name (required)
        if (jsonObject.get("name") == null) throw new JsonParseException("name not found");
        final String name = jsonObject.get("name").getAsString();
        // legacy flag (optional)
        final boolean legacy = jsonObject.get("legacy") != null && jsonObject.get("legacy").getAsBoolean();
        return new Profile(id, name, legacy);
    }

}
