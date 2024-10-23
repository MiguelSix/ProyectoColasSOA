package edu.itq.soa.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import edu.itq.soa.business.CreditValidationLogic;
import edu.itq.soa.dto.CreditValidationRequest;
import edu.itq.soa.dto.CreditValidationResponse;
import edu.itq.soa.dto.JmsMessage;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;

@Component
public class ValidationListener {
    
    @Autowired
    private JmsSender jmsSender;
    
    @Autowired
    private CreditValidationLogic validationLogic;
    
    @JmsListener(destination = "validation.in")
    public void receive(Message message) {
        try 
        {
            TextMessage textMessage = (TextMessage) message;
            JmsMessage jmsMessage = new JmsMessage(textMessage);
            
            Gson gson = new Gson();
            CreditValidationRequest request = gson.fromJson(
                    jmsMessage.getMessage(),
                    CreditValidationRequest.class
            );

            CreditValidationResponse response = validationLogic.validateCredit(request);
            
            jmsMessage.setMessage(gson.toJson(response));
            
            // Enviar a response.validation
            
            jmsSender.send("validation.out", jmsMessage);
            
            System.out.println("Validacion del credito realizada: ");
            System.out.println(response.toString());

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

}
