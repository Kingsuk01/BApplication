package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private CommentRepository commentRespository;
	 
	private PostRepository postRepository;
	
	private ModelMapper mapper;
	
	
	public CommentServiceImpl(CommentRepository commentRespository , PostRepository postRepository, ModelMapper mapper) {
		super();
		this.commentRespository = commentRespository;
		this.postRepository = postRepository;
		this.mapper = mapper;
	}

    private CommentDto mapTOCommentDto(Comment comment) {
    	
    	CommentDto cDto = mapper.map(comment, CommentDto.class);
    	return cDto;
    }
    
private Comment mapTOComment(CommentDto comment) {
    	
    	Comment comments = mapper.map(comment,Comment.class);
    	
    	return comments;
    }

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		
		Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		
		Comment comment = mapTOComment(commentDto);
		
		comment.setPost(post);
		
		commentRespository.save(comment);
		
		CommentDto cDto = mapTOCommentDto(comment);
		
		return cDto;
	}

	@Override
	public List<CommentDto> getCommentByPostId(long postId) {
		// TODO Auto-generated method stub
		
		List<Comment> comment = commentRespository.findByPostId(postId);
		
		List<CommentDto> commenDto = comment.stream().map(i ->mapTOCommentDto(i)).collect(Collectors.toList()); 
				
				
		return commenDto;
	}

	@Override
	public CommentDto getCommentById(long postId, long commentId) {
		// TODO Auto-generated method stub
		
		Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		
		Comment comment = commentRespository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));
		
		if(!comment.getPost().getId().equals(post.getId())) {
		   throw new BlogAPIException(HttpStatus.BAD_REQUEST ,"Comment does not belong to post");
		}
		return mapTOCommentDto(comment);
	}

	@Override
	public void deleteComment(Long postId, Long commentId) {
		// TODO Auto-generated method stub
		
Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		
		Comment comment = commentRespository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));
		
		if(!comment.getPost().getId().equals(post.getId())) {
			   throw new BlogAPIException(HttpStatus.BAD_REQUEST ,"Comment does not belong to post");
			}
		commentRespository.delete(comment);
	}

	@Override
	public CommentDto upDateComment(Long postId, Long commentId, CommentDto request) {
		// TODO Auto-generated method stub
		
		Post post = postRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		
		Comment comment = commentRespository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));
		
		if(!comment.getPost().getId().equals(post.getId())) {
			   throw new BlogAPIException(HttpStatus.BAD_REQUEST ,"Comment does not belong to post");
			}
		
		comment.setBody(request.getBody());
		comment.setEmail(request.getEmail());
		comment.setName(request.getName());
		
		Comment updatedComment = commentRespository.save(comment);
		

		return mapTOCommentDto(updatedComment);
	}
	
	

}
