package com.pay_my_buddy.PayMyBuddy.service;

import com.pay_my_buddy.PayMyBuddy.DTO.UserDTO;
import com.pay_my_buddy.PayMyBuddy.data.RelationRepository;
import com.pay_my_buddy.PayMyBuddy.data.UserRepository;
import com.pay_my_buddy.PayMyBuddy.model.Connection;
import com.pay_my_buddy.PayMyBuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConnectionService {
    @Autowired
    private RelationRepository relationRepository;
    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getConnections(User user){

        return relationRepository.findConnectionByUser1(user)
                .stream()
                .map(Connection::getUser2)
                .map(user2 -> this.userRepository.findUserById(user2.getId()))
                .map(user2 -> new UserDTO().toDTO(user2))
                .collect(Collectors.toList());
    }
}
