package in.amal.banking.service;

import in.amal.banking.model.Account;
import in.amal.banking.repository.AccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger LOG = LogManager.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Collection<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account createAccount(long id) {
        Account account = getAccountIfPresent(id);
        if (account != null) return account;
        account = new Account();
        account.setId(id);
        LOG.info("Account {} is created successfully.", account);
        accountRepository.save(account);
        return account;
    }

    private Account getAccountIfPresent(long id) {
        // Return existing account if the account already exists
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isPresent()) {
            return accountOptional.get();
        }
        return null;
    }

    @Override
    public Account getAccountById(long id) {
        Account account = getAccountIfPresent(id);
        if(account == null) {
            throw new RuntimeException("Account not found with id: " + id);
        }
        return account;
    }

    @Override
    public Account updateAccount(long id, String firstName, String lastName) {
        Account account = getAccountIfPresent(id);
        if (account == null) throw new RuntimeException("Account not found with id: " + id);
        account.setFirstName(firstName);
        account.setLastName(lastName);
        LOG.info("Account {} is update successfully.", account);
        accountRepository.save(account);
        return account;
    }
}
