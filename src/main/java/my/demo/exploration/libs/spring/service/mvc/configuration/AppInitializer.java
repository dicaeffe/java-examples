package my.demo.exploration.libs.spring.service.mvc.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/** The AppInitializer class implements WebApplicationInitializer interface. */
public class AppInitializer implements WebApplicationInitializer {

	/** The onStartup method will create a Spring context. */
    @Override
    public void onStartup(ServletContext container) throws ServletException {
    	
    	/* The use of the AnnotationConfigWebApplicationContext class means that you will use only annotation-based configuration (instead of Java-based or XML-based). */
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        /* Specify the packages to scan for components and configuration classes. */
        context.scan("my.demo.exploration.spring.service.mvc.configuration");
        container.addListener(new ContextLoaderListener(context));
        
        /* The DispatcherServlet defines the entry point for the web application. This class can entirely replace the web.xml file from <3.0 Servlet versions. */
        ServletRegistration.Dynamic dispatcher = container.addServlet("mvc", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");   
    }
}