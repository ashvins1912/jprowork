/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geekabyte.jprowork;

import com.geekabyte.jprowork.exceptions.InvalidLoginDetails;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
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
    
    public static String getJsonFromAPIEndPoint(String APIendpoint, String params) {
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

        } catch (Throwable e) {
        }
        
        return response;


    }

    public static String postToAPIEndPoint(String APIendpoint, String toPost) throws Exception {
        URL apiURL;
        HttpURLConnection apiConnection;
        String line = null;
        String response = null;
        try {
            apiURL = new URL(APIendpoint);
            apiConnection = (HttpURLConnection) apiURL.openConnection();
            apiConnection.setRequestMethod("POST");
            apiConnection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(apiConnection.getOutputStream());
            wr.write(toPost);
            wr.flush();
            BufferedReader rd = new BufferedReader(new InputStreamReader(apiConnection.getInputStream()));

            while ((line = rd.readLine()) != null) {
                response = line;
            }
            wr.close();
            rd.close();


        } catch(IOException e) {
            throw e;
        }
        
        return response;


    }
    
}
