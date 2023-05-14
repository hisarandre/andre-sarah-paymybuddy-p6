package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing connection between users.
 */
@Service
public class ConnectionService {

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private UserService userService;

    /**
     * Find all connections where the sender is the given user.
     *
     * @param sender The user who sent the connection requests.
     * @return A list of connections where the sender is the given user.
     */
    public List<Connection> findBySender(User sender) {
        return connectionRepository.findBySender(sender);
    }

    /**
     * Find all connections where the receiver is the given user.
     *
     * @param receiver The user who received the connection requests.
     * @return A list of connections where the receiver is the given user.
     */
    public List<Connection> findByReceiver(User receiver) {
        return connectionRepository.findByReceiver(receiver);
    }

    /**
     * Find a connection between the sender and receiver users.
     *
     * @param sender The user who sent the connection requests.
     * @param receiver The user who received the connection requests.
     * @return The connection between the sender and receiver users.
     */
    public Connection findBySenderAndReceiver(User sender, User receiver) {
        return connectionRepository.findBySenderAndReceiver(sender, receiver);
    }

    /**
     * Find a connection by its ID.
     *
     * @param id The ID of the connection to find.
     * @return The connection with the given ID.
     */
    public Connection findById(int id) {
        return connectionRepository.findById(id);
    }

    /**
     * Add a new connection between the current user and the given receiver user.
     *
     * @param receiver The user who received the connection requests.
     * @return The new connection.
     */
    public Connection addConnection(User receiver) {
        User sender = userService.getCurrentUser();

        Connection connection = new Connection();
        connection.setSender(sender);
        connection.setReceiver(receiver);
        return connectionRepository.save(connection);
    }
}
