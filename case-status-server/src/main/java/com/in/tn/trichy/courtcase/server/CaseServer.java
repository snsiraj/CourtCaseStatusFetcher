package com.in.tn.trichy.courtcase.server;

import java.net.URI;
import java.util.logging.LogManager;

import com.in.tn.trichy.courtcase.server.resource.CaseInformationResource;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class CaseServer {
    static {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();
    }
    private static final Logger LOG= LoggerFactory.getLogger(CaseServer.class);
    private static final String BASE_URI="http://localhost:7070/";

    public static void main(String[] args) {
        LOG.info("Starting HTTP Server...");
        ResourceConfig config=new ResourceConfig().register(new CaseInformationResource());
        GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI),config);
    }
}
