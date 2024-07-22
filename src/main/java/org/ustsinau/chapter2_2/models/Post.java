package org.ustsinau.chapter2_2.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private Writer writer;

    @Column(name = "content")
    private String content;

    @ManyToMany
    @JoinTable(
            name = "post_labels",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    private List<Label> labels;

    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @Enumerated(EnumType.STRING)
    @Column(name = "postStatus")
    private PostStatus postStatus;

    public Post() {
    }

    public Post(String content, PostStatus postStatus, Date created) {
        this.content = content;
        this.postStatus = postStatus;
        this.created = created;
    }

    public Post(long id, String content, PostStatus postStatus, Date updated, List<Label> labels) {
        this.id = id;
        this.content = content;
        this.postStatus = postStatus;
        this.updated = updated;
        this.labels = labels;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public PostStatus getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(PostStatus postStatus) {
        this.postStatus = postStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id &&
                Objects.equals(content, post.content) &&
                Objects.equals(labels, post.labels) &&
                Objects.equals(created, post.created) &&
                Objects.equals(updated, post.updated) &&
                postStatus == post.postStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, labels, created, updated, postStatus);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", postStatus=" + postStatus +
                ", labels=" + labels +
                '}';
    }
}
