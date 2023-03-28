package com.security.Repositories;



import com.security.Models.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepo extends JpaRepository<UserToken, Long> {

    @Query(value = "select * from xtoken t inner join xuser u on t.user_id = u.userid where u.userid = :id and (t.expired = 0 or t.revoked = 0)",
            nativeQuery = true
    )
    List<UserToken> findAllValidTokenByUser (Long id);

    Optional<UserToken> findByToken(String token);

}
