package in.amal.banking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.amal.banking.Application;
import in.amal.banking.model.Account;
import in.amal.banking.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
public class AccountResourceTest {
    private static final String HOST = "http://localhost:";

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AccountService accountService;

    @LocalServerPort
    private int port;

    @Test
    public void testGetAccountByIdSuccess() throws Exception {
        accountService.createAccount(4);
        Account account = accountService.updateAccount(4L, "Jon", "Snow");
        String url = prepareUrl("account", account.getId());
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(4)))
                .andExpect(jsonPath("$.firstName", equalTo(account.getFirstName())))
                .andExpect(jsonPath("$.lastName", equalTo(account.getLastName())));
    }

    @Test
    public void testCreateAccountSuccess() throws Exception {
        Account  account = accountService.createAccount(5);
        String url = prepareUrl("account", account.getId());
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(5)))
                .andExpect(jsonPath("$.firstName", equalTo(null)))
                .andExpect(jsonPath("$.lastName", equalTo(null)));
    }

    @Test
    public void testGetAccountByIdFailure() throws Exception {
        String url = prepareUrl("account", 6);
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.response.code", equalTo(500)))
                .andExpect(jsonPath("$.response.cause", equalTo("Account not found with id: 6")));
    }

    @Test
    public void testGetAllAccountSuccess() throws Exception {
        Account  account = accountService.updateAccount(1, "Jane", "Doe");
        String url = prepareURLWithPort("/accounts");
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].firstName", equalTo(account.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", equalTo(account.getLastName())));
    }

    @Test
    public void testUpdateAccountSuccess() throws Exception {
        Account  account = accountService.updateAccount(1, "Jane", "Doe");
        String url = prepareUrl("account", 1)+"?firstName=Jane&lastName=Doe";
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.firstName", equalTo(account.getFirstName())))
                .andExpect(jsonPath("$.lastName", equalTo(account.getLastName())));
    }

    @Test
    public void testUpdateAccountFailure() throws Exception {
        String url = prepareUrl("account", 6)+"?firstName=Jane&lastName=Doe";
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.response.code", equalTo(500)))
                .andExpect(jsonPath("$.response.cause", equalTo("Account not found with id: 6")));
    }

    private String prepareUrl(String path, long id) {
        StringBuilder url = new StringBuilder().append("/").append(path).append("/").append(id);
        return prepareURLWithPort(url.toString());
    }

    private String prepareURLWithPort(String uri) {
        return HOST + port + uri;
    }
}