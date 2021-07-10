package com.mojang.api.profiles;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HttpProfileRepositoryIntegrationTests {

    @Test
    public void findProfilesByNames_existingNameProvided_returnsProfile() throws Exception {
        ProfileRepository repository = new HttpProfileRepository("minecraft");

        Profile[] profiles = repository.findProfilesByNames("mollstam");

        Assertions.assertEquals(profiles.length, 1);
        Assertions.assertEquals(profiles[0].getName(), "mollstam");
        Assertions.assertEquals(profiles[0].getId(), "f8cdb6839e9043eea81939f85d9c5d69");
    }

    @Test
    public void findProfilesByNames_existingMultipleNamesProvided_returnsProfiles() throws Exception {
        ProfileRepository repository = new HttpProfileRepository("minecraft");

        Profile[] profiles = repository.findProfilesByNames("mollstam", "KrisJelbring");

        Assertions.assertEquals(profiles.length, 2);
        Assertions.assertEquals(profiles[1].getName(), "mollstam");
        Assertions.assertEquals(profiles[1].getId(), "f8cdb6839e9043eea81939f85d9c5d69");
        Assertions.assertEquals(profiles[0].getName(), "KrisJelbring");
        Assertions.assertEquals(profiles[0].getId(), "7125ba8b1c864508b92bb5c042ccfe2b");
    }

    @Test
    public void findProfilesByNames_nonExistingNameProvided_returnsEmptyArray() throws Exception {
        ProfileRepository repository = new HttpProfileRepository("minecraft");

        Profile[] profiles = repository.findProfilesByNames("doesnotexist$*not even legal");

        Assertions.assertEquals(profiles.length, 0);
    }
}
