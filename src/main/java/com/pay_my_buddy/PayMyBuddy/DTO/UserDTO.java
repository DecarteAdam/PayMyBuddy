package com.pay_my_buddy.PayMyBuddy.DTO;

import com.pay_my_buddy.PayMyBuddy.data.UserRepository;
import com.pay_my_buddy.PayMyBuddy.model.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Data
@Component
public class UserDTO {

    @Autowired
    private UserRepository userRepository;

    private int id;
    private String firstname;
    private String lastname;
    private List<UserDTO> connections;

    private User fromDTO(){
        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
    /*    user.setConnections(userDTO.connections);*/
        return user;
    }

    public UserDTO toDTO(User user){
        firstname = user.getFirstname();
        lastname = user.getLastname();
        return this;
    }

}
