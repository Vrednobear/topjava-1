package ru.javawebinar.topjava.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class MainWebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext sc) throws ServletException {

        sc.setInitParameter("spring.profiles.active", "data,Tomcat");

        AnnotationConfigWebApplicationContext root =
                new AnnotationConfigWebApplicationContext();

        root.scan("ru.javawebinar.topjava");
        sc.addListener(new ContextLoaderListener(root));

        ServletRegistration.Dynamic appServlet =
                sc.addServlet("mvc-dispatcher", new DispatcherServlet(new GenericWebApplicationContext()));
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("/");
    }
}
