package edu.itq.soa.business;

import org.springframework.stereotype.Service;

import edu.itq.soa.dto.CreditValidationRequest;
import edu.itq.soa.dto.CreditValidationResponse;

@Service
public class CreditValidationLogic {
    
    public CreditValidationResponse validateCredit(CreditValidationRequest request) {

        boolean approved = request.requestedAmount() <= 500000;
        String reason = approved ? "Credit history approved" : "Amount exceeds credit limit";
        double requestedAmmount = approved ? request.requestedAmount() : 500000;
        
        return new CreditValidationResponse(
            request.accountNumber(),
            request.clientName(),
            approved,
            reason,
            requestedAmmount,
            request.termMonths(),
            request.annualRate(),
            request.accountBalance(),
            request.extraPayments()
        );
    }

}
