package edu.itq.soa.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import edu.itq.soa.dto.JmsMessage;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;

@Component
public class CreditRequestListener {
    
    @Autowired
    private JmsSender jmsSender;
    
    @JmsListener(destination = "credit.in")
    public void receive(Message message) {
        try 
        {
            TextMessage textMessage = (TextMessage) message;
            JmsMessage jmsMessage = new JmsMessage(textMessage);
            
            //Inicio de la solicitud de crédito
            System.out.println("CREDIT RESPONSE RECEIVED:");
            System.out.println(jmsMessage.getMessage());
            
            // Mandar a validar la solicitud de crédito
            jmsSender.send("validation.in", jmsMessage);
          
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

}
