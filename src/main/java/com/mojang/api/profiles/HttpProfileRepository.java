package com.mojang.api.profiles;

import com.google.gson.Gson;
import com.mojang.api.http.BasicHttpClient;
import com.mojang.api.http.HttpBody;
import com.mojang.api.http.HttpClient;
import com.mojang.api.http.HttpHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static com.mojang.api.profiles.Endpoint.Get.*;
import static com.mojang.api.profiles.Endpoint.Post.USERNAMES_TO_UUID;

public class HttpProfileRepository implements ProfileRepository {

    private static final Logger log = LoggerFactory.getLogger("AccountsClient");

    // You're not allowed to request more than 100 profiles per go.
    private static final int PROFILES_PER_REQUEST = 100;

    private static final Gson gson = new Gson();
    private final HttpClient client;

    public HttpProfileRepository() {
        this(BasicHttpClient.getInstance());
    }

    public HttpProfileRepository(HttpClient client) {
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
                Profile[] result = post(USERNAMES_TO_UUID.url(), body, headers);
                profiles.addAll(Arrays.asList(result));

                start = end;
                i++;
            } while (start < namesCount);
        } catch (Exception e) {
            // TODO: logging and allowing consumer to react?
            log.error(e.getMessage());
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
            return get(USERNAME_TO_UUID.url(name));
        } catch (Exception e) {
            // TODO: logging and allowing consumer to react?
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Profile findProfileByUuid(String uuid) {
        try {
            return get(UUID_TO_USERNAME.url(uuid));
        } catch (Exception e) {
            // TODO: logging and allowing consumer to react?
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Profile findProfileByUuid(UUID uuid) {
        return findProfileByUuid(uuid.toString());
    }

    @Override
    public Profile findProfileWithProperties(String uuid) {
        try {
            return getTextures(UUID_TO_PROFILE.url(uuid));
        } catch (Exception e) {
            // TODO: logging and allowing consumer to react?
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Profile findProfileWithProperties(UUID uuid) {
        return findProfileWithProperties(uuid.toString());
    }

    private Profile get(URL url) throws IOException {
        return get(url, Collections.emptyList());
    }

    private Profile get(URL url, List<HttpHeader> headers) throws IOException {
        String response = client.get(url, headers);
        return gson.fromJson(response, Profile.class);
    }

    private Profile getTextures(URL url) throws IOException {
        String response = client.get(url, Collections.emptyList());
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
