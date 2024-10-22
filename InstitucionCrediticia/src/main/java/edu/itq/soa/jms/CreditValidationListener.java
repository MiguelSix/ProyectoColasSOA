package edu.itq.soa.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import edu.itq.soa.dto.CreditValidationResponse;
import edu.itq.soa.dto.JmsMessage;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;

@Component
public class CreditValidationListener {
    
    @Autowired
    private JmsSender jmsSender;
    
    @JmsListener(destination = "response.validation")
    public void receive(Message message) {
        try 
        {
            TextMessage textMessage = (TextMessage) message;
            JmsMessage jmsMessage = new JmsMessage(textMessage);
            
            Gson gson = new Gson();
            CreditValidationResponse response = gson.fromJson(
                    jmsMessage.getMessage(),
                    CreditValidationResponse.class
            );
            
            // Checar el status de la respuesta
            if (response.approved()) {
                System.out.println("CREDIT OF CLIENT " + response.clientName() + "HAS BEEN APPROVED");
                jmsSender.send("amortization.request", jmsMessage);
            } else {
                System.out.println("CREDIT OF CLIENT " + response.clientName() + "HAS BEEN REJECTED");
                jmsSender.send("credit.response", jmsMessage);
            }
            
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
