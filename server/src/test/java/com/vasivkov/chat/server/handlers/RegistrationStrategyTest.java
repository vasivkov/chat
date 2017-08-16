package com.vasivkov.chat.server.handlers;

import com.vasivkov.chat.common.GeneralResponse;
import com.vasivkov.chat.common.RegistrationRequest;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationStrategyTest {
    private static final String TEST_LOGIN = "test_login";
    private static final String TEST_PASS = "test_pass";
    private static final Integer CLIENT1_CONNECTION_ID = 1;
    private static final Integer CLIENT2_CONNECTION_ID = 2;
    private static final Integer CLIENT3_CONNECTION_ID = 3;

    @Mock
    private UserDao userDao;

    @Mock
    private MessageDao messageDao;

    @InjectMocks
    RegistrationStrategy as = new RegistrationStrategy();

    @Before
    public void setUp() {
        Server.addClient(CLIENT1_CONNECTION_ID, new ServerConnection());
        Server.addClient(CLIENT2_CONNECTION_ID, new ServerConnection());
        Server.addClient(CLIENT3_CONNECTION_ID, new ServerConnection());
    }

    @Test
    public void processNewUser() throws Exception {
        when(userDao.findByLogin(TEST_LOGIN)).thenReturn(null);
        when(messageDao.getLastTenMessages()).thenReturn(Collections.EMPTY_LIST);

        RegistrationRequest request = new RegistrationRequest(TEST_LOGIN, TEST_PASS);
        request.setId(CLIENT1_CONNECTION_ID);

        List<ResponseWithRecipients> res = as.process(request);
        assertEquals(1, res.size());
        ResponseWithRecipients response = res.get(0);
        GeneralResponse gr = (GeneralResponse) response.getResponse();
        assertTrue(gr.isOutcome());

        assertEquals(1, response.getRecipients().size());
        assertTrue(response.getRecipients().contains(CLIENT1_CONNECTION_ID));

        assertTrue(Server.getConnectedClients().get(CLIENT1_CONNECTION_ID).isAuthorized());

    }

    @Test
    public void processExistingLogin() throws Exception{
        when(userDao.findByLogin(TEST_LOGIN)).thenReturn(new User());

        RegistrationRequest request = new RegistrationRequest(TEST_LOGIN, TEST_PASS);
        request.setId(CLIENT1_CONNECTION_ID);

        List<ResponseWithRecipients> res = as.process(request);
        assertEquals(1, res.size());

        ResponseWithRecipients response = res.get(0);
        GeneralResponse gr = (GeneralResponse) response.getResponse();
        assertFalse(gr.isOutcome());

        assertFalse(Server.getConnectedClients().get(CLIENT1_CONNECTION_ID).isAuthorized());

    }

    @Test
    public void sendingGreetingMessagesToOtherClient() throws Exception {
        when(userDao.findByLogin(TEST_LOGIN)).thenReturn(null);
        when(messageDao.getLastTenMessages()).thenReturn(Collections.EMPTY_LIST);

        RegistrationRequest request = new RegistrationRequest(TEST_LOGIN, TEST_PASS);
        request.setId(CLIENT1_CONNECTION_ID);
        Server.getConnectedClients().get(CLIENT2_CONNECTION_ID).setAuthorized(true);
        List<ResponseWithRecipients> res = as.process(request);
        assertEquals(2, res.size());
    }

}