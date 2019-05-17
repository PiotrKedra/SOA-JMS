package helloG;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(name = "HelloWorldExample", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/helloQueue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class HelloWorldListener implements MessageListener {

    @Override
    public void onMessage(Message inMessage) {
        TextMessage message = (TextMessage)inMessage;
        try {
            System.out.println(String.format("Hello, %s", message.getText()));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
