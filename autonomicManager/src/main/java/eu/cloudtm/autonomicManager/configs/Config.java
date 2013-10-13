package eu.cloudtm.autonomicManager.configs;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created with IntelliJ IDEA. User: fabio Date: 7/23/13 Time: 10:22 AM To change this template use File | Settings |
 * File Templates.
 */
public class
        Config {

   private static final Log log = LogFactory.getLog(Config.class);
   private static AdaptationManagerConfig instance;

   public static AdaptationManagerConfig getInstance() {
      if (instance == null) {
         try {
            instance = new AdaptationManagerConfig("config/config.properties");
         } catch (ConfigurationException e) {
            log.error(e);
            throw new RuntimeException(e);
         }
      }
      return instance;
   }


}
