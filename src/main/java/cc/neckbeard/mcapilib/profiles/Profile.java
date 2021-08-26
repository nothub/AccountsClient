package cc.neckbeard.mcapilib.profiles;

import cc.neckbeard.mcapilib.http.BasicHttpClient;
import cc.neckbeard.mcapilib.json.GSON;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static cc.neckbeard.mcapilib.profiles.Endpoint.Get.UUID_TO_NAMEHISTORY;
import static cc.neckbeard.mcapilib.profiles.Endpoint.Get.UUID_TO_PROFILE;

public class Profile {

    public final String id;
    public final String name;
    public final boolean legacy;

    private final BasicHttpClient httpClient;

    private Textures textures;
    private List<Name> nameHistory;

    public Profile(String id, String name, boolean legacy) {
        this.id = id;
        this.name = name;
        this.legacy = legacy;
        this.textures = null;
        this.nameHistory = null;
        this.httpClient = new BasicHttpClient();
    }

    public Textures getTextures() {
        if (textures != null) return textures;
        try {
            textures = GSON.get().fromJson(
                httpClient.get(UUID_TO_PROFILE.url(id), Collections.emptyList()), Textures.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return textures;
    }

    public List<Name> getNameHistory() {
        if (nameHistory != null) return nameHistory;
        try {
            nameHistory = Arrays.asList(GSON.get().fromJson(
                httpClient.get(UUID_TO_NAMEHISTORY.url(id), Collections.emptyList()), Name[].class));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return nameHistory;
    }

}
