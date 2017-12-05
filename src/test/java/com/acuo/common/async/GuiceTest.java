package com.acuo.common.async;

import com.google.inject.servlet.GuiceFilter;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Fail.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GuiceTest {

    private static final Logger logger = LoggerFactory.getLogger(GuiceTest.class);
    public String urlTarget;
    protected GuiceFilter guiceFilter;
    public int port;
    public Server server;

    protected int findFreePort() throws IOException {
        ServerSocket socket = null;

        try {
            socket = new ServerSocket(0);

            return socket.getLocalPort();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    @Before
    public void setUpGlobal() throws Exception {
        port = findFreePort();
        urlTarget = "http://127.0.0.1:" + port + "/invoke";
        guiceFilter = new GuiceFilter();

        configureCometSupport();
        startServer();
    }

    @After
    public void unsetAtmosphereHandler() throws Exception {
        if (guiceFilter != null) guiceFilter.destroy();
        stopServer();
    }

    @Test(timeout = 20000)
    public void testSuspendTimeout() {
        logger.info("running test: testSuspendTimeout");
        AsyncHttpClient c = new AsyncHttpClient();
        try {
            Response r = c.prepareGet(urlTarget).execute().get(10, TimeUnit.SECONDS);
            assertNotNull(r);
            assertEquals(r.getStatusCode(), 200);
            String resume = r.getResponseBody();
            assertEquals(resume, "resume");
        } catch (Exception e) {
            logger.error("test failed", e);
            fail(e.getMessage());
        }
        c.close();

    }

    public void configureCometSupport() {
    }

    public void startServer() throws Exception {
        server = new Server(port);
        ServletContextHandler sch = new ServletContextHandler(server, "/");
        sch.addEventListener(new GuiceConfig());
        sch.addFilter(GuiceFilter.class, "/*", null);
        sch.addServlet(DefaultServlet.class, "/");
        server.start();
    }

    public void stopServer() throws Exception {
        server.stop();
    }

}