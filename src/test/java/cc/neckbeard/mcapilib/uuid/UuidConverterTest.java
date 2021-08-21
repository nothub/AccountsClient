package cc.neckbeard.mcapilib.uuid;

import cc.neckbeard.utils.UuidConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class UuidConverterTest {

    @Test
    void conversion() {
        String uuidDashed = "f8cdb683-9e90-43ee-a819-39f85d9c5d69";
        String uuidUndashed = "f8cdb6839e9043eea81939f85d9c5d69";
        UUID uuid = UUID.fromString(uuidDashed);
        Assertions.assertEquals(uuid, UuidConverter.of(uuidDashed));
        Assertions.assertEquals(uuid, UuidConverter.of(uuidUndashed));
    }

    @Test
    void exception() {
        Assertions.assertThrows(NullPointerException.class, () -> UuidConverter.of(null));
        Assertions.assertDoesNotThrow(() -> UuidConverter.of("00000000000000000000000000000000"));
    }

    @Test
    void nulled() {
        Assertions.assertNull(UuidConverter.of(""));
        Assertions.assertNull(UuidConverter.of(":3"));
        Assertions.assertNull(UuidConverter.of("________________________________"));
        Assertions.assertNull(UuidConverter.of(String.valueOf((UUID) null)));
    }

}
