package cc.neckbeard.mcapilib.profiles;

import java.net.URL;
import java.util.Arrays;

public class Textures {

    public final URL skin;
    public final URL cape;
    public final Model model;

    public Textures(URL skin, URL cape, Model model) {
        this.skin = skin;
        this.cape = cape;
        this.model = model;
    }

    public enum Model {
        CLASSIC,
        SLIM,
        UNKNOWN;

        public static Model getByName(String name) {
            return Arrays
                .stream(values())
                .filter(m -> m.toString().equalsIgnoreCase(name))
                .findAny()
                .orElse(UNKNOWN);
        }
    }

}
