package com.dogadopt.dog_adopt.repository;

import com.dogadopt.dog_adopt.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
