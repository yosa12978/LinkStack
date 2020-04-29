package com.IDS.LinkStack.services;

import com.IDS.LinkStack.domain.Link;
import com.IDS.LinkStack.repos.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkService {
    @Autowired
    private LinkRepository linkRepository;

    public List<Link> GetAll(){
        return linkRepository.findByOrderByIdDesc();
    }
}
