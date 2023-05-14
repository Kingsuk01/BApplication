package com.springboot.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

	private CommentService commentService;

	public CommentController(com.springboot.blog.service.CommentService commentService) {
		this.commentService = commentService;
	}
	
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable Long postId , @RequestBody CommentDto commentDto){
		
		
		return new ResponseEntity<>(commentService.createComment(postId,commentDto) , HttpStatus.CREATED);
	}
	
	@GetMapping("/post/{postId}/comments")
	public ResponseEntity<List<CommentDto>> getListOfCommentsByPostId(@PathVariable Long postId){
		List <CommentDto> cdto = commentService.getCommentByPostId(postId);
		
		return new ResponseEntity<>(cdto,HttpStatus.OK);
		
	}
	
	@PutMapping("/post/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateCommentByID(@PathVariable(value = "postId") Long postId,  @PathVariable(value = "id") Long commentId, @RequestBody CommentDto commentDto){
		
		CommentDto commentresponse =  commentService.getCommentById(postId,commentId);
		 
		 
		return new ResponseEntity<>(commentresponse , HttpStatus.OK);
		
		}
	


	@GetMapping("/post/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> getCommentByID(@PathVariable(value = "postId") Long postId,  @PathVariable(value = "id") Long commentId){
		
		CommentDto commentresponse =  commentService.getCommentById(postId,commentId);
		 
		 
		return new ResponseEntity<>(commentresponse , HttpStatus.OK)	;}
	
	
	@DeleteMapping("/post/{postId}/comments/{id}")
	public ResponseEntity<String> deleteCommentByID(@PathVariable(value = "postId") Long postId,  @PathVariable(value = "id") Long commentId){
		
		 commentService.deleteComment(postId, commentId);
		
		return new ResponseEntity<>("Comment Successfully deleted" , HttpStatus.OK)	;}

}
