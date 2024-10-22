package edu.itq.soa.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;
import edu.itq.soa.business.DepositLogic;
import edu.itq.soa.dto.CreditResponse;
import edu.itq.soa.dto.DepositRequest;
import edu.itq.soa.dto.JmsMessage;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;

@Component
public class DepositListener {
    
    @Autowired
    private JmsSender jmsSender;
    
    @Autowired
    private DepositLogic depositLogic;
    
    @JmsListener(destination = "deposit.request")
    public void receive(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            JmsMessage jmsMessage = new JmsMessage(textMessage);
            
            Gson gson = new Gson();
            DepositRequest request = gson.fromJson(
                jmsMessage.getMessage(), 
                DepositRequest.class
            );
            
            CreditResponse response = depositLogic.processDeposit(request);
            
            jmsMessage.setMessage(gson.toJson(response));
            jmsSender.send("deposit.response", jmsMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
