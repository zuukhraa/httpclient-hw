package ru.kpfu.itis.shagiakhmetova.net;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();

        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        params.put("userId", "1");
        params.put("photo", "none");


        System.out.println(new HttpClientRealisation().get("https://postman-echo.com/get", headers, params));
        System.out.println(new HttpClientRealisation().post("https://postman-echo.com/post", headers, params));
    }
}
