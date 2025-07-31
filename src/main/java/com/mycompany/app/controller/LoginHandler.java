package com.mycompany.app.controller;

import com.mycompany.app.model.user.UserManager;

import com.mycompany.app.model.user.User;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class LoginHandler implements HttpHandler {
    private static final Gson gson = new Gson();

    static class LoginRequest {
        String username;
    }
    static class LoginResponse {
        boolean success;
        String role;
    }

    @Override
    public void handle(HttpExchange exchange) {
        try {
            InputStreamReader reader = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
            LoginRequest request = gson.fromJson(reader, LoginRequest.class);

            User user = UserManager.login(request.username);

            LoginResponse response = new LoginResponse();
            response.success = (user != null);
            response.role = (user != null) ? user.getRole() : null;

            String json = gson.toJson(response);
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.sendResponseHeaders(200, json.length());
            OutputStream os = exchange.getResponseBody();
            os.write(json.getBytes(StandardCharsets.UTF_8));
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
