package com.mojang.api.profiles;


import com.mojang.api.uuid.UuidConverter;
import com.mojang.api.uuid.UuidException;
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
        Assertions.assertEquals("mollstam", profiles[0].getName());
        Assertions.assertEquals("f8cdb6839e9043eea81939f85d9c5d69", profiles[0].getId());
    }

    @Test
    public void findProfilesByNames_List_existingNameProvided_returnsProfile() {
        ProfileRepository repository = new HttpProfileRepository();

        List<String> list = new ArrayList<>();
        list.add("mollstam");
        List<Profile> profiles = repository.findProfilesByNames(list);

        Assertions.assertEquals(1, profiles.size());
        Assertions.assertEquals("mollstam", profiles.get(0).getName());
        Assertions.assertEquals("f8cdb6839e9043eea81939f85d9c5d69", profiles.get(0).getId());
    }

    @Test
    public void findProfileByName_existingNameProvided_returnsProfile() {
        ProfileRepository repository = new HttpProfileRepository();

        Profile profile = repository.findProfileByName("mollstam");

        Assertions.assertEquals("mollstam", profile.getName());
        Assertions.assertEquals("f8cdb6839e9043eea81939f85d9c5d69", profile.getId());
    }

    @Test
    public void findProfileByUuid_existingNameProvided_returnsProfile() throws UuidException {
        ProfileRepository repository = new HttpProfileRepository();
        final String uuid = "7125ba8b1c864508b92bb5c042ccfe2b";

        Profile profile = repository.findProfileByUuid(UuidConverter.of(uuid));

        Assertions.assertEquals("KrisJelbring", profile.getName());
        Assertions.assertEquals(uuid, profile.getId());
    }

    @Test
    public void findProfileByUuid_StringDashed_existingNameProvided_returnsProfile() {
        ProfileRepository repository = new HttpProfileRepository();

        Profile profile = repository.findProfileByUuid("7125ba8b-1c86-4508-b92b-b5c042ccfe2b");

        Assertions.assertEquals("KrisJelbring", profile.getName());
        Assertions.assertEquals("7125ba8b1c864508b92bb5c042ccfe2b", profile.getId());
    }

    @Test
    public void findProfileByUuid_StringUndashed_existingNameProvided_returnsProfile() {
        ProfileRepository repository = new HttpProfileRepository();

        Profile profile = repository.findProfileByUuid("7125ba8b1c864508b92bb5c042ccfe2b");

        Assertions.assertEquals("KrisJelbring", profile.getName());
        Assertions.assertEquals("7125ba8b1c864508b92bb5c042ccfe2b", profile.getId());
    }

    @Test
    public void findProfilesByNames_existingMultipleNamesProvided_returnsProfiles() {
        ProfileRepository repository = new HttpProfileRepository();

        Profile[] profiles = repository.findProfilesByNames("mollstam", "KrisJelbring");

        Assertions.assertEquals(2, profiles.length, 2);
        Assertions.assertEquals("mollstam", profiles[1].getName());
        Assertions.assertEquals("f8cdb6839e9043eea81939f85d9c5d69", profiles[1].getId());
        Assertions.assertEquals("KrisJelbring", profiles[0].getName());
        Assertions.assertEquals("7125ba8b1c864508b92bb5c042ccfe2b", profiles[0].getId());
    }

    @Test
    public void findProfilesByNames_List_existingMultipleNamesProvided_returnsProfiles() {
        ProfileRepository repository = new HttpProfileRepository();

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

    @SuppressWarnings("HttpUrlsUsage")
    @Test
    public void findTexturesByUuid_capeUuidProvided_returnsSkinAndCape() throws MalformedURLException, UuidException {
        ProfileRepository repository = new HttpProfileRepository();
        Profile profile = repository.findProfileWithProperties("1c063715395b4db9bc2ad5dfd20366f7");
        Assertions.assertEquals(profile.getSkin().getUrl(), repository.findProfileWithProperties(UuidConverter.of("1c063715395b4db9bc2ad5dfd20366f7")).getSkin().getUrl());
        Assertions.assertEquals(new URL("http://textures.minecraft.net/texture/9a44542ed4b5aaac887e7aac49cd19dcc97eb1f6c51995691ad4f1c006153ff6"), profile.getSkin().getUrl());
        Assertions.assertEquals(new URL("http://textures.minecraft.net/texture/bcfbe84c6542a4a5c213c1cacf8979b5e913dcb4ad783a8b80e3c4a7d5c8bdac"), profile.getCape().getUrl());
    }

    @Test
    public void findTexturesByUuid_skinUuidProvided_returnsSkin() throws UuidException {
        ProfileRepository repository = new HttpProfileRepository();
        Profile profile = repository.findProfileWithProperties("e8cb6e11683e4921b4738a8429e51ea1");
        Assertions.assertEquals(profile.getSkin().getUrl(), repository.findProfileWithProperties(UuidConverter.of("e8cb6e11683e4921b4738a8429e51ea1")).getSkin().getUrl());
        Assertions.assertNotNull(profile.getSkin().getUrl());
        Assertions.assertNull(profile.getCape());
    }

    @Test
    public void findTexturesByUuid_nonExistingUuidProvided_returnsNull() throws UuidException {
        ProfileRepository repository = new HttpProfileRepository();
        Profile profile = repository.findProfileWithProperties("00000000000000042000000000000000");
        Assertions.assertNull(profile);
    }

}
