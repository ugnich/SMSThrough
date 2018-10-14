package com.ugnich.smsthrough;

public class BulkSMS implements SMSGateway {

    String username;
    String password;
    String from;

    public BulkSMS(String username, String password, String from) {
        this.username = username;
        this.password = password;
        this.from = from;
    }

    @Override
    public String getGatewayName() {
        return "BulkSMS";
    }

    @Override
    public boolean send(String to, String message) {
        String post = "username=" + username
                + " &password=" + password
                + "&message=" + stringToHex(message)
                + "&dca=16bit"
                + "&msisdn=" + to
                + "&sender=" + from
                + "&repliable=0";

        String res = Utils.sendPostRequest("https://bulksms.vsms.net/eapi/submission/send_sms/2/2.0", post);

        return res != null && res.startsWith("0|");
    }

    private String stringToHex(String s) {
        char[] chars = s.toCharArray();
        String next;
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            next = Integer.toHexString((int) chars[i]);
            for (int j = 0; j < (4 - next.length()); j++) {
                output.append("0");
            }
            output.append(next);
        }
        return output.toString();
    }
}
