package br.com.superdia.valida;

import com.argofire.ws.CreditCardValidator;
import com.argofire.ws.CreditCardValidatorSoap;

public class ValidaCartao {
    public static int validateCreditCard(String cardNumber, String expirationDate) {
        try {
            CreditCardValidator service = new CreditCardValidator();
            CreditCardValidatorSoap validatorSoap = service.getCreditCardValidatorSoap();

            return validatorSoap.validCard(cardNumber, expirationDate);
        } catch (Exception e) {
            e.printStackTrace();
            return -1; 
        }
    }
}
