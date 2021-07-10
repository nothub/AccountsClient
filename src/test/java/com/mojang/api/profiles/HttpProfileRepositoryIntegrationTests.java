package com.mojang.api.profiles;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HttpProfileRepositoryIntegrationTests {

    @Test
    public void findProfilesByNames_existingNameProvided_returnsProfile() throws Exception {
        ProfileRepository repository = new HttpProfileRepository("minecraft");

        Profile[] profiles = repository.findProfilesByNames("mollstam");

        Assertions.assertEquals(1, profiles.length);
        Assertions.assertEquals("mollstam", profiles[0].getName());
        Assertions.assertEquals("f8cdb6839e9043eea81939f85d9c5d69", profiles[0].getId());
    }

    @Test
    public void findProfilesByNames_existingMultipleNamesProvided_returnsProfiles() throws Exception {
        ProfileRepository repository = new HttpProfileRepository("minecraft");

        Profile[] profiles = repository.findProfilesByNames("mollstam", "KrisJelbring");

        Assertions.assertEquals(2, profiles.length, 2);
        Assertions.assertEquals("mollstam", profiles[1].getName());
        Assertions.assertEquals("f8cdb6839e9043eea81939f85d9c5d69", profiles[1].getId());
        Assertions.assertEquals("KrisJelbring", profiles[0].getName());
        Assertions.assertEquals("7125ba8b1c864508b92bb5c042ccfe2b", profiles[0].getId());
    }

    @Test
    public void findProfilesByNames_nonExistingNameProvided_returnsEmptyArray() throws Exception {
        ProfileRepository repository = new HttpProfileRepository("minecraft");

        Profile[] profiles = repository.findProfilesByNames("doesnotexist$*not even legal");

        Assertions.assertEquals(0, profiles.length);
    }
}
