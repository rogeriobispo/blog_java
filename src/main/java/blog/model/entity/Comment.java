package blog.model.entity;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Entity

public class Comment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String texto;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date data;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @NotNull
    private Long thumbsUp;

    @NotNull
    private Long thumbsDown;

    public Comment() {
    }

    public Comment(Comment c) {
        this(c.getPost(), c.getTexto());
    }

    public Comment(Post post, String texto) {
        this.texto = texto;
        this.post = post;
    }

    public void addThumbsUp(){
        this.thumbsUp = ++this.thumbsUp;
    }
    public void addThumbsDown(){
        this.thumbsDown = ++this.thumbsDown;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Long getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(Long thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public Long getThumbsDown() {
        return thumbsDown;
    }

    public void setThumbsDown(Long thumbsDown) {
        this.thumbsDown = thumbsDown;
    }
}
