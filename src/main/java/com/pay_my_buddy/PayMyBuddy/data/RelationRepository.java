package com.pay_my_buddy.PayMyBuddy.data;

import com.pay_my_buddy.PayMyBuddy.model.Connection;
import com.pay_my_buddy.PayMyBuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface RelationRepository extends JpaRepository<Connection, Integer> {

    List<Connection> findConnectionByUser1(@NotNull User user);
}
