package com.IDS.LinkStack.repos;

import com.IDS.LinkStack.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LinkRepository extends JpaRepository<Link, Long> {
    public Iterable<Link> findByUserUsernameOrderByIdDesc(String username);
    Link findByUserUsernameAndId(String username, Long id);
}
