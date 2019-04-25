package in.amal.banking.controller;

import in.amal.banking.model.Account;
import in.amal.banking.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@Api(value = "Account APIs")
public class AccountResource {

    private static final Logger LOG = LogManager.getLogger(AccountResource.class);

    @Autowired
    private AccountService service;

    @ApiOperation(value = "Get all accounts in the system.")
    @GetMapping("/accounts")
    public ResponseEntity<Collection<Account>> getAllAccounts() throws Exception {
        Collection<Account> accounts = service.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @ApiOperation(value = "Create a new account.")
    @PostMapping("/account/{id}")
    public ResponseEntity<Account> createAccount(@PathVariable("id") long id) throws Exception {
        Account account = service.createAccount(id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @ApiOperation(value = "Get account by id.")
    @GetMapping("/account/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") long id) throws Exception {
        Account account = service.getAccountById(id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @ApiOperation(value = "Update account by id.")
    @PutMapping("/account/{id}")
    public ResponseEntity<Account> updateAccountById(@PathVariable("id") long id,
                                                  @RequestParam("firstName") String firstName,
                                                  @RequestParam("lastName") String lastName) throws Exception {
        Account account = service.updateAccount(id, firstName, lastName);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
