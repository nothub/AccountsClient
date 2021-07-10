package com.mojang.api.profiles;

import java.util.UUID;

public interface ProfileRepository {
    Profile[] findProfilesByNames(String... names);
    Profile findProfileByName(String name);
    Profile findProfileByUuid(UUID uuid);
}
