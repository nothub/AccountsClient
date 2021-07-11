package cc.neckbeard.mcapilib.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HttpBodyTests {

    @Test
    public void getBytes_constructedWithString_returnsBytesOfString() {
        String s = "someString";
        HttpBody body = new HttpBody(s);
        byte[] expected = s.getBytes();

        byte[] actual = body.getBytes();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void getBytes_constructedWithoutString_returnsEmptyByteArray() {
        HttpBody body = new HttpBody(null);
        byte[] expected = new byte[0];

        byte[] actual = body.getBytes();

        Assertions.assertArrayEquals(expected, actual);
    }

}
