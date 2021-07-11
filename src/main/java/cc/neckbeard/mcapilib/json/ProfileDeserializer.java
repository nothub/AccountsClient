package cc.neckbeard.mcapilib.json;

import cc.neckbeard.mcapilib.profiles.Profile;
import cc.neckbeard.mcapilib.profiles.TextureProperty;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Base64;

public class ProfileDeserializer implements JsonDeserializer<Profile> {

    @Override
    public Profile deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();

        Profile.Builder builder = Profile.Builder.getInstance();

        // id (required)
        final JsonElement idElement = jsonObject.get("id");
        if (idElement == null) throw new JsonParseException("id not found");
        builder.id(idElement.getAsString());

        // name (required)
        final JsonElement nameElement = jsonObject.get("name");
        if (nameElement == null) throw new JsonParseException("name not found");
        builder.name(nameElement.getAsString());

        // legacy flag (optional)
        final JsonElement legacyElement = jsonObject.get("legacy");
        if (legacyElement != null && legacyElement.getAsBoolean()) builder.legacy();

        // textures (optional)
        final JsonElement propertiesElement = jsonObject.get("properties");
        if (propertiesElement == null) return builder.build();
        // if properties element exists, we parse it
        JsonArray propertiesArray = propertiesElement.getAsJsonArray();
        if (propertiesArray.size() == 0) throw new JsonParseException("properties empty");
        if (!propertiesArray.get(0).isJsonObject()) throw new JsonParseException("properties not an array");
        final String propertyName = propertiesArray.get(0).getAsJsonObject().get("name").getAsString();
        if (!propertyName.equals("textures"))
            throw new JsonParseException("Property " + propertyName + " not implemented!");
        TextureProperty textures = GSON.instance.fromJson(new String(Base64.getDecoder().decode(propertiesArray.get(0).getAsJsonObject().get("value").getAsString())), TextureProperty.class);
        if (textures.skin != null) builder.skin(textures.skin);
        if (textures.cape != null) builder.cape(textures.cape);

        return builder.build();
    }

}
