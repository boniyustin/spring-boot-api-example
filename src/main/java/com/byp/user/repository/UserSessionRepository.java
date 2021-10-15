package com.byp.user.repository;

import com.byp.user.entity.UserSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// todo byp: looks like it's not needed
@Repository
public interface UserSessionRepository extends CrudRepository<UserSession, Long> {
}
