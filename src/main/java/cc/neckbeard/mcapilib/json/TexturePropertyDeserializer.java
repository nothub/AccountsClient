package cc.neckbeard.mcapilib.json;

import cc.neckbeard.mcapilib.profiles.Textures;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

public class TexturePropertyDeserializer implements JsonDeserializer<Textures> {

    @Override
    public Textures deserialize(JsonElement profileJson, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (!profileJson.isJsonObject()) throw new JsonParseException("invalid profile object");
        final JsonObject jsonObject = profileJson.getAsJsonObject();
        // properties (required)
        if (jsonObject.get("properties") == null || !jsonObject.get("properties").isJsonArray())
            throw new JsonParseException("invalid properties object");
        // validate array
        JsonArray propertiesArray = jsonObject.get("properties").getAsJsonArray();
        if (propertiesArray.size() == 0 || !propertiesArray.get(0).isJsonObject())
            throw new JsonParseException("invalid properties");
        // validate object
        final JsonObject propertyObject = propertiesArray.get(0).getAsJsonObject();
        if (propertyObject.get("name") == null || propertyObject.get("value") == null)
            throw new JsonParseException("invalid property");
        String propertyName;
        String propertyValue;
        try {
            propertyName = propertyObject.get("name").getAsString();
            propertyValue = propertyObject.get("value").getAsString();
        } catch (ClassCastException e) {
            throw new JsonParseException("invalid property");
        }
        if (!propertyName.equals("textures"))
            throw new JsonParseException("Property " + propertyName + " not implemented!");
        // decode and validate textures json
        JsonElement texturesJson = JsonParser.parseString(new String(Base64.getDecoder().decode(propertyValue)));
        if (!texturesJson.isJsonObject()
            || texturesJson.getAsJsonObject().get("textures") == null
            || !texturesJson.getAsJsonObject().get("textures").isJsonObject())
            throw new JsonParseException("invalid textures object");
        final JsonObject texturesObject = texturesJson.getAsJsonObject().get("textures").getAsJsonObject();
        // skin
        Textures.Model model = Textures.Model.CLASSIC;
        final JsonElement skinElement = texturesObject.get("SKIN");
        URL skin = null;
        if (skinElement != null && skinElement.isJsonObject()) {
            final JsonObject skinObject = skinElement.getAsJsonObject();
            if (skinObject.get("url") != null) {
                try {
                    skin = new URL(skinObject.get("url").getAsString());
                } catch (MalformedURLException | ClassCastException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (skinObject.get("metadata") != null && skinObject.get("metadata").isJsonObject()) {
                final JsonElement modelElement = skinObject.get("metadata").getAsJsonObject().get("model");
                try {
                    model = Textures.Model.getByName(modelElement.getAsString());
                } catch (ClassCastException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        // cape
        final JsonElement capeElement = texturesObject.get("CAPE");
        if (capeElement == null || !capeElement.isJsonObject()) return new Textures(skin, null, model);
        final JsonObject capeObject = capeElement.getAsJsonObject();
        URL cape = null;
        if (capeObject.get("url") != null) {
            try {
                cape = new URL(capeObject.get("url").getAsString());
            } catch (MalformedURLException | ClassCastException e) {
                System.out.println(e.getMessage());
            }
        }
        return new Textures(skin, cape, model);
    }

}
