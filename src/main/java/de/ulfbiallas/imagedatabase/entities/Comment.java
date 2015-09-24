package de.ulfbiallas.imagedatabase.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import de.ulfbiallas.imagedatabase.tools.CommentSerializer;

@Entity
@JsonSerialize(using=CommentSerializer.class)
public class Comment {

    @Id
    @Column
    private String id;

    @Column
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date time;

    @ManyToOne
    private Account author;

    @ManyToOne
    private ImageRecord subject;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    public ImageRecord getSubject() {
        return subject;
    }

    public void setSubject(ImageRecord subject) {
        this.subject = subject;
    }

    public String toString() {
        String str = "";
        str += "Content: " + content + "\n";
        return str;
    }

}
