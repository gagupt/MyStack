package com.stack.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.stack.entities.Post;
import com.stack.entities.User;
import com.stack.services.PostService;

@RestController
public class PostController {

	PostService postService;

	@Autowired
	public PostController(PostService postService) {
		this.postService = postService;
	}

	@RequestMapping(value = { "stack/post" }, method = RequestMethod.GET)
	public @ResponseBody Post getPost(@RequestParam String postId) {
		return postService.getPostById(postId);
	}

	@RequestMapping(value = { "create/post" }, method = RequestMethod.POST)
	public boolean createPost(@RequestBody Post post) {
		return postService.createPost(post);
	}

	@RequestMapping(value = { "create/user" }, method = RequestMethod.POST)
	public boolean createUser(@RequestBody User user) {
		return postService.createUser(user);
	}

	@RequestMapping(value = { "stack/user" }, method = RequestMethod.GET)
	public @ResponseBody User getUser(@RequestParam String userId) {
		return postService.getUserDetails(userId);
	}

	@RequestMapping(value = { "stack/post/userId" }, method = RequestMethod.GET)
	public @ResponseBody List<Post> getPostByUserId(@RequestParam String userId) {
		return postService.getPostsByUserId(userId);
	}
}
