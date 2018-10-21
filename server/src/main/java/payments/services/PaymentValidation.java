package payments.services;

import com.google.common.base.Strings;
import payments.exceptions.ValidationException;
import payments.model.PaymentsRequest;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class PaymentValidation {

    public void check(PaymentsRequest request) {
        List<String> errors = new ArrayList<>();
        checkDebtorAndCredtorAccountsDifferent(request, errors);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private void checkDebtorAndCredtorAccountsDifferent(PaymentsRequest request, List<String> errors) {
        String debtorAccount = request.getDebtorAccount();
        String creditorAccount = request.getCreditorAccount();

        if (Strings.isNullOrEmpty(debtorAccount)) {
            return;
        }

        if (debtorAccount.equals(creditorAccount)) {
            errors.add("Debtor and Credtor accounts must be different");
        }
    }
}
