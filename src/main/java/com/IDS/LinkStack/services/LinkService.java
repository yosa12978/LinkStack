package com.IDS.LinkStack.services;

import com.IDS.LinkStack.domain.Link;
import com.IDS.LinkStack.domain.User;
import com.IDS.LinkStack.repos.LinkRepository;
import com.IDS.LinkStack.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LinkService {
    private final LinkRepository linkRepository;
    private final UserRepository userRepository;

    public LinkService(LinkRepository linkRepository, UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
    }

    public Iterable<Link> GetAll(String username){
        return linkRepository.findByUserUsernameOrderByIdDesc(username);
    }

    public void saveLink(Link link, String username){
        link.setUser(userRepository.findByUsername(username));
        link.setPubDate(new Date());
        linkRepository.save(link);
    }

    public boolean deleteLink(Long id, String username){
        Link link = linkRepository.findByUserUsernameAndId(username, id);
        if(link == null)
            return false;
        linkRepository.delete(link);
        return true;
    }
}
