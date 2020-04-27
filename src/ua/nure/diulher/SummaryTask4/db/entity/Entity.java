package ua.nure.diulher.SummaryTask4.db.entity;

import java.io.Serializable;

public abstract class Entity implements Serializable {

    private static final long serialVersionUID = 8466257864408346236L;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
