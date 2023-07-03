package org.jpa.chapter2;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "MEMBER")
public class Member {

    @Id
    @Column(name = "ID")
    private String id; //식별자 필드
    @Column(name = "NAME")
    private String username;
    @Column(name = "AGE")
    private Integer age;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


}
