package wz.dao;

import java.util.List;

import wz.bean.Post;

public interface PostsDao {

	/**
	 * 添加一个帖子至数据库
	 * @param post
	 */
	public void addPost(Post post);

	/**
	 * 添加多个帖子至数据库
	 * @param posts
	 */
	public void addPosts(List<Post> posts);

	/**
	 * 获取多个帖子
	 * @return 多个帖子
	 */
	public List<Post> getPosts();
}
