package helloG;


import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

public class MyReceiver {
    public static void main(String[] args) {
        try{
            Properties props = new Properties();
//            props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            props.put(Context.SECURITY_PRINCIPAL,"manager");
            props.put(Context.SECURITY_CREDENTIALS,"manager1");
            props.put(Context.URL_PKG_PREFIXES,"jboss.naming.client.ejb.context");
            props.put(Context.INITIAL_CONTEXT_FACTORY,"org.wildfly.naming.client.WildFlyInitialContextFactory");
            props.put(Context.PROVIDER_URL,"http-remoting://localhost:8080");


            InitialContext ctx=new InitialContext(props);
            QueueConnectionFactory f=(QueueConnectionFactory)ctx.lookup("jms/RemoteConnectionFactory");
            QueueConnection con=f.createQueueConnection("manager","manager1");
            con.start();
            //2) create Queue session
            QueueSession ses=con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            //3) get the Queue object
            Queue t=(Queue)ctx.lookup("jms/SoaIsGreate");
            //4)create QueueReceiver
            QueueReceiver receiver=ses.createReceiver(t);

            //5) create listener object
            MyListener listener=new MyListener();

            //6) register the listener object with receiver
            receiver.setMessageListener(listener);

            System.out.println("Receiver1 is ready, waiting for messages...");
            System.out.println("press Ctrl+c to shutdown...");
            while(true){
                Thread.sleep(1000);
            }
        }catch(Exception e){System.out.println(e);}
    }

}
