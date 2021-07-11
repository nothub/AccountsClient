package cc.neckbeard.mcapilib.profiles;

import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

public interface ProfileRepository {
    Profile[] findProfilesByNames(String... names);
    List<Profile> findProfilesByNames(List<String> names);
    Profile findProfileByName(String name) throws MalformedURLException;
    Profile findProfileByUuid(String uuid);
    Profile findProfileByUuid(UUID uuid);
}
