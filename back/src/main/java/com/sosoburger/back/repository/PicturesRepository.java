package com.sosoburger.back.repository;

import com.sosoburger.back.dao.PictureDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PicturesRepository extends JpaRepository<PictureDAO, Integer> {
}
