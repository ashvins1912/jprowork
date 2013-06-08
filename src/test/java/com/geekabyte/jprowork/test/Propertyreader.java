package com.geekabyte.jprowork.test;

import java.io.FileInputStream;
import java.util.Properties;
/**
 *
 * @author dade
 */
public class Propertyreader {
    
    Properties prop;
    private String APIKey;
    private String email;
    private String password;
    
    
    public Propertyreader() {
        prop = new Properties();
        
        try {
            prop.load(Propertyreader.class.getClassLoader().getResourceAsStream("tests.properties"));
        } catch (Exception e) {
            System.out.println(e);
        }
        
        this.APIKey = prop.getProperty("apikey");
        this.email = prop.getProperty("email");
        this.password = prop.getProperty("password");
    }

    /**
     * @return the APIKey
     */
    public String getAPIKey() {
        return APIKey;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

}
