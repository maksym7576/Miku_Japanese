package com.japanese.lessons.dtos;

import com.japanese.lessons.models.ETargetType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Embeddable
@Data
public class TypeObjectDTO {

    private Long objectId;
    @Enumerated(EnumType.STRING)
    @Column(name = "object_type")
    private ETargetType objectType;
}
