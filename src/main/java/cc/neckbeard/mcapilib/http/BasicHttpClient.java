package cc.neckbeard.mcapilib.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class BasicHttpClient implements HttpClient {

    public BasicHttpClient() {
    }

    @Override
    public String get(URL url, List<HttpHeader> headers) throws IOException {
        return get(url, null, headers);
    }

    @Override
    public String get(URL url, Proxy proxy, List<HttpHeader> headers) throws IOException {
        HttpURLConnection connection = connect("GET", false, proxy, url, headers);
        return read(connection);
    }

    @Override
    public String post(URL url, HttpBody body, List<HttpHeader> headers) throws IOException {
        return post(url, null, body, headers);
    }

    @Override
    public String post(URL url, Proxy proxy, HttpBody body, List<HttpHeader> headers) throws IOException {
        HttpURLConnection connection = connect("POST", true, proxy, url, headers);
        send(connection, body.getBytes());
        return read(connection);
    }

    private HttpURLConnection connect(String type, boolean output, Proxy proxy, URL url, List<HttpHeader> headers) throws IOException {
        if (proxy == null) proxy = Proxy.NO_PROXY;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);
        connection.setRequestMethod(type);
        for (HttpHeader header : headers) {
            connection.setRequestProperty(header.getName(), header.getValue());
        }
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(output);
        return connection;
    }

    private String read(HttpURLConnection connection) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = reader.lines().collect(Collectors.joining(System.lineSeparator()));
        reader.close();
        return response;
    }

    private void send(HttpURLConnection connection, byte[] bytes) throws IOException {
        DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
        writer.write(bytes);
        writer.flush();
        writer.close();
    }

}
