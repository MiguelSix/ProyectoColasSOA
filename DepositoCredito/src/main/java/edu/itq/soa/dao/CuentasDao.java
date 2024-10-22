package edu.itq.soa.dao;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class CuentasDao {
    
    Map<String, Double> cuentas = Map.of(
            "1234567890", 1000.0,
            "0987654321", 2000.0);
    
    public Double getBalance(String accountNumber) {
        if (cuentas.containsKey(accountNumber)) {
            return cuentas.get(accountNumber);
        }
        return 0.0;
    }
}
