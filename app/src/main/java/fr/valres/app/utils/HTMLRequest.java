package fr.valres.app.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class HTMLRequest {

    /**
     * @param url url to get
     * @return response message from server or null if error
     */
    public static String get(String url) {
        // make a get request to url
        try {
            URLConnection connection = new URL(url).openConnection();
            HttpURLConnection http = (HttpURLConnection)connection;
            http.setRequestMethod("GET");
            http.setDoOutput(true);
            http.addRequestProperty("Content-Type", "application/json");
            http.addRequestProperty("Accept", "application/json");
            http.connect();

            return http.getResponseMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param url url to post to
     * @param data data to post
     * @return response message from server or null if error
     */
    public static String post(String url, HashMap<String, String> data) {
        // make a post request to url with data
        try {
            URLConnection connection = new URL(url).openConnection();
            HttpURLConnection http = (HttpURLConnection)connection;
            http.setRequestMethod("POST"); // PUT is another valid option
            http.setDoOutput(true);
            http.addRequestProperty("Content-Type", "application/json");
            http.addRequestProperty("Accept", "application/json");

            for(String key : data.keySet()) {
                http.addRequestProperty(key, data.get(key));
            }

            http.connect();

            return http.getResponseMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param url url to put to
     * @param data data to put
     * @return response message from server or null if error
     */
    public static String put(String url, String data) {
        // make a put request to url with data
        try {
            URLConnection connection = new URL(url).openConnection();
            HttpURLConnection http = (HttpURLConnection)connection;
            http.setRequestMethod("PUT");
            http.setDoOutput(true);
            http.addRequestProperty("Content-Type", "application/json");
            http.addRequestProperty("Accept", "application/json");
            http.connect();

            return http.getResponseMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
