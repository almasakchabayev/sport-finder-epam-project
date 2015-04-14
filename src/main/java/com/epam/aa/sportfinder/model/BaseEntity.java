package com.epam.aa.sportfinder.model;

import java.util.UUID;

public class BaseEntity {
    private Integer id;
    private UUID uuid;
    private boolean deleted;

    public BaseEntity() {
        this.uuid = UUID.randomUUID();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
