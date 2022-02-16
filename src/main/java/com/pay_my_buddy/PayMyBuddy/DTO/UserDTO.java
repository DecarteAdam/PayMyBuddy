package com.pay_my_buddy.PayMyBuddy.DTO;

import com.pay_my_buddy.PayMyBuddy.data.UserRepository;
import com.pay_my_buddy.PayMyBuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<UserDTO> getConnections() {
        return connections;
    }

    public void setConnections(List<UserDTO> connections) {
        this.connections = connections;
    }
}
