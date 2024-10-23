package edu.itq.soa.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import edu.itq.soa.dto.CreditDepositResponse;
import edu.itq.soa.dto.JmsMessage;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;

@Component
public class CreditDepositListener {
    
    @Autowired
    private JmsSender jmsSender;
    
    @JmsListener(destination = "deposit.out")
    public void receive(Message message) {
        try 
        {
            TextMessage textMessage = (TextMessage) message;
            JmsMessage jmsMessage = new JmsMessage(textMessage);
            
            Gson gson = new Gson();
            CreditDepositResponse response = gson.fromJson(
                    jmsMessage.getMessage(),
                    CreditDepositResponse.class
            );
            
            jmsSender.send("credit.out", jmsMessage);
            
            System.out.println("Credit Deposit Response: " + response.toString());
            
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
