package org.collectd.mx.java.org.collectd;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.logging.Logger;

public class MBeanServerConnectionFactory {

    private static final Logger _log = Logger.getLogger(MBeanServerConnectionFactory.class.getName());

    private MBeanServerConnection server;
    private final String remoteUrl;
    private boolean isRemote;

    public MBeanServerConnectionFactory(String remoteUrl) {
        this.server = null;
        this.remoteUrl = remoteUrl;
        this.isRemote = false;

        if (this.remoteUrl != null) {
            _log.info("Remote JMX will be used: " + remoteUrl);
        }
    }

    public MBeanServerConnectionFactory(MBeanServerConnection server) {
        this.server = server;
        this.remoteUrl = null;
        this.isRemote = false;

        if (this.server != null) {
            _log.info("An external instance will be used.");
        }
    }

    public MBeanServerConnectionFactory() {
        this.server = null;
        this.remoteUrl = null;
        this.isRemote = false;
    }

    public MBeanServerConnection getInstance() {
        if (!isRemote && remoteUrl != null){
            try {
                // service:jmx:http-remoting-jmx://127.0.0.1:9990
                final JMXServiceURL serviceURL = new JMXServiceURL(remoteUrl);
                final JMXConnector jmxConnector = JMXConnectorFactory.connect(serviceURL, null);

                server = jmxConnector.getMBeanServerConnection();
                isRemote = true;
            } catch (IOException e) {
                _log.warning("Unable to connect to Remote JMS. The local one will be used.");

                server =  ManagementFactory.getPlatformMBeanServer();
                isRemote = false;
            }
        } else if (server == null) {
            server =  ManagementFactory.getPlatformMBeanServer();
        }

        return server;
    }
}
