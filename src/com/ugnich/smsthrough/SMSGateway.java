package com.ugnich.smsthrough;

public interface SMSGateway {

    public String getGatewayName();

    public boolean send(String to, String message);

}
