package cc.neckbeard.mcapilib.json;

import cc.neckbeard.mcapilib.profiles.TextureProperty;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;

public class TexturePropertyDeserializer implements JsonDeserializer<TextureProperty> {

    @Override
    public TextureProperty deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject texturesObject = json.getAsJsonObject().get("textures").getAsJsonObject();
        // skin
        URL skin = null;
        try {
            skin = new URL(texturesObject.get("SKIN").getAsJsonObject().get("url").getAsString());
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        // cape
        final JsonElement capeElement = texturesObject.get("CAPE");
        if (capeElement == null) return new TextureProperty(skin, null);
        URL cape = null;
        try {
            cape = new URL(capeElement.getAsJsonObject().get("url").getAsString());
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        return new TextureProperty(skin, cape);
    }

}
