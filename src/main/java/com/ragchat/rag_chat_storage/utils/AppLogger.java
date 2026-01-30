package com.ragchat.rag_chat_storage.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLogger {

    // private constructor prevents instantiation
    private AppLogger() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static void info(Logger log, String message, Object... args) {
        log.info(message, args);
    }

    public static void debug(Logger log, String message, Object... args) {
        log.debug(message, args);
    }

    public static void warn(Logger log, String message, Object... args) {
        log.warn(message, args);
    }

    public static void error(Logger log, String message, Object... args) {
        log.error(message, args);
    }
}


