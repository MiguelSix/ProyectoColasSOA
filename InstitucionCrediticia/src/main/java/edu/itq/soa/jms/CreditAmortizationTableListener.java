package edu.itq.soa.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import edu.itq.soa.dto.CreditAmortizationResponse;
import edu.itq.soa.dto.JmsMessage;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;

@Component
public class CreditAmortizationTableListener {
    
    @Autowired
    private JmsSender jmsSender;
    
    @JmsListener(destination = "amortization.out")
    public void receive(Message message) {
        try 
        {
            TextMessage textMessage = (TextMessage) message;
            JmsMessage jmsMessage = new JmsMessage(textMessage);
            
            Gson gson = new Gson();
            
            CreditAmortizationResponse response = gson.fromJson(
                    jmsMessage.getMessage(),
                    CreditAmortizationResponse.class
            );
            
            // Una vez tenemos el credito aprobado y su tabla de amortizacion,
            // Mandamos los datos necesarios para el deposito a la cuenta del cliente
            
            System.out.println("AMORTIZATION TABLE FOR CLIENT " + response.clientName());
            System.out.println(response.toString());
            
            jmsSender.send("deposit.in", jmsMessage);
            
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
