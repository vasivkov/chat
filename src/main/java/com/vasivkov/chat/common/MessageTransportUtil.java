package com.vasivkov.chat.common;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by vasya on 15/07/17.
 */
public class MessageTransportUtil {
    public static final Logger LOGGER = Logger.getLogger(MessageTransportUtil.class.getName());

    public static void sendMessageNoGuarantee(Object o, ObjectOutputStream outputStream) {
        try {
            outputStream.writeObject(o);
            outputStream.flush();
            LOGGER.info("Message delivered " + o);
        } catch (IOException e) {
            LOGGER.error("Failed to send  message of " + o, e);
        }
    }

    public static void sendMessageWithRepeat(Object o, ObjectOutputStream outputStream, int times) {
        if (times < 1) {
            throw new IllegalArgumentException();
        }
        int tries = 0;
        boolean delivered = false;

        while (tries < times && !delivered) {
            try {
                outputStream.writeObject(o);
                outputStream.flush();
                delivered = true;
                LOGGER.info("Message delivered " + o);
            } catch (IOException e) {
                tries++;
                if(tries == times) {
                    LOGGER.error("Failed to send message of " + o, e);
                }
            }
        }
    }
}
