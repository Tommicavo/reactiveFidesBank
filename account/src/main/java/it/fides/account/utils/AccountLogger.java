package it.fides.account.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class AccountLogger {
    public final Logger log = LogManager.getLogger();
}
