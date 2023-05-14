package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.payload.CommentDto;


public interface CommentService {

	CommentDto createComment(long postId, CommentDto commentDto);
	
	List<CommentDto> getCommentByPostId(long postId);
	CommentDto getCommentById(long postId, long commentId);
	
	void deleteComment(Long postId, Long commentId);
	
	CommentDto upDateComment(Long postId, Long commentId, CommentDto request);
}
