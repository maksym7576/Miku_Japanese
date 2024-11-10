package com.japanese.lessons.dtos;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class TypeObjectDTO {

    private Long objectId;
    private String objectType;
}
