package org.collectd.mx.java.org.collectd;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.management.MBeanServerConnection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class MBeanServerConnectionFactoryTest {

    private MBeanServerConnectionFactory factory;
    @Mock
    private MBeanServerConnection server;

    @Test
    public void shouldReportDefaultMBeanServerConnection() {
        factory = new MBeanServerConnectionFactory();

        assertNotNull(factory.getInstance());
    }

    @Test
    public void shouldReturnPredefinedInstance() {
        factory = new MBeanServerConnectionFactory(server);

        assertEquals(server, factory.getInstance());
    }

}