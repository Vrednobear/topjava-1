package ru.javawebinar.topjava.model;

import org.springframework.util.Assert;

public abstract class AbstractBaseEntity {

    public static final int START_SEQ = 100000;

    protected Integer id;

    protected AbstractBaseEntity(Integer id) {
        this.id = id;
    }

    public AbstractBaseEntity() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getIdNotNull() {
        Assert.notNull(id, "Entity must have id");
        return id;
    }

    public boolean isNew() {
        return this.id == null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id;
    }
}