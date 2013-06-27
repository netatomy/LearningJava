package zqc.reading.poeaa.ch09domainlogic.appservices;


public interface EmailGateway {
    void sendEmailMessage(String to, String subject, String body);
}
