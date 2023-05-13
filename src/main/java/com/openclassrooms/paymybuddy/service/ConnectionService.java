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

    public List<Connection> findBySender(User sender) {
        return connectionRepository.findBySender(sender);
    }

    public List<Connection> findByReceiver(User receiver) {
        return connectionRepository.findByReceiver(receiver);
    }

    public Connection findBySenderAndReceiver(User sender, User receiver) {
        return connectionRepository.findBySenderAndReceiver(sender, receiver);
    }

    public Connection findById(int id) {
        return connectionRepository.findById(id);
    }

    public Connection addConnection(User receiver) {
        User sender = userService.getCurrentUser();

        Connection connection = new Connection();
        connection.setSender(sender);
        connection.setReceiver(receiver);
        return connectionRepository.save(connection);
    }
}
