package com.acuo.common.app.swagger;

import io.swagger.config.ScannerFactory;
import io.swagger.jaxrs.config.BeanConfig;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

 public class SwaggerServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {

        BeanConfig beanConfig = getBeanConfig();
        event.getServletContext().setAttribute("reader", beanConfig);
        event.getServletContext().setAttribute("swagger", beanConfig.getSwagger());
        event.getServletContext().setAttribute("scanner", ScannerFactory.getScanner());
    }

    private BeanConfig getBeanConfig() {

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[] { "http" });
        beanConfig.setHost("localhost:9090");
        beanConfig.setBasePath("/acuo/api");

        beanConfig.setTitle("RESTEasy, Embedded Jetty, Swagger and Guice");
        beanConfig.setDescription("Money transfer application.");


        beanConfig.setResourcePackage("com.acuo.valuation.web.resource");
        beanConfig.setScan(true);

        return beanConfig;
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
    }
}