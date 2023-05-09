package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionService {

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private UserService userService;

    public List<Connection> findByIdSender(int id) {
        return connectionRepository.findByIdSender(id);
    }

    public List<Connection> findByIdReceiver(int id) {
        return connectionRepository.findByIdReceiver(id);
    }

    public Connection findByIdSenderAndIdReceiver(int idSender, int idReceiver) {
        return connectionRepository.findByIdSenderAndIdReceiver(idSender, idReceiver);
    }

    public Connection findById(int id) {
        return connectionRepository.findById(id);
    }

    public Connection addConnection(User receiver) {
        User sender = userService.getCurrentUser();

        Connection connection = new Connection();
        connection.setIdSender(sender.getIdUser());
        connection.setIdReceiver(receiver.getIdUser());
        return connectionRepository.save(connection);
    }
}
