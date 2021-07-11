package com.mojang.api.profiles;

import java.util.List;
import java.util.UUID;

public interface ProfileRepository {
    Profile[] findProfilesByNames(String... names);
    List<Profile> findProfilesByNames(List<String> names);
    Profile findProfileByName(String name);
    Profile findProfileByUuid(String uuid);
    Profile findProfileByUuid(UUID uuid);
    Profile findProfileWithProperties(String uuid);
    Profile findProfileWithProperties(UUID uuid);
}
