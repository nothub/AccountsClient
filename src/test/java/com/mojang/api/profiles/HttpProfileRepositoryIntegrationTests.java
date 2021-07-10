package com.mojang.api.profiles;


import com.mojang.api.uuid.UuidConverter;
import com.mojang.api.uuid.UuidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class HttpProfileRepositoryIntegrationTests {

    @Test
    public void findProfilesByNames_existingNameProvided_returnsProfile() {
        ProfileRepository repository = new HttpProfileRepository("minecraft");

        Profile[] profiles = repository.findProfilesByNames("mollstam");

        Assertions.assertEquals(1, profiles.length);
        Assertions.assertEquals("mollstam", profiles[0].getName());
        Assertions.assertEquals("f8cdb6839e9043eea81939f85d9c5d69", profiles[0].getId());
    }

    @Test
    public void findProfilesByNames_List_existingNameProvided_returnsProfile() {
        ProfileRepository repository = new HttpProfileRepository("minecraft");

        List<String> list = new ArrayList<>();
        list.add("mollstam");
        List<Profile> profiles = repository.findProfilesByNames(list);

        Assertions.assertEquals(1, profiles.size());
        Assertions.assertEquals("mollstam", profiles.get(0).getName());
        Assertions.assertEquals("f8cdb6839e9043eea81939f85d9c5d69", profiles.get(0).getId());
    }

    @Test
    public void findProfileByName_existingNameProvided_returnsProfile() {
        ProfileRepository repository = new HttpProfileRepository("minecraft");

        Profile profile = repository.findProfileByName("mollstam");

        Assertions.assertEquals("mollstam", profile.getName());
        Assertions.assertEquals("f8cdb6839e9043eea81939f85d9c5d69", profile.getId());
    }

    @Test
    public void findProfileByUuid_existingNameProvided_returnsProfile() throws UuidException {
        ProfileRepository repository = new HttpProfileRepository("minecraft");
        final String uuid = "7125ba8b1c864508b92bb5c042ccfe2b";

        Profile profile = repository.findProfileByUuid(UuidConverter.of(uuid));

        Assertions.assertEquals("KrisJelbring", profile.getName());
        Assertions.assertEquals(uuid, profile.getId());
    }

    @Test
    public void findProfileByUuid_StringDashed_existingNameProvided_returnsProfile() {
        ProfileRepository repository = new HttpProfileRepository("minecraft");

        Profile profile = repository.findProfileByUuid("7125ba8b-1c86-4508-b92b-b5c042ccfe2b");

        Assertions.assertEquals("KrisJelbring", profile.getName());
        Assertions.assertEquals("7125ba8b1c864508b92bb5c042ccfe2b", profile.getId());
    }

    @Test
    public void findProfileByUuid_StringUndashed_existingNameProvided_returnsProfile() {
        ProfileRepository repository = new HttpProfileRepository("minecraft");

        Profile profile = repository.findProfileByUuid("7125ba8b1c864508b92bb5c042ccfe2b");

        Assertions.assertEquals("KrisJelbring", profile.getName());
        Assertions.assertEquals("7125ba8b1c864508b92bb5c042ccfe2b", profile.getId());
    }

    @Test
    public void findProfilesByNames_existingMultipleNamesProvided_returnsProfiles() {
        ProfileRepository repository = new HttpProfileRepository("minecraft");

        Profile[] profiles = repository.findProfilesByNames("mollstam", "KrisJelbring");

        Assertions.assertEquals(2, profiles.length, 2);
        Assertions.assertEquals("mollstam", profiles[1].getName());
        Assertions.assertEquals("f8cdb6839e9043eea81939f85d9c5d69", profiles[1].getId());
        Assertions.assertEquals("KrisJelbring", profiles[0].getName());
        Assertions.assertEquals("7125ba8b1c864508b92bb5c042ccfe2b", profiles[0].getId());
    }

    @Test
    public void findProfilesByNames_List_existingMultipleNamesProvided_returnsProfiles() {
        ProfileRepository repository = new HttpProfileRepository("minecraft");

        List<String> list = new ArrayList<>();
        list.add("mollstam");
        list.add("KrisJelbring");
        java.util.List<Profile> profiles = repository.findProfilesByNames(list);

        Assertions.assertEquals(2, profiles.size(), 2);
        Assertions.assertEquals("mollstam", profiles.get(1).getName());
        Assertions.assertEquals("f8cdb6839e9043eea81939f85d9c5d69", profiles.get(1).getId());
        Assertions.assertEquals("KrisJelbring", profiles.get(0).getName());
        Assertions.assertEquals("7125ba8b1c864508b92bb5c042ccfe2b", profiles.get(0).getId());
    }

    @Test
    public void findProfilesByNames_nonExistingNameProvided_returnsEmptyArray() {
        ProfileRepository repository = new HttpProfileRepository("minecraft");

        Profile[] profiles = repository.findProfilesByNames("doesnotexist$*not even legal");

        Assertions.assertEquals(0, profiles.length);
    }

    @Test
    public void findProfilesByNames_List_nonExistingNameProvided_returnsEmptyArray() {
        ProfileRepository repository = new HttpProfileRepository("minecraft");

        List<String> list = new ArrayList<>();
        list.add("doesnotexist$*not even legal");
        List<Profile> profiles = repository.findProfilesByNames(list);

        Assertions.assertEquals(0, profiles.size());
    }

}
