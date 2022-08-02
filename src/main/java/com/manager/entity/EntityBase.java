package com.manager.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class EntityBase {


    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "created_user")
    private String createdUser;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}
