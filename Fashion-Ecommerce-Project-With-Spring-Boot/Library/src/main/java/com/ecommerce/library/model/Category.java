package com.ecommerce.library.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "name", columnDefinition = "nvarchar")
    private String name;

    @Column(name = "is_delete")
    private boolean isDeleted;

    @Column(name = "is_activated")
    private boolean isActivated;

    private String image;

    public Category(String name){
        this.name = name;
        this.isActivated = true;
        this.isDeleted = false;

    }
}
