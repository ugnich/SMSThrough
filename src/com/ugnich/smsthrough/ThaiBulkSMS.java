package com.ugnich.smsthrough;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ThaiBulkSMS implements SMSGateway {

    String username;
    String password;
    String from;

    public ThaiBulkSMS(String username, String password, String from) {
        this.username = username;
        this.password = password;
        this.from = from;
    }

    @Override
    public String getGatewayName() {
        return "ThaiBulkSMS";
    }

    @Override
    public boolean send(String to, String message) {
        try {
            String post = "username=" + username
                    + "&password=" + password
                    + "&msisdn=" + to
                    + "&message=" + URLEncoder.encode(message, "utf-8")
                    + "&sender=" + from;

            String res = Utils.sendPostRequest("https://secure.thaibulksms.com/sms_api.php", post);
            return res != null && res.indexOf("<Status>1</Status>") > 0;
        } catch (UnsupportedEncodingException e) {
            System.err.println("SMSThrough.ThaiBulkSMS: " + e.toString());
            return false;
        }
    }
}
