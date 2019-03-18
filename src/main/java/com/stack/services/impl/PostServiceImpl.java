package com.stack.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stack.entities.Comment;
import com.stack.entities.Post;
import com.stack.entities.User;
import com.stack.enums.PostType;
import com.stack.enums.Tag;
import com.stack.repositories.PostSearchRepository;
import com.stack.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	private PostSearchRepository postSearchRepository;

	@Autowired
	public PostServiceImpl(PostSearchRepository postSearchRepository) {
		this.postSearchRepository = postSearchRepository;
	}

	@Override
	public Post getPostById(String postId) {
		return postSearchRepository.getPostById(postId);
	}

	@Override
	public List<Post> getPostsByUserId(String userId) {
		return postSearchRepository.getPostsByUserId(userId);
	}

	@Override
	public List<Post> getPostByTag(List<Tag> tags) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> getPostByType(PostType postType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comment> getUserComments(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment getCommentByd(String commentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> searchPostByText(String text) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> getAllPostLimitByCount(int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserDetails(String userId) {
		return postSearchRepository.getUserDetails(userId);
	}

	@Override
	public boolean createUser(User user) {
		return postSearchRepository.createUser(user);
	}

	@Override
	public boolean createPost(Post post) {
		return postSearchRepository.createPost(post);
	}

}
