package se.snorbuse.coronaimporter.util;

import se.snorbuse.coronaimporter.model.RestResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class RestClient {

    public RestClient() {
    }

    public void delete(String url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) createUrl(url).openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("DELETE");
            connection.connect();
            int responseCode = connection.getResponseCode();
            String responseMessage = connection.getResponseMessage();
            Logger.info("Response - Code: %s, %s", responseCode, responseMessage);
        } catch (IOException e) {
            throw new RuntimeException("Could not delete data", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public RestResponse put(String url, String data) {
        return execute("PUT", url, data);
    }

    public RestResponse post(String url, String data) {
        return execute("POST", url, data);
    }

    private RestResponse execute(String method, String url, String data) {
        HttpURLConnection connection = null;
        OutputStream outputStream = null;
        try {
            connection = (HttpURLConnection) createUrl(url).openConnection();
            connection.setRequestMethod(method);
            connection.addRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("Accept", "*/*");
            if (data != null) {
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Length", Integer.toString(data.length()));
                outputStream = connection.getOutputStream();
                outputStream.write(data.getBytes(StandardCharsets.UTF_8), 0, data.length());
                return new RestResponse(connection.getResponseCode(), connection.getResponseMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not post data", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException("Could not close output stream", e);
                }
            }
        }
        return null;
    }

    private void printHeaders(HttpURLConnection connection) {
        Map<String, List<String>> headers = connection.getHeaderFields();
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            Logger.info("Key : %s, Value: %s", entry.getKey(), entry.getValue());
        }
    }

    private URL createUrl(String url) {
        URL mUrl;
        try {
            mUrl = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not create URL, " + url);
        }
        return mUrl;
    }

}
