package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;
	
	private ModelMapper mapper;
	
	public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
		this.postRepository = postRepository;
		this.mapper = mapper;
	}
	
	
	@Override
	public PostDto createPost(PostDto postDto) {
		Post pst =postDtoToPost(postDto);
		
		Post newPost = postRepository.save(pst);
		PostDto postResponse =postToPostDto(newPost);		
		
		return postResponse;
	}
	
	private PostDto postToPostDto(Post newPost){
		
		PostDto postDto = mapper.map(newPost, PostDto.class);
		
		return postDto;
		
	}
	private Post postDtoToPost(PostDto postDto) {
		
		Post post = mapper.map(postDto, Post.class);
		return post;
	}

	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir){
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():
			Sort.by(sortBy).descending();
		
		
		// create Pageable instance
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		
		Page<Post> posts = postRepository.findAll(pageable);
		
		
		//get content for page object
		List<Post> listOfPost = posts.getContent();
		
		List< PostDto> content = listOfPost.stream().map(post->postToPostDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());
		
		
		return postResponse;
		
	}


	@Override
	public PostDto getPostById(Long id) {
		   Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post" , "id" , id));
		   return postToPostDto(post);
		
		
	}


	@Override
	public PostDto updatePost(PostDto postDto, Long id) {
		Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post" , "id" , id));
		Post newPost = postDtoToPost(postDto);
		postRepository.save(newPost);
		return postDto;
	}


	@Override
	public void deletePostById(Long id) {
		Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post" , "id" , id));
		postRepository.delete(post);
	}
}
