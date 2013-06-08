
package com.geekabyte.jprowork.test;

import com.geekabyte.jprowork.Member;
import com.geekabyte.jprowork.Prowork;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 *
 * @author dade
 */
public class ProworkTest {
    
    Propertyreader prop;
    public ProworkTest() {
        prop = new Propertyreader();
    }

    /**
     *
     * @param email
     * @param password
     * @return
     */
    
    /**
     * Test of login Method).
     */
    @Test
    public void loginTest() {
        String APIKEY = prop.getAPIKey();
        Prowork pwk = new Prowork(APIKEY);
        System.out.println(prop.getEmail());
        Member member = pwk.login(prop.getEmail(), prop.getPassword());
        assertThat(member, instanceOf(Member.class));
    }
}
