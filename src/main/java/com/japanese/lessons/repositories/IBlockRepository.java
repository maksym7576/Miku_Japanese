package com.japanese.lessons.repositories;

import com.japanese.lessons.models.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBlockRepository extends JpaRepository<Block, Long> {
}
