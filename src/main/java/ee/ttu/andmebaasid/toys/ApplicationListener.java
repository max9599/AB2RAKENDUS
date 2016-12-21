package ee.ttu.andmebaasid.toys;

import com.jcraft.jsch.Session;
import ee.ttu.andmebaasid.toys.config.ApexSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@Component
@WebListener
public class ApplicationListener implements ServletContextListener {

    private static Logger logger = LoggerFactory.getLogger(ApplicationListener.class);

    private Session SSHSession;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        
        //logger.info("Spring context initialized!");

        // Creating ssh tunnel for PostgreSQL connection
        //try {
        //    ApexSessionFactory apexSessionFactory = new ApexSessionFactory();
        //    setSSHSession(apexSessionFactory.getSession());
        //} catch (Throwable throwable) {
        //    throwable.printStackTrace();
        //}
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        // Close ssh session
        //SSHSession.disconnect();
        //logger.info("Spring context destroyed!");
    }

    public void setSSHSession(Session SSHSession) {
        this.SSHSession = SSHSession;
    }
}
