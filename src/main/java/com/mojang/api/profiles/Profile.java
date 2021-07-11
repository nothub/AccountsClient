package com.mojang.api.profiles;

import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class Profile {

    private static final Gson gson = new Gson();

    private String id;
    private String name;
    private List<Property> properties;
    private boolean legacy;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Texture getSkin() {
        return parseTexture("SKIN");
    }

    public Texture getCape() {
        return parseTexture("CAPE");
    }

    public boolean isLegacy() {
        return legacy;
    }

    private Texture parseTexture(String label) {
        final TextureContainer textureContainer = gson.fromJson(new String(Base64.getDecoder().decode(properties.get(0).value)), TextureContainer.class);
        return textureContainer.textures.get(label);
    }

    private static class Property {
        private String value;

        private String getValue() {
            return value;
        }
    }

    private static class TextureContainer {
        private Map<String, Texture> textures;
    }

    public static class Texture {
        private String url;
        private Model metadata;

        public URL getUrl() {
            try {
                return new URL(url);
            } catch (MalformedURLException e) {
                return null;
            }
        }

        public String getModel() {
            if (metadata == null) return null;
            return metadata.model;
        }

        private static class Model {
            private String model;
        }
    }

}
