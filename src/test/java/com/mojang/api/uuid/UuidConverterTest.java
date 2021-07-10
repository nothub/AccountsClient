package com.mojang.api.uuid;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class UuidConverterTest {

    @Test
    void conversion() throws UuidException {
        String uuidDashed = "f8cdb683-9e90-43ee-a819-39f85d9c5d69";
        String uuidUndashed = "f8cdb6839e9043eea81939f85d9c5d69";
        UUID uuid = UUID.fromString(uuidDashed);
        Assertions.assertEquals(uuid, UuidConverter.of(uuidDashed));
        Assertions.assertEquals(uuid, UuidConverter.of(uuidUndashed));
        Assertions.assertEquals(uuidUndashed, UuidConverter.of(uuid));
    }

    @Test
    void exception() {
        Assertions.assertThrows(UuidException.class, () -> UuidConverter.of((String) null));
        Assertions.assertThrows(UuidException.class, () -> UuidConverter.of((UUID) null));
        Assertions.assertThrows(UuidException.class, () -> UuidConverter.of(""));
        Assertions.assertThrows(UuidException.class, () -> UuidConverter.of(":3"));
        Assertions.assertThrows(UuidException.class, () -> UuidConverter.of("________________________________"));
        Assertions.assertDoesNotThrow(() -> UuidConverter.of("00000000000000000000000000000000"));
    }

}
