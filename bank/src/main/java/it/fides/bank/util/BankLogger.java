package it.fides.bank.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class BankLogger {
    public final Logger log = LogManager.getLogger();
}
