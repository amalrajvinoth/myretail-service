package in.amal.banking.service;

import in.amal.banking.model.Account;
import in.amal.banking.repository.AccountRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class AccountServiceImplTest {
    @InjectMocks
    AccountServiceImpl accountService;

    @Mock
    AccountRepository accountRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAllAccounts() {
        when(accountRepository.findAll()).thenReturn(
                Arrays.asList(
                        new Account(11, "First1", "Last1"),
                        new Account(12, "First2", "Last2")));

        assertEquals(accountService.getAllAccounts().size(), 2);
    }

    @Test
    public void createAccount() {
        Account account = new Account(11, "First1", "Last1");
        when(accountRepository.save(account)).thenReturn(account);
        Account actualAccount = accountService.createAccount(11);

        assertEquals(actualAccount.getId().longValue(), account.getId().longValue());
    }

    @Test
    public void getAccountById() {
        Account account = new Account(11, "First1", "Last1");
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account));
        Account actualAccount = accountService.getAccountById(11);
        verify(accountRepository, times(1)).findById(anyLong());

        assertEquals(actualAccount.getId().longValue(), account.getId().longValue());
        assertEquals(actualAccount.getFirstName(), account.getFirstName());
        assertEquals(actualAccount.getLastName(), account.getLastName());
    }

    @Test
    public void updateAccount() {
        Account account = new Account(11, "First1", "Last1");
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);
        Account actualAccount = accountService.updateAccount(11, "First1", "Last1");
        verify(accountRepository, times(1)).findById(anyLong());

        assertEquals(actualAccount.getId().longValue(), account.getId().longValue());
        assertEquals(actualAccount.getFirstName(), account.getFirstName());
        assertEquals(actualAccount.getLastName(), account.getLastName());
    }
}