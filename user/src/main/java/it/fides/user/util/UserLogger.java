package it.fides.user.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class UserLogger {
    public final Logger log = LogManager.getLogger();
}
