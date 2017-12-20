package shit.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import shit.web.loader.JSONConfigWebControllerLoader;
import shit.web.loader.WebControllerLoader;

/**
 * Application Lifecycle Listener implementation class WebInitListener
 *
 */
public class ShitWebInitListener implements ServletContextListener {
	/**
	 * web.xml中配置请求逻辑控制器的加载方式的param-name
	 */
	public static final String controllerLoaderConfigStr = "shitWebControllerLoaderClass";
	
	/**
	 * controller加载器
	 */
	public static WebControllerLoader webControllerLoader;

	/**
	 * Default constructor.
	 */
	public ShitWebInitListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		ServletContext servletContext = arg0.getServletContext();
		try {
			Class<?> webControllerLoaderClass = Class
					.forName(servletContext.getInitParameter(controllerLoaderConfigStr));
			webControllerLoader = (WebControllerLoader) webControllerLoaderClass.newInstance();
		} catch (ClassNotFoundException|InstantiationException|IllegalAccessException e) {
			e.printStackTrace();
			webControllerLoader = new JSONConfigWebControllerLoader();
		} 
	}

}
