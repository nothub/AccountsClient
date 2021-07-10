package com.mojang.api.profiles;

import com.google.gson.Gson;
import com.mojang.api.http.BasicHttpClient;
import com.mojang.api.http.HttpBody;
import com.mojang.api.http.HttpClient;
import com.mojang.api.http.HttpHeader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class HttpProfileRepository implements ProfileRepository {

    // You're not allowed to request more than 100 profiles per go.
    private static final int PROFILES_PER_REQUEST = 100;

    private static final Gson gson = new Gson();
    private final String agent;
    private final HttpClient client;

    public HttpProfileRepository(String agent) {
        this(agent, BasicHttpClient.getInstance());
    }

    public HttpProfileRepository(String agent, HttpClient client) {
        this.agent = agent;
        this.client = client;
    }

    @Override
    public List<Profile> findProfilesByNames(List<String> names) {
        List<Profile> profiles = new ArrayList<>();
        try {

            List<HttpHeader> headers = new ArrayList<>();
            headers.add(new HttpHeader("Content-Type", "application/json"));

            int namesCount = names.size();
            int start = 0;
            int i = 0;
            do {
                int end = PROFILES_PER_REQUEST * (i + 1);
                if (end > namesCount) {
                    end = namesCount;
                }
                List<String> namesBatch = names.subList(start, end);
                HttpBody body = getHttpBody(namesBatch);
                Profile[] result = post(getProfilesUrl(), body, headers);
                profiles.addAll(Arrays.asList(result));

                start = end;
                i++;
            } while (start < namesCount);
        } catch (Exception e) {
            // TODO: logging and allowing consumer to react?
            System.err.println(e.getMessage());
        }

        return profiles;
    }

    @Override
    public Profile[] findProfilesByNames(String... names) {
        return findProfilesByNames(Arrays.asList(names)).toArray(new Profile[0]);
    }

    @Override
    public Profile findProfileByName(String name) {
        try {
            return get(getProfileUrl(name));
        } catch (Exception e) {
            // TODO: logging and allowing consumer to react?
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Profile findProfileByUuid(String uuid) {
        try {
            return get(getProfileUuidUrl(uuid));
        } catch (Exception e) {
            // TODO: logging and allowing consumer to react?
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Profile findProfileByUuid(UUID uuid) {
        return findProfileByUuid(uuid.toString());
    }

    private URL getProfilesUrl() throws MalformedURLException {
        // To lookup Minecraft profiles, agent should be "minecraft"
        // URL for POST requests
        return new URL("https://api.mojang.com/profiles/" + agent);
    }

    private URL getProfileUrl(String name) throws MalformedURLException {
        // To lookup Minecraft profiles, agent should be "minecraft"
        // URL for GET requests
        return new URL("https://api.mojang.com/users/profiles/" + agent + "/" + name);
    }

    private URL getProfileUuidUrl(String uuid) throws MalformedURLException {
        // To lookup Minecraft profiles, agent should be "minecraft"
        // URL for GET requests
        return new URL("https://api.mojang.com/user/profile/" + uuid);
    }

    private Profile get(URL url) throws IOException {
        return get(url, Collections.emptyList());
    }

    private Profile get(URL url, List<HttpHeader> headers) throws IOException {
        String response = client.get(url, headers);
        return gson.fromJson(response, Profile.class);
    }

    private Profile[] post(URL url, HttpBody body, List<HttpHeader> headers) throws IOException {
        String response = client.post(url, body, headers);
        return gson.fromJson(response, Profile[].class);
    }

    private static HttpBody getHttpBody(List<String> namesBatch) {
        return new HttpBody(gson.toJson(namesBatch));
    }

}
