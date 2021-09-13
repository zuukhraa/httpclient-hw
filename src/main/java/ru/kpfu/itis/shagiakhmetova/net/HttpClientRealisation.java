package ru.kpfu.itis.shagiakhmetova.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpClientRealisation implements HttpClient {
    @Override
    public String get(String url, Map<String, String> headers, Map<String, String> params) {
        try {
            int counter = 0;
            StringBuilder stringBuilder = new StringBuilder(url);
            stringBuilder.append("?");

            for (String key : params.keySet()) {
                if (counter != 0) {
                    stringBuilder.append("&");
                }
                stringBuilder.append(key).append("=").append(params.get(key));
                counter++;
            }
            url = stringBuilder.toString();
            URL url1 = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
            StringBuilder content = new StringBuilder();

            for (String key : headers.keySet()) {
                connection.setRequestProperty(key, headers.get(key));
            }
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            System.out.println(connection.getResponseCode());

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {

                String input;
                while ((input = reader.readLine()) != null) {
                    content.append(input);
                }
                connection.disconnect();
                return content.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String post(String url, Map<String, String> headers, Map<String, String> params) {
        try {
            URL url2 = new URL(url);
            int counter = 0;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{");

            for (String key : params.keySet()) {
                if (counter != 0) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(key).append(":").append(params.get(key));
                counter++;
            }
            stringBuilder.append("}");
            url = stringBuilder.toString();

            HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
            connection.setRequestMethod("POST");

            for (String key : headers.keySet()) {
                connection.setRequestProperty(key, headers.get(key));
            }
            connection.setDoOutput(true);
            String jsonInputString = url;

            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }

            System.out.println(connection.getResponseCode());
            StringBuilder content = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)))
            {
                String input;
                while ((input = reader.readLine()) != null) {
                    content.append(input.trim());
                }
                return content.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
