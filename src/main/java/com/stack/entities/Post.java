package com.stack.entities;

import java.util.List;

import com.stack.enums.PostType;
import com.stack.enums.Tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
	private String postId;
	private long timestamp;
	private List<Tag> tags;
	private PostType postType;
	private String createrId;
	private String descrption;
	private String heading;
}
