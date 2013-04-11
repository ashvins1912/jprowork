/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geekabyte.jprowork;

import com.geekabyte.jprowork.exceptions.*;
import com.geekabyte.jprowork.apiendpoints.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Map;

/**
 * Prowork main class that controls and manages representing Prowork API
 *
 * @author dade
 */
public class Prowork {

    private String APIKey;
    private RemoteAPIHandler RemoteAPIHandler;

    public Prowork(String api_key) {
        APIKey = api_key;
    }

    /**
     * This logs in a user to Prowork Doc
     * {@link http://dev.prowork.me/accounts-login}
     *
     * @param email {String} email of the user
     * @param password {String} password of the user
     * @return A Prowork user represented as member object
     * @exception InvalidLoginDetails
     * @exception MissingParameterException
     */
    public Member login(String email, String password)
            throws InvalidLoginDetails, MissingParameterException {

        String data;
        String resultJsonString;
        Member proworkMember = null;
        Gson gson;
        data = "email=" + email;
        data += "&";
        data += "password=" + password;
        data += "&";
        data += "api_key=" + this.APIKey;
        try {
            resultJsonString = RemoteAPIHandler.postToAPIEndPoint(APIendpoint.loginEndpoint, data);

            // Turn result to Java object
            gson = new Gson();
            Map<String, String> resultMap = gson.fromJson(resultJsonString,
                    new TypeToken<Map<String, String>>() {
            }.getType());

            if (resultMap == null) {
                throw new InvalidResponseJsonString();
            } else if (resultMap.get("error") != null) {
                throw new InvalidLoginDetails(resultMap.get("error"));
            } else {
                // Set API session token
                RemoteAPIHandler.setToken(resultMap.get("token"));
                proworkMember = new Member(resultMap.get("name"), resultMap.get("email"), resultMap.get("user_id"), resultMap.get("token"));
            }

        } catch (Exception e) {
            //TODO What to do with error thrown by postToAPIEndPoint
            System.out.println(e);
        }

        return proworkMember;

        // JSON FORMAT
        //{
        // "token":"xyz123",
        // "user_id":"1",
        // "name":"Francis Onwumere",
        // "email":"fran@prowork.me"
        //
        //}

    }

    /**
     * Registers a new Prowork User Doc
     * {@link http://dev.prowork.me/accounts-register}
     *
     * @return A Prowork user represented as member object
     * @exception MissingParameterException
     * @exception InvalidRegisteringDetails
     */
    public Member register(String email, String password)
            throws MissingParameterException {

        if (email == null || password == null) {
            throw new MissingParameterException();
        }

        String data;
        String resultJsonString;
        Member proworkMember = null;
        Gson gson;
        data = "email=" + email;
        data += "&";
        data += "password=" + password;
        data += "&";
        data += "api_key=" + this.APIKey;
        try {
            resultJsonString = RemoteAPIHandler.postToAPIEndPoint(APIendpoint.registerEndpoint, data);
            //{
            // "token":"xyz123",
            // "user_id":"1"
            //}

            // Turn result to Java object
            gson = new Gson();
            Map<String, String> resultMap = gson.fromJson(resultJsonString,
                    new TypeToken<Map<String, String>>() {
            }.getType());

            if (resultMap == null) {
                throw new InvalidResponseJsonString();
            } else if (resultMap.get("error") != null) {
                throw new InvalidLoginDetails(resultMap.get("error"));
            } else {
                // Set API session token
                RemoteAPIHandler.setToken(resultMap.get("token"));
                // Login and return Member object
                return login(email, password);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return proworkMember;
    }
}
