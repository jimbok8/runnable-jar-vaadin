package org.vaadin.lightvaadin;

import com.vaadin.server.VaadinServlet;
import java.util.EventListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/** Minimal Servlet bootstrap for Vaadin application.
 *
 * @author Sami Ekblad
 */
public class App {

    public static void main(String[] args) {
        Server server = new Server(8080);

        ServletContextHandler contextHandler
                = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");

        ServletHolder sh = new ServletHolder(new VaadinServlet());
        contextHandler.addServlet(sh, "/*");
        contextHandler.setInitParameter("ui", HelloWorldUI.class.getCanonicalName());

        // Register cdn.virit.in if present
        try {
            Class cls = Class.forName("in.virit.WidgetSet");
            if (cls != null) {
                contextHandler.getSessionHandler().addEventListener((EventListener) cls.newInstance());
            }
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

        server.setHandler(contextHandler);

        try {
            server.start();
            server.join();

        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
