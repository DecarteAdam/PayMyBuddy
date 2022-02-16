package com.pay_my_buddy.PayMyBuddy.service;

import com.pay_my_buddy.PayMyBuddy.DTO.UserDTO;

public class SendMoneyForm {

    private Long fromAccountId;

    public UserDTO getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(UserDTO userFrom) {
        this.userFrom = userFrom;
    }

    public UserDTO getUserTo() {
        return userTo;
    }

    public void setUserTo(UserDTO userTo) {
        this.userTo = userTo;
    }

    private UserDTO userFrom;
    private Long toAccountId;
    private UserDTO userTo;
    private Double amount;

    public SendMoneyForm() {

    }

    public SendMoneyForm(Long fromAccountId, Long toAccountId, Double amount) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
