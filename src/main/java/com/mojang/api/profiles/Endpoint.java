package com.mojang.api.profiles;

import java.net.MalformedURLException;
import java.net.URL;

public enum Endpoint {
;
    public enum Get{

        USERNAME_TO_UUID("https://api.mojang.com/users/profiles/minecraft/"),
        UUID_TO_USERNAME("https://api.mojang.com/user/profile/"),
        UUID_TO_PROFILE("https://sessionserver.mojang.com/session/minecraft/profile/"),
        ;

        private final String url;

        Get(String url) {
            this.url = url;
        }

        public URL url(String value) {
            try {
                return new URL(url + value);
            } catch (MalformedURLException e) {
                throw new IllegalStateException("Someone broke the internet! Not a valid URL: " + url + value);
            }
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
