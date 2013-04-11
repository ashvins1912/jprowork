/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geekabyte.jprowork;
import com.geekabyte.jprowork.exceptions.InvalidLoginDetails;
import com.geekabyte.jprowork.exceptions.MissingParameterException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author dade
 */
public class Main {
    
    
    public static void main(String[] args) {

        
        Prowork pwk = new Prowork("90986a94945621749e4e29e1d8feb624");
        try {
            Member user = pwk.login("dadepo@gmail.com", "kalakutashow");
            user.getProjectActivity(208,1);
        } catch (InvalidLoginDetails | MissingParameterException e) {
           System.out.print(e);
        }

    }
    
}
