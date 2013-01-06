package org.libreoffice.zoho;

import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.lib.uno.helper.Factory;
import com.sun.star.lang.XSingleComponentFactory;
import com.sun.star.registry.XRegistryKey;
import com.sun.star.lib.uno.helper.WeakBase;
import org.libreoffice.zoho.handler.DocumentHandler;

/**
 * Main Interface that communicate with Libreoffice tool-bar controls
 * Handles command dispatch events from Libreoffice
 */

public final class Zoho extends WeakBase
   implements com.sun.star.frame.XDispatchProvider,
              com.sun.star.lang.XServiceInfo,
              com.sun.star.lang.XInitialization,
              com.sun.star.frame.XDispatch
{
    private final XComponentContext m_xContext;
    private com.sun.star.frame.XFrame m_xFrame;
    private static final String m_implementationName = Zoho.class.getName();
    private static final String[] m_serviceNames = { "com.sun.star.frame.ProtocolHandler" };


    public Zoho( XComponentContext context )
    {
        m_xContext = context;
    };

    public static XSingleComponentFactory __getComponentFactory( String sImplementationName ) {
        XSingleComponentFactory xFactory = null;

        if ( sImplementationName.equals( m_implementationName ) )
            xFactory = Factory.createComponentFactory(Zoho.class, m_serviceNames);
        return xFactory;
    }

    public static boolean __writeRegistryServiceInfo( XRegistryKey xRegistryKey ) {
        return Factory.writeRegistryServiceInfo(m_implementationName,
                                                m_serviceNames,
                                                xRegistryKey);
    }

    // com.sun.star.frame.XDispatchProvider:
    public com.sun.star.frame.XDispatch queryDispatch( com.sun.star.util.URL aURL,
                                                       String sTargetFrameName,
                                                       int iSearchFlags )
    {
        if ( aURL.Protocol.compareTo("org.libreoffice.zoho.zoho:") == 0 )
        {
            if ( aURL.Path.compareTo("Import") == 0 )
                return this;
            if ( aURL.Path.compareTo("Export") == 0 )
                return this;
            if ( aURL.Path.compareTo("Share") == 0 )
                return this;
            if ( aURL.Path.compareTo("Options") == 0 )
                return this;
            if ( aURL.Path.compareTo("Local") == 0 )
                return this;
        }
        return null;
    }

    // com.sun.star.frame.XDispatchProvider:
    public com.sun.star.frame.XDispatch[] queryDispatches(
         com.sun.star.frame.DispatchDescriptor[] seqDescriptors )
    {
        int nCount = seqDescriptors.length;
        com.sun.star.frame.XDispatch[] seqDispatcher =
            new com.sun.star.frame.XDispatch[seqDescriptors.length];

        for( int i=0; i < nCount; ++i )
        {
            seqDispatcher[i] = queryDispatch(seqDescriptors[i].FeatureURL,
                                             seqDescriptors[i].FrameName,
                                             seqDescriptors[i].SearchFlags );
        }
        return seqDispatcher;
    }

    // com.sun.star.lang.XServiceInfo:
    public String getImplementationName() {
         return m_implementationName;
    }

    public boolean supportsService( String sService ) {
        int len = m_serviceNames.length;

        for( int i=0; i < len; i++) {
            if (sService.equals(m_serviceNames[i]))
                return true;
        }
        return false;
    }

    public String[] getSupportedServiceNames() {
        return m_serviceNames;
    }

    // com.sun.star.lang.XInitialization:
    public void initialize( Object[] object )
        throws com.sun.star.uno.Exception
    {
        if ( object.length > 0 )
        {
            m_xFrame = (com.sun.star.frame.XFrame)UnoRuntime.queryInterface(
                com.sun.star.frame.XFrame.class, object[0]);
        }
    }

    // com.sun.star.frame.XDispatch:
     public void dispatch( com.sun.star.util.URL aURL,
                           com.sun.star.beans.PropertyValue[] aArguments )
    {
         DocumentHandler handler = new DocumentHandler((m_xFrame));
         
         if ( aURL.Protocol.compareTo("org.libreoffice.zoho.zoho:") == 0 )
        {
            if ( aURL.Path.compareTo("Import") == 0 )
            {
                // add your own code here
                handler.downloadDocument();
                return;
            }
            if ( aURL.Path.compareTo("Export") == 0 )
            {
                // add your own code here
                handler.uploadDocument();
                return;
            }
            if ( aURL.Path.compareTo("Share") == 0 )
            {
                // add your own code here
                return;
            }
            if ( aURL.Path.compareTo("Options") == 0 )
            {
                // add your own code here
                handler.changeConfig();
                return;
            }if ( aURL.Path.compareTo("Local") == 0 )
            {
                // add your own code here
                handler.openLocalDocument();
                return;
            }
        }
    }

    public void addStatusListener( com.sun.star.frame.XStatusListener xControl,
                                    com.sun.star.util.URL aURL )
    {
        // add your own code here
    }

    public void removeStatusListener( com.sun.star.frame.XStatusListener xControl,
                                       com.sun.star.util.URL aURL )
    {
        // add your own code here
    }

}
