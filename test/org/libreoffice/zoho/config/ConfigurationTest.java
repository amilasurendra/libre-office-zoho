/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.libreoffice.zoho.config;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author amila
 */
public class ConfigurationTest {

    private static Configuration config;
    
    public ConfigurationTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        config = Configuration.getInstance();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void ProxyWriteTrue() {
        config.setProxyEnabled(true);
        assertTrue(config.isProxyEnabled());
    }

    @Test
    public void ProxyWriteFalse() {
        config.setProxyEnabled(false);
        assertTrue(!config.isProxyEnabled());
    }

    @Test
    public void testWorkingPath(){
        System.out.println(config.getWorkingPath());
    }

}