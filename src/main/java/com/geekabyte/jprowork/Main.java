/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geekabyte.jprowork;


/**
 *
 * @author dade
 */
public class Main {
    
    
    public static void main(String[] args) {

        String APIKEY = "Your API KEY GOES HERE";
        Prowork pwk = new Prowork(APIKEY);
        Member member = pwk.login("dadepo@gmail.com", "kalakutashow");
        System.out.println(member);
        
        

        
        
         

            
       

    }
    
}
