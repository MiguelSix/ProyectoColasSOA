package edu.itq.soa.jms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.itq.soa.business.AmortizationLogic;
import edu.itq.soa.dto.AmortizationResponse;
import edu.itq.soa.dto.AmortizationRow;
import edu.itq.soa.dto.CreditRequest;
import edu.itq.soa.dto.JmsMessage;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;

@Component
public class AmortizationListener {
    
    @Autowired
    private JmsSender jmsSender;
    
    @Autowired
    private AmortizationLogic amortizationLogic;
    
    @JmsListener(destination = "amortization.request")
    public void receive(Message message) {
        try 
        {
            TextMessage textMessage = (TextMessage) message;
            JmsMessage jmsMessage = new JmsMessage(textMessage);
            
            Gson gson = new GsonBuilder()
                    .serializeSpecialFloatingPointValues()
                    .create();
                
            CreditRequest request = gson.fromJson(
                jmsMessage.getMessage(),
                CreditRequest.class
            );
            
            //double loanAmount = request.suggestedAmount() > 0 ? request.suggestedAmount() : request.requestedAmount();
            double loanAmount = request.requestedAmount();
            
            // Generar tabla de amortización usando los datos de la solicitud
            List<AmortizationRow> table = amortizationLogic.generateAmortizationTable(
                loanAmount,
                request.termMonths(),
                request.annualRate(),
                request.extraPayments()  // Usando los pagos extras de la solicitud
            );
            
            // Crear respuesta con la tabla
            AmortizationResponse response = new AmortizationResponse(
                request.accountNumber(),
                request.clientName(),
                loanAmount,
                request.termMonths(),
                request.annualRate(),
                request.accountBalance(),
                table
            );

            // Enviar a cola de amortization.response
            jmsMessage.setMessage(gson.toJson(response));
            
            // Manda al historial
            jmsSender.send("amortization.history", jmsMessage);
            
            // Manda a la respuesta
            jmsSender.send("amortization.response", jmsMessage);
            
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

}
