package eu.cloudtm.RESTServer;

import eu.cloudtm.StatsManager;
import eu.cloudtm.controller.Controller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.Binder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

/**
 * Created by: Fabio Perfetti
 * E-mail: perfabio87@gmail.com
 * Date: 7/2/13
 */

public class RESTServer {

    private static Log log = LogFactory.getLog(RESTServer.class);

    public static final String BASE_URI = "http://localhost:9998/am/";

    private HttpServer server;

    final ResourceConfig rc = new ResourceConfig();

    private Binder binder;

    public static RESTServer getInstance(Controller _controller, StatsManager _statsManager){

        Binder binder new RESTBinder();


    }

    private RESTServer(RESTBinder binder){

    }


    public void startServer() {

        rc.register(binder);
        rc.packages(true, "eu.cloudtm.RESTServer.resources");


        // uncomment the following line if you want to enable
        // support for JSON on the service (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml)
        // --
        // rc.addBinder(org.glassfish.jersey.media.json.JsonJaxbBinder);

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        this.server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

        log.info(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl", BASE_URI));
    }

    public void stopServer() {

        server.stop();
    }
}

