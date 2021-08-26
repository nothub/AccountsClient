package cc.neckbeard.mcapilib.json;

import cc.neckbeard.mcapilib.profiles.Profile;
import cc.neckbeard.mcapilib.profiles.Textures;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GSON {

    private static final ThreadLocal<Gson> gson = ThreadLocal.withInitial(GSON::build);

    public static Gson get() {
        return gson.get();
    }

    private static Gson build() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Profile.class, new ProfileDeserializer());
        builder.registerTypeAdapter(Textures.class, new TexturePropertyDeserializer());
        return builder.create();
    }

}
