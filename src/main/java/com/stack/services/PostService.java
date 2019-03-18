package com.stack.services;

import java.util.List;

import com.stack.entities.Comment;
import com.stack.entities.Post;
import com.stack.entities.User;
import com.stack.enums.PostType;
import com.stack.enums.Tag;

public interface PostService {
	Post getPostById(String postId);

	List<Post> getPostsByUserId(String userId);

	List<Post> getPostByTag(List<Tag> tags);

	List<Post> getPostByType(PostType postType);

	List<Comment> getUserComments(String userId);

	Comment getCommentByd(String commentId);

	List<Post> searchPostByText(String text);

	List<Post> getAllPostLimitByCount(int count);

	User getUserDetails(String userId);

	boolean createUser(User user);

	boolean createPost(Post post);
}
