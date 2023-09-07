package com.example.creditservice.apiDelegateImpl;

import com.example.creditservice.api.CreditsApiDelegate;
import com.example.creditservice.model.Credit;

import com.example.creditservice.servicio.ServiceCredit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CreditApiDelagateImpl implements CreditsApiDelegate {
    /**
     * Para acceder a ServiceCredit.
     */
    @Autowired
    private ServiceCredit serviceCredit;

    /**
     * Método para guardar una transacción.
     * @param credit parametro de Credit.
     * @return Credit del credit.
     */
    @Override
    public ResponseEntity<Credit> applyForCredit(final Credit credit) {
        return new ResponseEntity<Credit>(serviceCredit.createCredit(credit), HttpStatus.CREATED);
    }

    /**
     * Método para guardar una transacción.
     * @param clientId parametro de Credit.
     * @return Credit del credit.
     */
    @Override
    public ResponseEntity<Credit> getAllCreditsByClient(final String clientId) {
        Credit credit = serviceCredit.getCreditById(clientId);
       return ResponseEntity.ok(credit);
    }
}
