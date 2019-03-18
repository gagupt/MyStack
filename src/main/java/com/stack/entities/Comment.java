package com.stack.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
	private String commentId;
	private String createrId;
	private String text;
	private long timestamp;

}
