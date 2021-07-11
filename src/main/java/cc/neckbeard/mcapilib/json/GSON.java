package cc.neckbeard.mcapilib.json;

import cc.neckbeard.mcapilib.profiles.Profile;
import cc.neckbeard.mcapilib.profiles.TextureProperty;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GSON {

    public static final Gson instance = build();

    private static Gson build() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Profile.class, new ProfileDeserializer());
        builder.registerTypeAdapter(TextureProperty.class, new TexturePropertyDeserializer());
        return builder.create();
    }

}
