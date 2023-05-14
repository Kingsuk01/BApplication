package com.springboot.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.entity.Comment;

// Simple Data repository call implements JpaRepostory, which already have @Repository annotation so we don`t need
// @Respository annotation here. same for @Transactional

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByPostId(long postID);
	
	
	
}
