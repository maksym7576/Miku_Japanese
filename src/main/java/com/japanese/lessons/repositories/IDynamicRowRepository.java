package com.japanese.lessons.repositories;

import com.japanese.lessons.models.fifth.DynamicRow;
import com.japanese.lessons.models.sixsth.Text;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IDynamicRowRepository extends CrudRepository<DynamicRow, Long> {
    List<DynamicRow> findAllById(Iterable<Long> ids);
}
