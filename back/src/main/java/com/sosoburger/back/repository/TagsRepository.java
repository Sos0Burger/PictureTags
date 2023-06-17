package com.sosoburger.back.repository;

import com.sosoburger.back.dao.TagDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsRepository extends JpaRepository<TagDAO, Integer> {
}
