package util;

import org.apache.log4j.Logger;

public class OperatingSystemUtil {
    private static final Logger logger = Logger.getLogger(OperatingSystemUtil.class);

    public static String getOS() {
        final String operatingSystemName = System.getProperty("os.name").toLowerCase();

        logger.info("Operating System: " + operatingSystemName);

        String operatingSystem = "";

        if (operatingSystemName.contains("win")) {
            operatingSystem = "Windows";
        }

        if (operatingSystemName.contains("linux")) {
            operatingSystem = "Linux";
        }

        return operatingSystem;
    }
}