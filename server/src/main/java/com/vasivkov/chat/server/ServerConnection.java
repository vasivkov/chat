package com.vasivkov.chat.server;

import com.vasivkov.chat.common.*;
import com.vasivkov.chat.server.dao.MessageDao;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.Map;

//public class ServerConnection implements Runnable {
//    private String login;
//    private ObjectInputStream ois;
//    private ObjectOutputStream oos;
//    private static final Logger LOGGER = Logger.getLogger(ServerConnection.class.getName());
//    private MessageDao messageDao = new MessageDao();
//
//    public void setLogin(String login) {
//        this.login = login;
//    }
//
//    public ObjectOutputStream getOos() {
//        return oos;
//    }
//
//    public ServerConnection(Socket socket) {
//        try {
//            oos = new ObjectOutputStream(socket.getOutputStream());
//            ois = new ObjectInputStream(socket.getInputStream());
//        } catch (IOException e) {
//            LOGGER.fatal("Failed to create streams", e);
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public void run() {
//        boolean isClientExit = false;
//        try {
//            while (!isClientExit) {
//                Object object = ois.readObject();
//                LOGGER.info("Received message " + object);
//                if (object instanceof Message) {
//                    Message message = (Message) object;
//                    message.setAuthor(login);
//                    messageDao.insertMessage(message);
//                    sendToAllClients(message);
//                } else if (object instanceof ClosedConnectionRequest) {
//                    Message message = new Message(login + " LEFT THE CHAT", "  ", new Date());
//                    sendToAllClients(message);
//                    Server.connectedClients.remove(login);
//                    isClientExit = true;
//                } else if (object instanceof Request) {
//                    Request request = (Request) object;
//                    Response response = MessageHandler.handle(request);
//                    if (response.isResult()) {
//                        login = request.getLogin();
//                        Server.connectedClients.put(login, this);
//                    }
//                    MessageTransportUtil.sendMessageWithRepeat(response, oos, 5);
//                    if (response.isResult()) {
//                        List<Message> list = messageDao.getLastTenMessages();
//                        for (Message message : list) {
//                            MessageTransportUtil.sendMessageNoGuarantee(message, oos);
//                        }
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            LOGGER.error("Failed to read from socket", e);
//
//        } finally {
//            IOUtils.closeQuietly(ois);
//            IOUtils.closeQuietly(oos);
//            LOGGER.info("Streams are closed. Client " + login + " is disconnected");
//        }
//    }
//
//    private void sendToAllClients(Object o) {
//        for (Map.Entry<String, ServerConnection> entry : Server.connectedClients.entrySet()) {
//            if (!login.equals(entry.getKey())) {
//                MessageTransportUtil.sendMessageNoGuarantee(o, entry.getValue().getOos());
//            }
//        }
//    }
//}

