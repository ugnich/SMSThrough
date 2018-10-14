package com.ugnich.smsthrough;

import java.util.ArrayList;

/**
 * Send SMS using various services
 *
 * https://github.com/ugnich/SMSThrough
 *
 * MIT License
 *
 * @version 1.0
 */
public class SMSThrough {

    ArrayList<SMSGateway> gateways = new ArrayList<>();

    public SMSThrough addGateway(SMSGateway gateway) {
        gateways.add(gateway);
        return this;
    }

    public String send(String to, String message) {
        for (SMSGateway gateway : gateways) {
            if (gateway.send(to, message)) {
                return gateway.getGatewayName();
            }
        }
        return null;
    }
}
