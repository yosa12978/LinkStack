package com.IDS.LinkStack.domain;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @URL
    @NotNull
    private String Url;
    @Temporal(TemporalType.TIMESTAMP)
    private Date pubDate;
    private String title;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Link(){}
    public Link(String Url, Date pubDate, String title, User user){
        this.Url = Url;
        this.pubDate = pubDate;
        this.title = title;
        this.user = user;
    }

    public Long getId() {
        return id;
    }
    public Date getPubDate() {
        return pubDate;
    }
    public String getTitle() {
        return title;
    }
    public User getUser() {
        return user;
    }
    public String getUrl() {
        return Url;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setUrl(String url) {
        Url = url;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
