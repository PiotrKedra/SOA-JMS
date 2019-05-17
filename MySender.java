package helloG;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

//@SuppressWarnings("Duplicates")
public class MySender {
    public static void main(String[] args) {
        try
        {   //Create and start connection
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
            //2) create queue session
            QueueSession ses=con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            //3) get the Queue object
            Queue t=(Queue)ctx.lookup("jms/SoaIsGreate");
            //4)create QueueSender object
            QueueSender sender=ses.createSender(t);
            //5) create TextMessage object
            TextMessage msg=ses.createTextMessage();

            //6) write message
            BufferedReader b=new BufferedReader(new InputStreamReader(System.in));
            while(true)
            {
                System.out.println("Enter Msg, end to terminate:");
                String s=b.readLine();
                if (s.equals("end"))
                    break;
                msg.setText(s);
                //7) send message
                sender.send(msg);
                System.out.println("Message successfully sent.");
            }
            //8) connection close
            con.close();
        }catch(Exception e){System.out.println(e);}
    }
}