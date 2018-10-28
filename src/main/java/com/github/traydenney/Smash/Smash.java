package com.github.traydenney.Smash;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Smash {

/* Fix all of this shit */

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public String getSmashEvent(String url) throws IOException, JSONException {

        JSONObject json = readJsonFromUrl(url);
        System.out.println(json.toString());
        System.out.println(json.getJSONObject("entities").getJSONObject("tournament").get("name"));
        return(json.getJSONObject("entities").getJSONObject("tournament").get("details").toString());
    }


}
