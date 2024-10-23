package edu.itq.soa.business;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.itq.soa.dao.CuentasDao;
import edu.itq.soa.dto.DepositResponse;
import edu.itq.soa.dto.DepositRequest;

@Service
public class DepositLogic {
    
    // Dao con la información de las cuentas (accountNumber, balance)
    @Autowired
    private CuentasDao cuentasDao;
    
    public DepositResponse processDeposit(DepositRequest request) {
        
        double balance = cuentasDao.getBalance(request.accountNumber());
        
        String date = java.time.LocalDate.now().toString();
        
        // Armamos la respuesta
        DepositResponse response = new DepositResponse(
                request.accountNumber(),
                request.clientName(),
                balance,
                request.loanAmount(),
                request.termMonths(),
                request.annualRate(),
                balance + request.loanAmount(),
                "COMPLETED",
                "Credit deposited successfully ;D",
                date,
                request.amortizationTable()
        );
        
        return response;
    }

}
