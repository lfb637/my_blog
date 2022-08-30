package com.lfb.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lfb
 * @data 2022/8/19 6:00
 */
@Entity
@Table(name="t_type")
public class Type {
    @Id
    @GeneratedValue
    private Long id;
//    @NotBlank(message="名称不能为空")
    private String name;
    public Type() {
    }
    @OneToMany(mappedBy="type")
    private List<Blog> blogs = new ArrayList<>();

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
