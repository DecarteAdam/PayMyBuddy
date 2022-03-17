package com.pay_my_buddy.PayMyBuddy.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * DTO object that contains the informations from form page for a user registration.
 * @author jerome
 *
 */

@Getter
@Setter
public class UserFormDTO {

	@NotBlank
	private String firstname;
	@NotBlank
	private String lastname;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String password;
	@NotBlank
	private String passwordconfirm;
	@NotBlank
	private String bankaccountnumber;
	
	
}
