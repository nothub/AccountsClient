package cc.neckbeard.mcapilib.profiles;

import cc.neckbeard.mcapilib.http.HttpBody;
import cc.neckbeard.mcapilib.http.HttpClient;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static cc.neckbeard.mcapilib.profiles.Endpoint.Post.USERNAMES_TO_UUID;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HttpProfileRepositoryTests {

    private final Gson gson = new Gson();

    private static Profile getProfile(String name) throws MalformedURLException {
        return ((ProfileRepository) new HttpProfileRepository()).findProfileByName(name);
    }

    private void setProfilesForUrl(HttpClient mock, URL url, Profile[] profiles) throws IOException {
        String jsonString = gson.toJson(profiles);
        when(mock.post(eq(url), any(HttpBody.class), ArgumentMatchers.anyList())).thenReturn(jsonString);
    }

    @Test
    public void findProfilesByCriteria_someProfileNames_returnsExpectedProfiles() throws Exception{
        HttpClient client = mock(HttpClient.class);

        Profile someProfile = getProfile("mollstam");
        Profile someOtherProfile = getProfile("KrisJelbring");
        Profile[] profiles = {someProfile, someOtherProfile};

        setProfilesForUrl(client, USERNAMES_TO_UUID.url(), profiles);
        ProfileRepository repository = new HttpProfileRepository(client);

        Profile[] actual = repository.findProfilesByNames("mollstam", "KrisJelbring");

        Assertions.assertEquals(2, actual.length);
        Assertions.assertEquals("mollstam", actual[0].getName());
        Assertions.assertEquals("KrisJelbring", actual[1].getName());
    }

}
