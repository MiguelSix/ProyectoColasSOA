package edu.itq.soa.business;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.itq.soa.dao.CuentasDao;
import edu.itq.soa.dto.CreditResponse;
import edu.itq.soa.dto.DepositRequest;

@Service
public class DepositLogic {
    
    // Dao con la información de las cuentas (accountNumber, balance)
    @Autowired
    private CuentasDao cuentasDao;
    
    public CreditResponse processDeposit(DepositRequest request) {
        
        double balance = cuentasDao.getBalance(request.accountNumber());
        
        // Armamos la respuesta
        CreditResponse response = new CreditResponse(
                request.accountNumber(),
                request.clientName(),
                balance,
                request.loanAmount(),
                balance + request.loanAmount(),
                "COMPLETED",
                "Credit deposited successfully ;D"
        );
        
        return response;
    }

}
