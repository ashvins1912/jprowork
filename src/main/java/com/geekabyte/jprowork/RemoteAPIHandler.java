package com.geekabyte.jprowork;

import com.geekabyte.jprowork.exceptions.RemoteAPIHandlerException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class that contains static methods for calling API endpoints
 *
 * @author dade
 */
public class RemoteAPIHandler {

    private static String token;

    protected static void setToken(String sessiontoken) {
        token = sessiontoken;
    }

    public static String getToken() {
        return token;
    }

    /**
     * Static method that is used to do GET operations to API end points
     *
     * @param APIendpoint URL of API end point
     * @param params parameters
     * @throws Exception
     * @return The JSON string from API
     */
    public static String getJsonFromAPIEndPoint(String APIendpoint, String params) throws Exception {
        URL apiURL;
        HttpURLConnection apiConnection;
        String line = null;
        String response = null;
        try {
            apiURL = new URL(APIendpoint + "/?" + params);
            apiConnection = (HttpURLConnection) apiURL.openConnection();
            apiConnection.setRequestMethod("GET");
            InputStream is = apiConnection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            while ((line = rd.readLine()) != null) {
                response = line;
            }

        } catch (Exception e) {
            throw e;
        }

        if (response == null) {
            throw new RemoteAPIHandlerException("API Call returned null");
        }

        return response;


    }

    /**
     * Static method that is used to do POST operations to API end points
     *
     * @param APIendpoint URL of API end point
     * @param params parameters
     * @throws Exception
     * @return The JSON string from API
     */
    public static String postToAPIEndPoint(String APIendpoint, String toPost) throws Exception {
        URL apiURL;
        HttpURLConnection apiConnection;
        String line = null;
        String response = null;
        OutputStreamWriter wr;
        BufferedReader rd;
        try {
            apiURL = new URL(APIendpoint);
            apiConnection = (HttpURLConnection) apiURL.openConnection();
            apiConnection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            apiConnection.setRequestMethod("POST");
            apiConnection.setDoOutput(true);
            wr = new OutputStreamWriter(apiConnection.getOutputStream());
            wr.write(toPost);
            wr.flush();
            rd = new BufferedReader(new InputStreamReader(apiConnection.getInputStream()));

            while ((line = rd.readLine()) != null) {
                response = line;
            }
            wr.close();
            rd.close();


        } catch (IOException e) {
            throw e;
        }

        return response;


    }
}
