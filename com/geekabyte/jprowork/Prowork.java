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
 * Prowork main class that controls and manages representing Prowork API. 
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
     * This logs in a user to Prowork Doc. 
     * {@link http://dev.prowork.me/accounts-login}
     *
     * @param email {String} email of the user
     * @param password {String} password of the user
     * @return A Prowork user represented as member object
     * @exception InvalidLoginDetails
     * @exception MissingParameterException
     * @see Member
     */
    public Member login(String email, String password)
            throws JproworkRuntimeException {

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
            throw new RemoteAPIHandlerException("Error calling API Endpoint", e);
        }

        return proworkMember;
    }

    /**
     * Registers a new Prowork User Doc
     * {@link http://dev.prowork.me/accounts-register}
     *
     * @param email {String} email of the user
     * @param password {String} password of the user
     * @return A Prowork user represented as member object
     * @exception MissingParameterException
     * @exception InvalidRegisteringDetails
     * @see Member
     */
    public Member register(String email, String password)
            throws JproworkRuntimeException {

        if (email == null || password == null) {
            throw new MissingParameterException();
        }

        String params;
        String resultJsonString;
        Gson gson;
        params = "email=" + email;
        params += "&";
        params += "password=" + password;
        params += "&";
        params += "api_key=" + this.APIKey;
        try {
            resultJsonString = RemoteAPIHandler.postToAPIEndPoint(APIendpoint.registerEndpoint, params);
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
            throw new RemoteAPIHandlerException("Error calling API Endpoint", e);
        }

    }

    /**
     * Subscribe to activity stream of all projects the authenticated user is a
     * member of via push. Prowork posts the notification messages for the user
     * to the subscribed URL {@link http://dev.prowork.me/push-subscribe}.
     *
     * @param member {Member} Logged in user as Member object
     * @param url {String} URL notification messages will be sent to (via POST).
     * @param verifier {String} known to you for verifying message is from
     * Prowork. Prowork sends this with messages. On [url] page, if stored
     * verifier string is not the same as received via post, post message is not
     * from Prowork, ignore.
     * @return Boolean. True on success, false otherwise.
     */
    public Boolean subscribeToStream(Member member, String url, String verifier)
            throws JproworkRuntimeException {
        String api_key = this.APIKey;
        String token = member.getToken();

        if (token == null) {
            throw new InvalidTokenException("Invalid token or none given");
        }

        String responseJson = null;
        String params = null;
        params = "api_key=" + api_key;
        params += "&";
        params += "token=" + token;
        params += "&";
        params += "url=" + url;
        params += "&";
        params += "verifier=" + verifier;
        try {
            responseJson = RemoteAPIHandler.postToAPIEndPoint(APIendpoint.subscribe, params);
        } catch (Exception e) {
            throw new RemoteAPIHandlerException("Error calling API Endpoint", e);
        }

        Gson gson = new Gson();
        Map<String, String> resultMap = gson.fromJson(responseJson,
                new TypeToken<Map<String, String>>() {
        }.getType());

        if (resultMap == null) {
            // TODO standardize all the exception api calls can throw
            //throw new InvalidResponseJsonString();
            return null;
        } else if (resultMap.get("error") != null) {
            throw new APIerrorMessage(resultMap.get("error"));
        } else if (resultMap.get("status").equals("done")) {

            return true;
        } else {

            return false;
        }

    }

    /**
     * Un-subscribe from push activity stream.
     * {@link http://dev.prowork.me/push-unsubscribe}.
     *
     * @return Boolean. True on success, false otherwise.
     */
    public Boolean unsubscribeToStream(Member member) throws JproworkRuntimeException {
        String api_key = this.APIKey;
        String token = member.getToken();

        if (token == null) {
            throw new InvalidTokenException("Invalid token or none given");
        }

        String responseJson = null;
        String params = null;
        params = "api_key=" + api_key;
        params += "&";
        params += "token=" + token;

        try {
            responseJson = RemoteAPIHandler.postToAPIEndPoint(APIendpoint.unsubscribe, params);
        } catch (Exception e) {
            throw new RemoteAPIHandlerException("Error calling API Endpoint", e);
        }

        Gson gson = new Gson();
        Map<String, String> resultMap = gson.fromJson(responseJson,
                new TypeToken<Map<String, String>>() {
        }.getType());

        if (resultMap == null) {
            // TODO standardize all the exception api calls can throw
            //throw new InvalidResponseJsonString();
            return null;
        } else if (resultMap.get("error") != null) {
            throw new APIerrorMessage(resultMap.get("error"));
        } else if (resultMap.get("status").equals("done")) {

            return true;
        } else {

            return false;
        }

    }
}
