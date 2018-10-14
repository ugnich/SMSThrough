package com.ugnich.smsthrough;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Base64;
import javax.net.ssl.HttpsURLConnection;

public class Utils {

    public static String sendPostRequest(String url, String body) {
        return sendPostRequest(url, body, null);
    }

    public static String sendPostRequest(String url, String body, String auth) {
        String ret = null;
        try {
            HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
            conn.setRequestProperty("User-Agent", "Java/1.0");
            conn.setRequestProperty("Content-Length", Integer.toString(body.length()));
            if (auth != null) {
                conn.setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString(auth.getBytes()));
            }
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.connect();

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(body);
            wr.close();

            int status = conn.getResponseCode();
            if (status >= 200 && status < 300) {
                ret = streamToString(conn.getInputStream());
            } else {
                System.err.println("sendPostRequest:" + streamToString(conn.getErrorStream()));
            }

            conn.disconnect();
        } catch (IOException e) {
            System.err.println("sendPostRequest: " + e.toString());
        }

        return ret;
    }

    static String streamToString(InputStream is) {
        try {
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String inputLine;
            StringBuilder str = new StringBuilder();
            while ((inputLine = buf.readLine()) != null) {
                str.append(inputLine).append("\n");
            }
            buf.close();
            return str.toString();
        } catch (IOException e) {
            System.err.println("streamToString: " + e.toString());
        }
        return null;
    }
}
