package cc.neckbeard.mcapilib.profiles;

import cc.neckbeard.mcapilib.http.BasicHttpClient;
import cc.neckbeard.mcapilib.http.HttpBody;
import cc.neckbeard.mcapilib.http.HttpClient;
import cc.neckbeard.mcapilib.http.HttpHeader;
import cc.neckbeard.mcapilib.json.GSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static cc.neckbeard.mcapilib.profiles.Endpoint.Get.*;
import static cc.neckbeard.mcapilib.profiles.Endpoint.Post.USERNAMES_TO_UUID;

public class HttpProfileRepository implements ProfileRepository {

    private static final Logger log = LoggerFactory.getLogger("AccountsClient");

    // You're not allowed to request more than 100 profiles per go.
    private static final int PROFILES_PER_REQUEST = 100;

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
        List<HttpHeader> headers = new ArrayList<>();
        headers.add(new HttpHeader("Content-Type", "application/json"));
        try {
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
        } catch (IOException e) {
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
        } catch (IOException e) {
            // TODO: logging and allowing consumer to react?
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Profile findProfileByUuid(String uuid) {
        try {
            return get(UUID_TO_USERNAME.url(uuid));
        } catch (IOException e) {
            // TODO: logging and allowing consumer to react?
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Profile findProfileByUuid(UUID uuid) {
        return findProfileByUuid(uuid.toString());
    }

    private Profile get(URL url) throws IOException {
        return get(url, Collections.emptyList());
    }

    private Profile get(URL url, List<HttpHeader> headers) throws IOException {
        String response = client.get(url, headers);
        return GSON.instance.fromJson(response, Profile.class);
    }

    private Profile[] post(URL url, HttpBody body, List<HttpHeader> headers) throws IOException {
        String response = client.post(url, body, headers);
        return GSON.instance.fromJson(response, Profile[].class);
    }

    private static HttpBody getHttpBody(List<String> namesBatch) {
        return new HttpBody(GSON.instance.toJson(namesBatch));
    }

}
