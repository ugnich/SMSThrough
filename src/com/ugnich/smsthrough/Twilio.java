package com.ugnich.smsthrough;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Twilio implements SMSGateway {

    String accountId;
    String authToken;
    String from;

    public Twilio(String accountId, String authToken, String from) {
        this.accountId = accountId;
        this.authToken = authToken;
        this.from = from;
    }

    @Override
    public String getGatewayName() {
        return "Twilio";
    }

    @Override
    public boolean send(String to, String message) {
        if (!to.startsWith("+")) {
            to = "+" + to;
        }

        try {
            String post = "From=" + URLEncoder.encode(from, "utf-8")
                    + "&To=" + URLEncoder.encode(to, "utf-8")
                    + "&Body=" + URLEncoder.encode(message, "utf-8");

            String res = Utils.sendPostRequest("https://api.twilio.com/2010-04-01/Accounts/" + accountId + "/Messages.json", post, accountId + ":" + authToken);

            return res != null;
        } catch (UnsupportedEncodingException e) {
            System.err.println("SMSThrough.Twilio: " + e.toString());
            return false;
        }
    }
}
