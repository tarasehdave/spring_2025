package com.nighthawk.spring_portfolio.mvc.forum.forumBlogs;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data // Lombok annotation to generate boilerplate code (getters, setters, toString, etc.)
@Entity // JPA annotation to specify that this class is an entity
@NoArgsConstructor // Lombok annotation to generate a no-argument constructor
@AllArgsConstructor // Lombok annotation to generate an all-arguments constructor
@Getter // Lombok annotation to generate getters
@Setter // Lombok annotation to generate setters
public class forumBlogs {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // Primary key for the userStocksTable entity

    @Column(nullable=false)
    private String author; // Name of the associated person

    @Column(nullable=false)
    private String title; // Title of the post

    @Column(nullable=false)
    private String filePath; // Context of the post

    @Column(nullable=false)
    private String date; // Date of the post

    public forumBlogs(String author, String title, String filePath, String date) {
        this.author = author;
        this.title = title;
        this.filePath = filePath;
        this.date = date;
    }

    public String echoMessage(String message) {
        return message;
    }
}
