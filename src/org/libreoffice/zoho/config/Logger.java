/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.libreoffice.zoho.config;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

/**
 * @author amila
 *
 * This class manages a log file for debugging purposes.
 */
public class Logger {

    static FileHandler hand;
    static java.util.logging.Logger log;

    static{
        try {
            
            hand = new FileHandler("/home/amila/zoho.log");
            hand.setFormatter(new SimpleFormatter());
            log = java.util.logging.Logger.getLogger("log_file");
            log.addHandler(hand);
        } catch (Exception ex) {
        }
    }
     
 

    public static void log(String message){
        log.log(Level.INFO, message);
    }
    
}
