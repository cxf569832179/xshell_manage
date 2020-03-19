package me.ffs.www.conf;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.valves.RemoteIpValve;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: 456
 * @date: 2018/3/6
 */

@Configuration
public class TomcatAjpConfig {

    @Value("${ajp.port}")
    private int ajpport;


    @Bean
    @SuppressWarnings("static-method")
    public TomcatServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createConnector(ajpport));
        tomcat.addContextValves(createRemoteIpValves());
        return tomcat;
    }

    private static RemoteIpValve createRemoteIpValves() {
        RemoteIpValve remoteIpValve = new RemoteIpValve();
        remoteIpValve.setRemoteIpHeader("x-forwarded-for");
        remoteIpValve.setProtocolHeader("x-forwarded-proto");
        return remoteIpValve;
    }

    private static Connector createConnector(int ajpport) {
        Connector connector = new Connector("AJP/1.3");
        connector.setPort(ajpport);
        return connector;
    }

    public void setAjpport(int ajpport) {
        this.ajpport = ajpport;
    }




}