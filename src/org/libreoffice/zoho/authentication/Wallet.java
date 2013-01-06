package org.libreoffice.zoho.authentication;


/**
 * @author amila
 *
 * Interface for any Wallet
 * Secure storage can be achieved in any supported OS by implementing this.
 */
public interface Wallet {

    public enum ticketType{
        Writer, Presentation, SpreadSheet
    };

    public String getPassword();
    public String getUserName();
    public String getTicket(ticketType type);
    public void setPassword(String password);
    public void setUserName(String username);
    public void setTicket(ticketType type, String ticket);
}
