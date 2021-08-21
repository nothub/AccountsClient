package cc.neckbeard.mcapilib.profiles;


import cc.neckbeard.utils.UuidConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpProfileRepositoryIntegrationTests {

    @Test
    public void findProfilesByNames_existingNameProvided_returnsProfile() {
        ProfileRepository repository = new HttpProfileRepository();

        Profile[] profiles = repository.findProfilesByNames("mollstam");

        Assertions.assertEquals(1, profiles.length);
        Assertions.assertEquals("mollstam", profiles[0].name);
        Assertions.assertEquals("f8cdb6839e9043eea81939f85d9c5d69", profiles[0].id);
    }

    @Test
    public void findProfilesByNames_List_existingNameProvided_returnsProfile() {
        ProfileRepository repository = new HttpProfileRepository();

        List<String> list = new ArrayList<>();
        list.add("mollstam");
        List<Profile> profiles = repository.findProfilesByNames(list);

        Assertions.assertEquals(1, profiles.size());
        Assertions.assertEquals("mollstam", profiles.get(0).name);
        Assertions.assertEquals("f8cdb6839e9043eea81939f85d9c5d69", profiles.get(0).id);
    }

    @Test
    public void findProfileByName_existingNameProvided_returnsProfile() throws MalformedURLException {
        ProfileRepository repository = new HttpProfileRepository();

        Profile profile = repository.findProfileByName("mollstam");

        Assertions.assertEquals("mollstam", profile.name);
        Assertions.assertEquals("f8cdb6839e9043eea81939f85d9c5d69", profile.id);
    }

    @Test
    public void findProfileByUuid_existingNameProvided_returnsProfile() {
        ProfileRepository repository = new HttpProfileRepository();
        final String uuid = "7125ba8b1c864508b92bb5c042ccfe2b";

        Profile profile = repository.findProfileByUuid(UuidConverter.of(uuid));

        Assertions.assertEquals("KrisJelbring", profile.name);
        Assertions.assertEquals(uuid, profile.id);
    }

    @Test
    public void findProfileByUuid_StringDashed_existingNameProvided_returnsProfile() {
        ProfileRepository repository = new HttpProfileRepository();

        Profile profile = repository.findProfileByUuid("7125ba8b-1c86-4508-b92b-b5c042ccfe2b");

        Assertions.assertEquals("KrisJelbring", profile.name);
        Assertions.assertEquals("7125ba8b1c864508b92bb5c042ccfe2b", profile.id);
    }

    @Test
    public void findProfileByUuid_StringUndashed_existingNameProvided_returnsProfile() {
        ProfileRepository repository = new HttpProfileRepository();

        Profile profile = repository.findProfileByUuid("7125ba8b1c864508b92bb5c042ccfe2b");

        Assertions.assertEquals("KrisJelbring", profile.name);
        Assertions.assertEquals("7125ba8b1c864508b92bb5c042ccfe2b", profile.id);
    }

    @Test
    public void findProfilesByNames_existingMultipleNamesProvided_returnsProfiles() {
        ProfileRepository repository = new HttpProfileRepository();

        Profile[] profiles = repository.findProfilesByNames("mollstam", "KrisJelbring");

        Assertions.assertEquals(2, profiles.length, 2);
        Assertions.assertEquals("mollstam", profiles[1].name);
        Assertions.assertEquals("f8cdb6839e9043eea81939f85d9c5d69", profiles[1].id);
        Assertions.assertEquals("KrisJelbring", profiles[0].name);
        Assertions.assertEquals("7125ba8b1c864508b92bb5c042ccfe2b", profiles[0].id);
    }

    @Test
    public void findProfilesByNames_List_existingMultipleNamesProvided_returnsProfiles() {
        ProfileRepository repository = new HttpProfileRepository();

        List<String> list = new ArrayList<>();
        list.add("mollstam");
        list.add("KrisJelbring");
        java.util.List<Profile> profiles = repository.findProfilesByNames(list);

        Assertions.assertEquals(2, profiles.size(), 2);
        Assertions.assertEquals("mollstam", profiles.get(1).name);
        Assertions.assertEquals("f8cdb6839e9043eea81939f85d9c5d69", profiles.get(1).id);
        Assertions.assertEquals("KrisJelbring", profiles.get(0).name);
        Assertions.assertEquals("7125ba8b1c864508b92bb5c042ccfe2b", profiles.get(0).id);
    }

    @Test
    public void findProfilesByNames_nonExistingNameProvided_returnsEmptyArray() {
        ProfileRepository repository = new HttpProfileRepository();

        Profile[] profiles = repository.findProfilesByNames("doesnotexist$*not even legal");

        Assertions.assertEquals(0, profiles.length);
    }

    @Test
    public void findProfilesByNames_List_nonExistingNameProvided_returnsEmptyArray() {
        ProfileRepository repository = new HttpProfileRepository();

        List<String> list = new ArrayList<>();
        list.add("doesnotexist$*not even legal");
        List<Profile> profiles = repository.findProfilesByNames(list);

        Assertions.assertEquals(0, profiles.size());
    }

    @Test
    public void findProfileByName_nonExistingNameProvided_returnsNull() throws MalformedURLException {
        ProfileRepository repository = new HttpProfileRepository();
        Assertions.assertNull(repository.findProfileByName("doesnotexist$*not even legal"));
    }

    @Test
    public void findProfileByUuid_nonExistingUuidStringProvided_returnsNull() {
        ProfileRepository repository = new HttpProfileRepository();
        Assertions.assertNull(repository.findProfileByUuid("000b6e11683e4921b4738a8429e51000"));
    }

    @Test
    public void findProfileByUuid_nonExistingUuidProvided_returnsNull() {
        ProfileRepository repository = new HttpProfileRepository();
        Assertions.assertNull(repository.findProfileByUuid(UuidConverter.of("000b6e11683e4921b4738a8429e51000")));
    }

    @SuppressWarnings("HttpUrlsUsage")
    @Test
    public void loadTextures_capeUuidProvided_returnsSkinAndCape() throws MalformedURLException {
        ProfileRepository repository = new HttpProfileRepository();
        Profile profile = repository.findProfileByUuid("1c063715395b4db9bc2ad5dfd20366f7");
        Assertions.assertEquals(new URL("http://textures.minecraft.net/texture/9a44542ed4b5aaac887e7aac49cd19dcc97eb1f6c51995691ad4f1c006153ff6"), profile.getTextures().skin);
        Assertions.assertEquals(new URL("http://textures.minecraft.net/texture/bcfbe84c6542a4a5c213c1cacf8979b5e913dcb4ad783a8b80e3c4a7d5c8bdac"), profile.getTextures().cape);
    }

    @Test
    public void loadTextures_skinUuidProvided_returnsSkin() {
        ProfileRepository repository = new HttpProfileRepository();
        Profile profile = repository.findProfileByUuid("e8cb6e11683e4921b4738a8429e51ea1");
        Assertions.assertNotNull(profile.getTextures().skin);
        Assertions.assertNull(profile.getTextures().cape);
    }

    @Test
    public void nameTest_firstName_returnsIsCurrent() {
        Name name = new Name("", null);
        Assertions.assertTrue(name.isCurrent());
    }

    @Test
    public void nameTest_ChangedName_returnsNotIsCurrent() {
        Name name = new Name("", "");
        Assertions.assertFalse(name.isCurrent());
    }

    @Test
    public void loadNameHistory_historyUuidProvided_returnsHistory() {
        ProfileRepository repository = new HttpProfileRepository();
        Profile profile = repository.findProfileByUuid("e8cb6e11683e4921b4738a8429e51ea1");
        List<Name> history = profile.getNameHistory();
        Assertions.assertNotNull(history);
        Assertions.assertTrue(history.size() >= 2);
    }

    @Test
    public void loadNameHistory_singleNameUuidProvided_returnsHistory() {
        ProfileRepository repository = new HttpProfileRepository();
        Profile profile = repository.findProfileByUuid("069a79f444e94726a5befca90e38aaf5");
        List<Name> history = profile.getNameHistory();
        Assertions.assertNotNull(history);
        Assertions.assertEquals(1, history.size());
    }

}
