package com.IDS.LinkStack.repos;

import com.IDS.LinkStack.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepository extends JpaRepository<Link, Long> {
    public List<Link> findByOrderByIdDesc();
}
