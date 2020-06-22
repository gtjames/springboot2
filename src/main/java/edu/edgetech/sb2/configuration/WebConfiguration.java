package edu.edgetech.sb2.configuration;

//import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {

	/*
	 *	If you are not using Spring Security with the H2 database console,
	 *		this is all you need to do.
	 *		When you run your Spring Boot application,
	 *			you’ll now be able to access the H2 database console at
	 *			http://localhost:8080/console
	 */
	/*
	this might be usfule but for now I have it turned off
	 */
//    @Bean
//    ServletRegistrationBean h2servletRegistration(){
//        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
//        registrationBean.addUrlMappings("/console/*");
//        return registrationBean;
//    }
}
