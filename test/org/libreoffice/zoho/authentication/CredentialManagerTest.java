/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.libreoffice.zoho.authentication;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.libreoffice.zoho.handler.WriterDocument;
import static org.junit.Assert.*;

/**
 *
 * @author amila
 */
public class CredentialManagerTest {

    public static CredentialManager credentials;
    public static String ticket;
    
    public CredentialManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        credentials = CredentialManager.getInstance();
        
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    //@Test
    public void testInitialTicketGeneration(){
        WalletFactory.getWallet().setTicket(Wallet.ticketType.Writer, null);
        ticket = credentials.getTicket(new WriterDocument());
        System.out.println(ticket);
        assertTrue(ticket!= null);
    }

    //@Test
    public void testSecondTicketGeneration() {
        String newTicket = credentials.getTicket(new WriterDocument());
        System.out.println(newTicket);
        assertTrue(ticket.equals(newTicket));
    }

    @Test
    public void testIsUsernameSaved(){
        credentials.setUserName("amilasurendra", true);
        assertTrue(credentials.isUsernameSaved());
    }

    @Test
    public void testIsUsernameSavedTemporary(){
        credentials.setUserName("amilasurendra", false);
        assertTrue(!credentials.isUsernameSaved());
    }
}