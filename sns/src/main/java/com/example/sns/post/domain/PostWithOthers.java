package com.example.sns.post.domain;

import com.example.sns.post.domain.entity.Comment;
import com.example.sns.post.domain.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PostWithOthers {
	
	private Post post;
	private List<Comment> commentList;
	private boolean isLike;
	private int likeCount;
	private String writerProfileImage;
	
	@Builder
	public PostWithOthers(Post post, List<Comment> commentList, boolean isLike, int likeCount,
						  String writerProfileImage) {
		this.post = post;
		this.commentList = commentList;
		this.isLike = isLike;
		this.likeCount = likeCount;
		this.writerProfileImage = writerProfileImage;
	}
	
	

}
