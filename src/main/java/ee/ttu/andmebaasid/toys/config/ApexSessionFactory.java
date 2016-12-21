package ee.ttu.andmebaasid.toys.config;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ee.ttu.andmebaasid.toys.config.Constants.*;

public class ApexSessionFactory {

    private static Logger logger = LoggerFactory.getLogger(ApexSessionFactory.class);

    private Session session;

    public ApexSessionFactory () throws Throwable {
        try {
            JSch jsch = new JSch();

            // Get SSH session
            session = jsch.getSession(APEX_REMOTE_USER, APEX_REMOTE_HOST, APEX_REMOTE_PORT);
            session.setPassword(APEX_REMOTE_PASS);

            // Never automatically add new host keys to the host file
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            // Connect to remote server
            session.connect();

            // Apply the port forwarding
            session.setPortForwardingL(LOCAL_FORWARD_PORT, LOCALHOST, APEX_FORWARD_PORT);
            logger.info("Connection to apex.ttu.ee established!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Session getSession() {
        return session;
    }
}
