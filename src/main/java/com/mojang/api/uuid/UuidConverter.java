package com.mojang.api.uuid;

import java.util.UUID;
import java.util.regex.Pattern;

public class UuidConverter {

    private static final String FORMAT = "$1-$2-$3-$4-$5";
    private static final Pattern PATTERN = Pattern.compile("([0-9a-fA-F]{8})-?([0-9a-fA-F]{4})-?([0-9a-fA-F]{4})-?([0-9a-fA-F]{4})-?([0-9a-fA-F]{12})");

    /**
     * Generates a UUID Object from an UUID String.<br>
     * The String can either contain a dashed or undashed UUID.
     *
     * @param uuid UUID String
     * @return UUID Object
     * @throws UuidException Thrown if UUID is null or not valid.
     */
    public static UUID of(final String uuid) throws UuidException {
        if (uuid == null) throw new UuidException("UUID String is null");
        if (!PATTERN.matcher(uuid).matches()) {
            throw new UuidException("Invalid UUID String: " + uuid);
        }
        return UUID.fromString(uuid.replaceFirst(PATTERN.toString(), FORMAT));
    }

    /**
     * Generates a String from an UUID Object.<br>
     * The String will not contain any dashes (the way Mojang likes it :3).
     *
     * @param uuid UUID Object
     * @return UUID String
     * @throws UuidException Thrown if UUID is null.
     */
    public static String of(final UUID uuid) throws UuidException {
        if (uuid == null) throw new UuidException("UUID Object is null");
        return uuid.toString().replaceAll("-", "");
    }

}
