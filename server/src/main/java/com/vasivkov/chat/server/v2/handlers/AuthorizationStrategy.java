package com.vasivkov.chat.server.v2.handlers;

import com.vasivkov.chat.server.MessageHandler;
import com.vasivkov.chat.server.User;
import com.vasivkov.chat.server.dao.UserDao;
import com.vasivkov.chat.server.v2.ServerConnectionV2;
import com.vasivkov.chat.server.v2.ServerV2;
import com.vasivkov.chat.server.v2.vo.AuthorizationRequest;
import com.vasivkov.chat.server.v2.vo.GeneralResponse;
import com.vasivkov.chat.server.v2.vo.ResponseWithRecipients;
import org.apache.log4j.Logger;
import java.sql.SQLException;

public class AuthorizationStrategy implements Strategy<AuthorizationRequest> {
    private static final Logger LOGGER = Logger.getLogger(MessageHandler.class.getName());
    private static UserDao userDao = new UserDao();
    private ServerConnectionV2 serverConnectionV2;


    @Override
    public ResponseWithRecipients process(AuthorizationRequest authorizationRequest) {
        String login = authorizationRequest.getLogin();
        String password = authorizationRequest.getPassword();
        int id = authorizationRequest.getId();
        try {
           User  user = userDao.findByLogin(login);
            if(user == null){
                return new ResponseWithRecipients(id, new GeneralResponse(false, "Invalid login!"));
            } else if (!password.equals(user.getPassword())){
                return new ResponseWithRecipients(id, new GeneralResponse(false, "Invalid password!"));
            }else {
                ServerV2.getConnectedClients().get(id).setActive(true);
                return new ResponseWithRecipients(id, new GeneralResponse(true, "You joined!"));
            }
        } catch (SQLException e) {
            return new ResponseWithRecipients(id, new GeneralResponse(false, "Please, try again later!"));
        }
        }
    }

