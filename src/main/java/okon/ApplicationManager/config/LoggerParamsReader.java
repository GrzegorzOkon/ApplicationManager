package okon.ApplicationManager.config;

import okon.ApplicationManager.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class LoggerParamsReader {
    private static final Logger logger = LogManager.getLogger(LoggerParamsReader.class);

    public static Properties readProperties(File file) {
        Properties result = new Properties();
        try {
            result.load(new FileInputStream(file));
            if (result.getProperty("logger.enable").equals("true")) {
                logger.debug("ReadProperties(" + file.getName() + ") : OK");
            }
        } catch (Exception e) {
            if (result.getProperty("logger.enable").equals("true")) {
                logger.error("ReadProperties(" + file.getName() + ") : " + e.getMessage());
            }
            throw new AppException(e);
        }
        return result;
    }
}
