package com.pay_my_buddy.PayMyBuddy.service;

import com.pay_my_buddy.PayMyBuddy.data.BankAccountRepository;
import com.pay_my_buddy.PayMyBuddy.data.UserRepository;
import com.pay_my_buddy.PayMyBuddy.model.BankAccount;
import com.pay_my_buddy.PayMyBuddy.model.CustomUserDetails;
import com.pay_my_buddy.PayMyBuddy.model.User;
import com.pay_my_buddy.PayMyBuddy.model.UserFormDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.constraints.NotBlank;

@Service
@SessionAttributes("user")
public class UserDetailService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailService.class);

    /**
     * Load user data
     */

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Load User by username: {}", username);
        User user = userRepository.findByUsername(username);
        return new CustomUserDetails(user);
    }

    public User getUser(String email) throws UsernameNotFoundException {
        logger.info("Get User by username: {}", email);
        return userRepository.findByEmail(email);
    }

    public void saveUser(User user, UserFormDTO userFormDTO) throws UsernameNotFoundException {
        logger.info("Get User by username: {}", user);

        String encryptedPassword = passwordEncoder.encode(user.getPassword());

        BankAccount bankAccount = new BankAccount();
        bankAccount.setFirstname(user.getFirstname());
        bankAccount.setLastname(user.getLastname());
        bankAccount.setBankAccountNumber(userFormDTO.getBankaccountnumber());
        this.bankAccountRepository.save(bankAccount);

        user.setPassword(encryptedPassword);
        user.setUsername(user.getEmail());
        user.setAccount(bankAccount);
        user.setRole("user");
        this.userRepository.save(user);
    }

    public void addConnection(CustomUserDetails customUserDetails, int id) throws UsernameNotFoundException {
        logger.info("Get User by username: {}", customUserDetails.getUsername());

        User connection = this.userRepository.getById(id);

        User user = this.userRepository.findByUsername(customUserDetails.getUsername());

        user.getConnections().add(connection);

        this.userRepository.save(user);

    }

    public String registration(UserFormDTO userFormDTO, BindingResult bindingResult) {

        if (this.userRepository.existsByEmail(userFormDTO.getEmail())) {
            bindingResult.rejectValue("email", "", "This email already exists");
            return "registration";
        }

        if (!userFormDTO.getPassword().equals(userFormDTO.getPasswordconfirm())) {
            bindingResult.rejectValue("password", "", "Cannot confirm password");
            return "registration";
        }

        User user1 = convertToEntity(userFormDTO);
        user1.getAccount().setBalance(10000);

        this.saveUser(user1, userFormDTO);

        return "redirect:/";
    }

    /**
     * Search by username (user email) keyword
     * @param keyword user email
     * @return user
     */
    public User search(@NotBlank String keyword) {
        return this.userRepository.findByUsername(keyword);
    }

    /**
     * This method converts a DTO object to an Entity
     */
    private User convertToEntity(UserFormDTO userFormDTO) {
        return modelMapper.map(userFormDTO, User.class);
    }


}
