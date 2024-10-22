package edu.itq.soa.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import edu.itq.soa.dto.AmortizationRow;

@Service
public class AmortizationLogic {
    
    public List<AmortizationRow> generateAmortizationTable(
            double loanAmount, 
            int termMonths, 
            double annualInterestRate,
            Map<Integer, Double> extraPayments) {
        
        List<AmortizationRow> table = new ArrayList<>();
        
        // Convertir tasa anual a mensual
        double monthlyRate = annualInterestRate / 12.0;
        
        // Calcular pago mensual
        double monthlyPayment = calculateMonthlyPayment(loanAmount, termMonths, monthlyRate);
        
        double balance = loanAmount;
        double totalInterest = 0.0;
        
        for (int month = 1; month <= termMonths; month++) {
            // Calcular interés del mes
            double interest = Math.round((balance * monthlyRate) * 100.0) / 100.0;
            
            // Calcular capital (principal) del pago
            double capital = Math.round((monthlyPayment - interest) * 100.0) / 100.0;
            
            // Manejar pagos extra si existen
            double extraPayment = 0.0;
            if (extraPayments != null && extraPayments.containsKey(month)) {
                extraPayment = extraPayments.get(month);
            }
            
            // Actualizar balance
            balance = Math.round((balance - capital - extraPayment) * 100.0) / 100.0;
            
            // Actualizar total de intereses
            totalInterest = Math.round((totalInterest + interest) * 100.0) / 100.0;
            
            // Crear y añadir la fila a la tabla
            table.add(new AmortizationRow(
                month,
                monthlyPayment,
                interest,
                capital,
                extraPayment,
                Math.max(balance, 0.0), // Evitar balance negativo
                totalInterest
            ));
            
            // Si el balance llega a 0 o menos, terminar
            if (balance <= 0) {
                break;
            }
        }
        
        return table;
    }
    
    private double calculateMonthlyPayment(
            double loanAmount, 
            int termMonths, 
            double monthlyRate) {
        
        double powered = Math.pow(1 + monthlyRate, termMonths);
        
        return Math.round((loanAmount * monthlyRate * powered / (powered - 1)) * 100.0) / 100.0;
    }

}
