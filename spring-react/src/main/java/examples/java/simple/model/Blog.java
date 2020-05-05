package examples.java.simple.model;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Blog {
    private @Id @GeneratedValue Long id;
    private String title;
    private String content;
    private LocalDateTime createTimeStamp;
    @ElementCollection
    private Set<String> tags;

    public Blog() {
    }

    public Blog(String title, String content, LocalDateTime createTimeStamp, Set<String> tags) {
        this.title = title;
        this.content = content;
        this.createTimeStamp = createTimeStamp;
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blog blog = (Blog) o;
        return Objects.equals(id, blog.id) &&
                Objects.equals(title, blog.title) &&
                Objects.equals(content, blog.content) &&
                Objects.equals(createTimeStamp, blog.createTimeStamp) &&
                Objects.equals(tags, blog.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, createTimeStamp, tags);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateTimeStamp() {
        return createTimeStamp;
    }

    public void setCreateTimeStamp(LocalDateTime createTimeStamp) {
        this.createTimeStamp = createTimeStamp;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public static Set<String> addTags(String...tagList){
        Set<String> tags = new HashSet<>();
        Collections.addAll(tags, tagList);
        return tags;
    }
}
