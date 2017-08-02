package com.vasivkov.chat.server.v2.handlers;

import com.vasivkov.chat.server.MessageHandler;
import com.vasivkov.chat.server.User;
import com.vasivkov.chat.server.dao.UserDao;
import com.vasivkov.chat.server.v2.ServerV2;
import com.vasivkov.chat.server.v2.vo.GeneralResponse;
import com.vasivkov.chat.server.v2.vo.RegistrationRequest;
import com.vasivkov.chat.server.v2.vo.ResponseWithRecipients;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.Date;

public class
RegistrationStrategy implements Strategy<RegistrationRequest> {
    private static final Logger LOGGER = Logger.getLogger(MessageHandler.class.getName());
    private static UserDao userDao = new UserDao();

    @Override
    public ResponseWithRecipients process(RegistrationRequest registrationRequest) {
        String login = registrationRequest.getLogin();
        String password = registrationRequest.getPassword();
        int id = registrationRequest.getId();

        try {
            User user = userDao.findByLogin(login);
            if(user == null ){
                ServerV2.getConnectedClients().get(id).setActive(true);
                userDao.insertUser(new User(login, password, new Date()));

                return  new ResponseWithRecipients(id, new GeneralResponse(true, "You are registrated!"));
            }
            return new ResponseWithRecipients(id, new GeneralResponse(false, "Such login already exits!"));
        }catch (SQLException e){
            return new ResponseWithRecipients(id, new GeneralResponse(false, "Please, try again later!"));
        }
    }
}
