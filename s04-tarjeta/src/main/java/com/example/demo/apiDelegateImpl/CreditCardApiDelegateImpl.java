package com.example.demo.apiDelegateImpl;

import com.example.demo.document.CreditCard;
import com.example.demo.Service.CreditCardService;
import com.example.demo.api.CreditCardsApiDelegate;
import com.example.demo.mapper.CreditCardMapper;

import com.example.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreditCardApiDelegateImpl implements CreditCardsApiDelegate {
    /**
     * Para acceder a CreditCardService.
     */
    @Autowired
    private CreditCardService creditCardService;

    /**
     * CreditCardApiDelegateImpl.
     * @param cardNumber variable.
     * @return CreditCardBalance.
     */
    @Override
    public ResponseEntity<CreditCardBalance> creditCardsCardNumberBalanceGet(final String cardNumber) {
        CreditCard creditCard = creditCardService.findCardByNumber(cardNumber);
        if (creditCard != null) {
            return ResponseEntity.ok(CreditCardMapper.toCreditCardBalance(creditCard));
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * CreditCardApiDelegateImpl.
     * @param cardNumber variable.
     * @param creditCardPayment objeto.
     * @return ResponseEntity.
     */
    @Override
    public ResponseEntity<Void> creditCardsCardNumberPaymentPost(final String cardNumber, final CreditCardPayment creditCardPayment) {
        try {
            creditCardService.makePayment(cardNumber, creditCardPayment.getAmount().doubleValue());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * CreditCardApiDelegateImpl.
     * @param cardNumber variable.
     * @param creditCardPurchase objeto.
     * @return ResponseEntity.
     */
    @Override
    public ResponseEntity<Void> creditCardsCardNumberPurchasePost(final String cardNumber, final CreditCardPurchase creditCardPurchase) {
        try {
            creditCardService.registerPurchase(cardNumber, creditCardPurchase.getAmount().doubleValue());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * CreditCardApiDelegateImpl.
     * @param creditCardCreateInput objeto.
     * @return CreditCardDetails.
     */
    @Override
    public ResponseEntity<CreditCardDetails> creditCardsPost(final CreditCardCreateInput creditCardCreateInput) {
        CreditCard creditCard = CreditCardMapper.fromCreditCardCreateInput(creditCardCreateInput);
        CreditCard savedCard = creditCardService.saveCreditCard(creditCard);
        return ResponseEntity.ok(CreditCardMapper.toCreditCardDetails(savedCard));
    }

    /**
     * CreditCardApiDelegateImpl.
     * @param clientId variable.
     * @return CreditCardDetails lista.
     */
    @Override
    public ResponseEntity<List<CreditCardDetails>> creditCardsByClientClientIdGet(final String clientId) {
        List<CreditCard> creditCards = creditCardService.findCardsByClientId(clientId);
        List<CreditCardDetails> creditCardDetailsList = creditCards.stream()
                .map(CreditCardMapper::toCreditCardDetails)
                .collect(Collectors.toList());
        System.out.println("Returning: " + creditCardDetailsList);

        return ResponseEntity.ok(creditCardDetailsList);
    }
}
