/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.libreoffice.zoho.ui;

import javax.swing.JFrame;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author amila
 */
public class ProgressTest {

    public ProgressTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public static void main(String args[]){
        Progress x = new Progress(new JFrame(), true, "test");
        x.setVisible(true);
        x.update(null, 10);
    }



}