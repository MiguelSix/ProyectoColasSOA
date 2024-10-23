package edu.itq.soa.business;

import java.util.Random;

import org.springframework.stereotype.Service;

import edu.itq.soa.dto.CreditValidationRequest;
import edu.itq.soa.dto.CreditValidationResponse;

@Service
public class CreditValidationLogic {
    
    public CreditValidationResponse validateCredit(CreditValidationRequest request) {
        
        // Generamos una serie de numeros random del 1 al 10
        Random random = new Random();
        int randomNumber = random.nextInt(10) + 1;
        System.out.println("Random Number: " + randomNumber);
        
        boolean approved = randomNumber <= 8;
        String reason;
        
        if (approved) {
            reason = "Credit Approved";
        } else {
            reason = "Credit Denied";
        }
        
        return new CreditValidationResponse(
            request.accountNumber(),
            request.clientName(),
            approved,
            reason,
            request.requestedAmount(),
            request.termMonths(),
            request.annualRate(),
            request.accountBalance(),
            request.extraPayments()
        );
    }

}
