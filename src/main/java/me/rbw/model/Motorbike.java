package me.rbw.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Motorbike {

    @Id
    private String id;
    private String name;
    @Parent()
    private Key<User> owner;

    private Motorbike() {
    }

    public Motorbike(String ownerId) {
        owner = Key.create(User.class, ownerId);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
