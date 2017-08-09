package com.vasivkov.chat.server.v2;

import com.vasivkov.chat.common.Message;
import com.vasivkov.chat.common.MessageResponse;
import com.vasivkov.chat.server.dao.MessageDao;
import com.vasivkov.chat.server.v2.handlers.MessageProcessor;
import com.vasivkov.chat.common.Request;
import com.vasivkov.chat.server.v2.vo.ResponseWithRecipients;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {

    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private static final BlockingQueue<Request> requests = new LinkedBlockingQueue<>();
    private static final BlockingQueue<ResponseWithRecipients> responses = new LinkedBlockingQueue<>();
    private static final Map<Integer, ServerConnection> connectedClients = new HashMap<>();

    public static void main(String[] args) throws IOException {
        verifyProgramArguments(args);

        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
        LOGGER.info("Server started! Waiting for clients to connect...");

        new Thread(new MessageSender(responses, connectedClients)).start();
        new Thread(new MessageProcessor(requests, responses)).start();

        waitConnection(serverSocket);
    }

    public static Map<Integer, ServerConnection> getConnectedClients() {
        return connectedClients;
    }

    private static void verifyProgramArguments(String[] args) {
        if (args.length != 1) {
            System.out.println("Wrong input argument!");
            System.exit(-1);
        }
        Integer.parseInt(args[0]);
    }

    private static void closeSocketQuitely(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                LOGGER.error("Failed to close socket", e);
            }
        }
    }

    private static void waitConnection(ServerSocket serverSocket) {
        Socket socket = null;
        int id = 0;
        try {
            while (true) {
                socket = serverSocket.accept();
                ServerConnection serverConnectionV2 = new ServerConnection(socket, requests, id);
                connectedClients.put(id, serverConnectionV2);
                id++;
                new Thread(serverConnectionV2).start();
            }
        } catch (IOException e) {
            LOGGER.error("Failed to connect client", e);
        } finally {
            closeSocketQuitely(socket);
        }
    }

    public static List<Integer> getAuthorizedClients(int id){
        List<Integer> resipients = new ArrayList<>();
        for (Map.Entry<Integer, ServerConnection> pair : connectedClients.entrySet()) {
            if (pair.getKey() != id && pair.getValue().isAuthorized()) {
                resipients.add(pair.getKey());
            }
        }
        return  resipients;
    }

    public static List<ResponseWithRecipients>  getLastLetters(List<Message> messages, int id){
        List<ResponseWithRecipients> responseWithRecipientss = new ArrayList<>();
        try {
            messages = new MessageDao().getLastTenMessages();
        } catch (SQLException e) {
            LOGGER.error("Failed to write messages from database", e);
        }
        for (Message message : messages) {
            responseWithRecipientss.add(new ResponseWithRecipients(id, new MessageResponse(message)));
        }
        return responseWithRecipientss;
    }

}
