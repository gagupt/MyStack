package com.stack.repositories.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stack.entities.Comment;
import com.stack.entities.Post;
import com.stack.entities.User;
import com.stack.enums.PostType;
import com.stack.enums.Tag;
import com.stack.repositories.PostSearchRepository;

@Repository
public class PostSearchRepositoryImpl implements PostSearchRepository {

	private RestHighLevelClient client;
	private static String INDEX_NAME_USER = "user";
	private static String INDEX_TYPE_USER = "_doc";
	private static String NAME = "name";
	private static String CREATED_AT = "createdAt";

	private static String INDEX_NAME_POST = "post";
	private static String INDEX_TYPE_POST = "posts";
	private static String TAGS = "tags";
	private static String POST_TYPE = "post_type";
	private static String CREATER_ID = "creater_id";
	private static String DESCRPTION = "descrption";
	private static String HEADING = "heading";

	private static ObjectMapper MAPPER = new ObjectMapper();
	private static TypeReference<List<Tag>> typeRefTag = new TypeReference<List<Tag>>() {
	};

	@Autowired
	public PostSearchRepositoryImpl(RestHighLevelClient restHighLevelClient) {
		this.client = restHighLevelClient;
	}

	XContentBuilder getUserBuilder(User user) {
		XContentBuilder builder = null;
		try {
			builder = XContentFactory.jsonBuilder();
			builder.startObject();
			{
				builder.field(NAME, user.getName());
				builder.field(CREATED_AT, System.currentTimeMillis());
			}
			builder.endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return builder;
	}

	XContentBuilder getPostBuilder(Post post) {
		XContentBuilder builder = null;
		try {
			builder = XContentFactory.jsonBuilder();
			builder.startObject();
			{
				builder.field(DESCRPTION, post.getDescrption());
				builder.field(TAGS, post.getTags());
				builder.field(POST_TYPE, post.getPostType());
				builder.field(CREATER_ID, post.getCreaterId());
				builder.field(HEADING, post.getHeading());
				builder.field(CREATED_AT, System.currentTimeMillis());
			}
			builder.endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return builder;
	}

	@Override
	public boolean createUser(User user) {
		boolean status = false;
		IndexRequest indexRequest = new IndexRequest(INDEX_NAME_USER, INDEX_TYPE_USER, user.getUserId())
				.source(getUserBuilder(user));
		try {
			IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
			if (indexResponse.status() == RestStatus.OK || indexResponse.status() == RestStatus.CREATED) {
				status = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return status;
	}

	@Override
	public User getUserDetails(String userId) {
		User user = null;
		GetRequest getRequest = new GetRequest(INDEX_NAME_USER, INDEX_TYPE_USER, userId);
		try {
			GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);
			if (response.isExists()) {
				user = new User();
				user.setUserId(userId);
				Map<String, Object> userMap = response.getSourceAsMap();
				if (userMap != null && userMap.get(NAME) != null) {
					user.setName(userMap.get(NAME).toString());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return user;
	}

	private Post getPostFromMap(Map<String, Object> source) {
		Post post = new Post();
		if (source.get(DESCRPTION) != null) {
			post.setDescrption(source.get(DESCRPTION).toString());
		}
		if (source.get(CREATED_AT) != null) {
			post.setTimestamp(Long.valueOf(source.get(CREATED_AT).toString()));
		}
		if (source.get(TAGS) != null) {
			post.setTags(MAPPER.convertValue(source.get(TAGS), typeRefTag));
		}
		if (source.get(POST_TYPE) != null) {
			post.setPostType(PostType.valueOf(source.get(POST_TYPE).toString()));
		}
		if (source.get(CREATER_ID) != null) {
			post.setCreaterId(source.get(CREATER_ID).toString());
		}
		if (source.get(HEADING) != null) {
			post.setHeading(source.get(HEADING).toString());
		}
		return post;
	}

	@Override
	public Post getPostById(String postId) {
		Post post = null;
		GetRequest request = new GetRequest(INDEX_NAME_POST, INDEX_TYPE_POST, postId);
		try {
			GetResponse response = client.get(request, RequestOptions.DEFAULT);
			if (response.isExists()) {
				post = getPostFromMap(response.getSourceAsMap());
				post.setPostId(postId);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return post;
	}

	@Override
	public List<Post> getPostsByUserId(String userId) {
		List<Post> posts = new ArrayList<>();

		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder qBuilder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery(CREATER_ID, userId));
		searchSourceBuilder.query(qBuilder);
		searchRequest.source(searchSourceBuilder);

		try {
			SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
			SearchHits hits = response.getHits();
			SearchHit[] searchHits = hits.getHits();
			for (SearchHit hit : searchHits) {
				Post post = getPostFromMap(hit.getSourceAsMap());
				post.setPostId(hit.getId());
				posts.add(post);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return posts;
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
	public boolean createPost(Post post) {
		boolean status = false;
		IndexRequest indexRequest = new IndexRequest(INDEX_NAME_POST, INDEX_TYPE_POST, post.getPostId())
				.source(getPostBuilder(post));
		try {
			IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
			if (indexResponse.status() == RestStatus.OK || indexResponse.status() == RestStatus.CREATED) {
				status = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return status;
	}
}
