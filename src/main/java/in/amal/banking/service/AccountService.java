package in.amal.banking.service;

import in.amal.banking.model.Account;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface AccountService {

    Collection<Account> getAllAccounts();
    Account createAccount(long id);
    Account getAccountById(long id);
    Account updateAccount(long id, String firstName, String lastName);
}
