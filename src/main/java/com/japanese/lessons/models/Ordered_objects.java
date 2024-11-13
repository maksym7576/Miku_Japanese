package com.japanese.lessons.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ordered_objects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "parent_type", nullable = false)
    private ETargetType parentObjectType;

    @Column
    private Long objectId;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false)
    private ETargetType targetType;

    @Column
    private Long parentObjectId;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;
}
