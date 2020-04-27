package ua.nure.diulher.SummaryTask4.web.listener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class ContextListener implements ServletContextListener {

	private static final Logger LOG = Logger.getLogger(ContextListener.class);

	public void contextDestroyed(ServletContextEvent event) {
		log("Servlet context destruction starts");
		// no op
		log("Servlet context destruction finished");
	}

	public void contextInitialized(ServletContextEvent event) {
		log("Servlet context initialization starts");

		ServletContext servletContext = event.getServletContext();
		initLog4J(servletContext);
		initCommandContainer();
		initLocale(event);
		log("Servlet context initialization finished");
	}

	/**
	 * Initializes log4j framework.
	 * 
	 * @param servletContext
	 */
	private void initLog4J(ServletContext servletContext) {
		log("Log4J initialization started");
		try {
			PropertyConfigurator.configure(
				servletContext.getRealPath("WEB-INF/log4j.properties"));
			LOG.debug("Log4j has been initialized");
		} catch (Exception ex) {
			log("Cannot configure Log4j");
			ex.printStackTrace();
		}		
		log("Log4J initialization finished");
	}
	
	/**
	 * Initializes CommandContainer.
	 *
	 */
	private void initCommandContainer() {
		
		// initialize commands container
		// just load class to JVM
		try {
			Class.forName("ua.nure.diulher.SummaryTask4.web.command.CommandContainer");
		} catch (ClassNotFoundException ex) {
			throw new IllegalStateException("Cannot initialize Command Container");
		}
	}

	private void initLocale(ServletContextEvent event){
		ServletContext context = event.getServletContext();
		String localesFileName = context.getInitParameter("locales");
		String localesFileRealPath = context.getRealPath(localesFileName);
		Properties locales = new Properties();
		try {
			locales.load(new FileInputStream(localesFileRealPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		context.setAttribute("locales", locales);
		locales.list(System.out);
	}

	private void log(String msg) {
		System.out.println("[ContextListener] " + msg);
	}
}