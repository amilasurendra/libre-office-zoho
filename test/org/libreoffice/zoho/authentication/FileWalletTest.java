/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.libreoffice.zoho.authentication;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author amila
 */
public class FileWalletTest {

    private static FileWallet wallet;

    public FileWalletTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        wallet = FileWallet.getInstance();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testWriteCredentials() {
        wallet.setUserName("amilasurendra");
        wallet.setPassword("AMILAcrack!(**");
        wallet.setTicket(Wallet.ticketType.Writer, null);
    }

    @Test
    public void testReadUsername() {
        assertTrue("amilasurendra".equals(wallet.getUserName()));
        System.out.println(wallet.getUserName());
    }

     @Test
    public void testReadPassword() {
        assertTrue("AMILAcrack!(**".equals(wallet.getPassword()));
        System.out.println(wallet.getPassword());
    }

 }