package com.rest.restservice.init;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class ApplicationInitializer implements WebApplicationInitializer {

	
	  @Override public void onStartup(ServletContext servletCxt) {
	  
	  // Load Spring web application configuration
	  AnnotationConfigWebApplicationContext ac = new
	  AnnotationConfigWebApplicationContext(); ac.register(AppConfig.class);
	  ac.refresh();
	  
	 	  DispatcherServlet servlet = new
	  DispatcherServlet(ac); 
	  ServletRegistration.Dynamic registration =
	  servletCxt.addServlet("app", servlet); registration.setLoadOnStartup(1);
	  registration.addMapping("/app/*"); }
	 
	
}