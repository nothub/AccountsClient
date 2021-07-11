package cc.neckbeard.mcapilib.profiles;

import java.net.MalformedURLException;
import java.net.URL;

public enum Endpoint {
    ;

    public enum Get {

        USERNAME_TO_UUID("https://api.mojang.com/users/profiles/minecraft/"),
        UUID_TO_USERNAME("https://api.mojang.com/user/profile/"),
        UUID_TO_PROFILE("https://sessionserver.mojang.com/session/minecraft/profile/"),
        UUID_TO_NAMEHISTORY("https://api.mojang.com/user/profiles/", "/names"),
        ;

        private final String url;
        private final String suffix;

        Get(String url) {
            this.url = url;
            this.suffix = "";
        }

        Get(String url, String suffix) {
            this.url = url;
            this.suffix = suffix;
        }

        public URL url(String value) throws MalformedURLException {
            return new URL(url + value + suffix);
        }

    }

    public enum Post {

        USERNAMES_TO_UUID("https://api.mojang.com/profiles/minecraft"),
        ;

        private final String url;

        Post(String url) {
            this.url = url;
        }

        public URL url() {
            try {
                return new URL(url);
            } catch (MalformedURLException e) {
                throw new IllegalStateException("Someone broke the internet! Not a valid URL: " + url);
            }
        }

    }

}
