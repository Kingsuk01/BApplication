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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	
	private PostService postService;

	public PostController(PostService postService) {
		super();
		this.postService = postService;
	}
	
	@PostMapping
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
		
		PostDto newPost = postService.createPost(postDto);
		return new ResponseEntity<>(newPost, HttpStatus.CREATED);
	}
	
	@GetMapping
	public PostResponse getAllPosts(
			@RequestParam(value = "pageNo" , defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize" , defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy" , defaultValue = AppConstants.DEFAULT_SORT_BY, required = false)String sortBy,
			@RequestParam(value = "sortDir" , defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false)String sortDir
			){
		 
	  return postService.getAllPosts(pageNo , pageSize,sortBy,sortDir );
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
		
		PostDto post = postService.getPostById(id);
		return new ResponseEntity<>(post, HttpStatus.OK);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto , @PathVariable Long id){
		
		return new ResponseEntity<>(postService.updatePost(postDto , id), HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable Long id){
		
		postService.deletePostById(id);
		return new ResponseEntity<>("Post Deleted successfully", HttpStatus.OK);
		
	}
}
