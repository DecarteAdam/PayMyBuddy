package com.pay_my_buddy.PayMyBuddy.data;

import com.pay_my_buddy.PayMyBuddy.model.BankAccount;
import com.pay_my_buddy.PayMyBuddy.model.Transaction;
import com.pay_my_buddy.PayMyBuddy.service.BankTransactionException;
import com.pay_my_buddy.PayMyBuddy.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
public class BankAccountDAO {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TransactionService transactionService;


    public BankAccountDAO() {
    }

    public BankAccount findById(int id) {
        return this.entityManager.find(BankAccount.class, id);
    }

   /* public List<BankAccount> listBankAccountInfo() {
        String sql = "Select new " + BankAccount.class.getName() //
                + "(e.id,e.fullName,e.balance) " //
                + " from " + BankAccount.class.getName() + " e ";
        Query query = entityManager.createQuery(sql, BankAccount.class);
        return query.getResultList();
    }*/

    // MANDATORY: Transaction must be created before.
    @Transactional(propagation = Propagation.MANDATORY )
    public void addAmount(int id, double amount) throws BankTransactionException {
        BankAccount account = this.findById(id);
        if (account == null) {
            throw new BankTransactionException("Account not found " + id);
        }
        double newBalance = account.getBalance() + amount;
        if (account.getBalance() + amount < 0) {
            throw new BankTransactionException(
                    "The money in the account '" + id + "' is not enough (" + account.getBalance() + ")");
        }
        account.setBalance(newBalance);


    }

    // Do not catch BankTransactionException in this method.
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = BankTransactionException.class)
    public void sendMoney(int fromAccountId, int toAccountId, double amount, String user) throws BankTransactionException {

        addAmount(toAccountId, amount);
        addAmount(fromAccountId, -amount);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setUserId(fromAccountId);
        transaction.setDescription("Transaction");
        transaction.setConnection(user);
        transactionService.save(transaction);
    }

}
