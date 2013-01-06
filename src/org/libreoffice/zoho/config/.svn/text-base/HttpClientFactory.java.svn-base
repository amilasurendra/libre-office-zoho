/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.libreoffice.zoho.config;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * @author amila
 *
 * This class creates configured HttpClient according to current configuration
 *
 */
public class HttpClientFactory {


    public static HttpClient getHttpClient(){
        if(Configuration.getInstance().isProxyEnabled()){
            HttpClient client = new DefaultHttpClient();
            HttpHost proxy = Configuration.getInstance().getProxy();

            client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
            return client;
        }else{
            return  new DefaultHttpClient();
        }
    }

}
