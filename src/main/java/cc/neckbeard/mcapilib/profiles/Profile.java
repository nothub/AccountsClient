package cc.neckbeard.mcapilib.profiles;

import java.net.URL;

public class Profile {

    public final String id;
    public final String name;
    public final URL skin;
    public final URL cape;
    public final boolean legacy;

    public Profile(String id, String name, URL skin, URL cape, boolean legacy) {
        this.id = id;
        this.name = name;
        this.skin = skin;
        this.cape = cape;
        this.legacy = legacy;
    }

    public String getId() {
        // backward compatibility
        return id;
    }

    public String getName() {
        // backward compatibility
        return name;
    }

    public enum SkinModel {
        STANDARD,
        SLIM
    }

    public static final class Builder {

        private String id;
        private String name;
        private URL skin;
        private URL cape;
        private boolean legacy;

        private Builder() {
        }

        public static Builder getInstance() {
            return new Builder();
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder skin(URL skin) {
            this.skin = skin;
            return this;
        }

        public Builder cape(URL cape) {
            this.cape = cape;
            return this;
        }

        public Builder legacy() {
            this.legacy = true;
            return this;
        }

        public Profile build() {
            return new Profile(id, name, skin, cape, legacy);
        }

    }

}
