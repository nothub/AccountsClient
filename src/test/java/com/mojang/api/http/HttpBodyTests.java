package com.mojang.api.http;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class HttpBodyTests {

    @Test
    public void getBytes_constructedWithString_returnsBytesOfString() {
        String s = "someString";
        HttpBody body = new HttpBody(s);
        byte[] expected = s.getBytes();

        byte[] actual = body.getBytes();

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void getBytes_constructedWithoutString_returnsEmptyByteArray() {
        HttpBody body = new HttpBody(null);
        byte[] expected = new byte[0];

        byte[] actual = body.getBytes();

        assertThat(actual, is(equalTo(expected)));
    }

}