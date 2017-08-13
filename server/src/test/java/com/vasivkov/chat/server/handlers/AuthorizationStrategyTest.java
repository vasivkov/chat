package com.vasivkov.chat.server.handlers;

import com.vasivkov.chat.common.AuthorizationRequest;
import com.vasivkov.chat.common.GeneralResponse;
import com.vasivkov.chat.server.Server;
import com.vasivkov.chat.server.ServerConnection;
import com.vasivkov.chat.server.User;
import com.vasivkov.chat.server.dao.MessageDao;
import com.vasivkov.chat.server.dao.UserDao;
import com.vasivkov.chat.server.vo.ResponseWithRecipients;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthorizationStrategyTest {

    private static final String TEST_LOGIN = "test_login";
    private static final String TEST_PASS = "test_pass";
    private static final String TEST_INCORRECT_PASS = "test_incorrect_pass";
    private static final Integer CLIENT1_CONNECTION_ID = 1;
    private static final Integer CLIENT2_CONNECTION_ID = 2;
    private static final Integer CLIENT3_CONNECTION_ID = 3;

    @Mock
    private UserDao userDao;

    @Mock
    private MessageDao messageDao;

    @InjectMocks
    AuthorizationStrategy as = new AuthorizationStrategy();

    @Before
    public void setUp() {
        Server.addClient(CLIENT1_CONNECTION_ID, new ServerConnection());
        Server.addClient(CLIENT2_CONNECTION_ID, new ServerConnection());
        Server.addClient(CLIENT3_CONNECTION_ID, new ServerConnection());
    }

    @Test
    public void processExistingUser() throws Exception {
        when(userDao.findByLogin(TEST_LOGIN)).thenReturn(new User(TEST_LOGIN, TEST_PASS));
        when(messageDao.getLastTenMessages()).thenReturn(Collections.EMPTY_LIST);

        AuthorizationRequest request = new AuthorizationRequest(TEST_LOGIN, TEST_PASS);
        request.setId(CLIENT1_CONNECTION_ID);

        List<ResponseWithRecipients> res = as.process(request);
        assertEquals(1, res.size());
        ResponseWithRecipients response = res.get(0);
        GeneralResponse gr = (GeneralResponse) response.getResponse();
        assertTrue(gr.isOutcome());

        assertEquals(1, response.getRecipients().size());
        assertTrue(response.getRecipients().contains(CLIENT1_CONNECTION_ID));
    }


    @Test
    public void processLoginInvalid() throws Exception {
        when(userDao.findByLogin(TEST_LOGIN)).thenReturn(null);
        when(messageDao.getLastTenMessages()).thenReturn(Collections.EMPTY_LIST);

        AuthorizationRequest request = new AuthorizationRequest(TEST_LOGIN, TEST_PASS);
        request.setId(CLIENT1_CONNECTION_ID);

        List<ResponseWithRecipients> res = as.process(request);
        GeneralResponse gr = (GeneralResponse) res.get(0).getResponse();
        assertFalse(gr.isOutcome());

        assertEquals(1, res.get(0).getRecipients().size());
        Server.getConnectedClients().get(CLIENT2_CONNECTION_ID).setAuthorized(true);
        assertEquals(1, res.size());


    }

    @Test
    public void processPasswordInvalid() throws Exception {
        when(userDao.findByLogin(TEST_LOGIN)).thenReturn(new User(TEST_LOGIN, TEST_PASS));
        when(messageDao.getLastTenMessages()).thenReturn(Collections.EMPTY_LIST);

        AuthorizationRequest request = new AuthorizationRequest(TEST_LOGIN, TEST_INCORRECT_PASS);
        request.setId(CLIENT1_CONNECTION_ID);

        List<ResponseWithRecipients> res = as.process(request);
        GeneralResponse gr = (GeneralResponse) res.get(0).getResponse();
        assertFalse(gr.isOutcome());


    }

    @Test
    public void sendingGreetingMessagesToOtherClient() throws Exception {
        when(userDao.findByLogin(TEST_LOGIN)).thenReturn(new User(TEST_LOGIN, TEST_PASS));
        when(messageDao.getLastTenMessages()).thenReturn(Collections.EMPTY_LIST);

        AuthorizationRequest request = new AuthorizationRequest(TEST_LOGIN, TEST_PASS);
        request.setId(CLIENT1_CONNECTION_ID);
        Server.getConnectedClients().get(CLIENT2_CONNECTION_ID).setAuthorized(true);
        List<ResponseWithRecipients> res = as.process(request);
        assertEquals(2, res.size());
    }


}