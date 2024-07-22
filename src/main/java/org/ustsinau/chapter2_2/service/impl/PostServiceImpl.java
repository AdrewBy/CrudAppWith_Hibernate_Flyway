package org.ustsinau.chapter2_2.service.impl;

import org.ustsinau.chapter2_2.models.Label;
import org.ustsinau.chapter2_2.models.Post;
import org.ustsinau.chapter2_2.models.PostStatus;
import org.ustsinau.chapter2_2.repository.PostRepository;
import org.ustsinau.chapter2_2.repository.impl.JdbcPostRepositoryImpl;
import org.ustsinau.chapter2_2.service.PostService;

import java.util.Date;
import java.util.List;

public class PostServiceImpl implements PostService {
    private final PostRepository postRepository ;

    // для внедрения зависимотей
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository =  postRepository;
    }

    public PostServiceImpl() {
        this.postRepository =  new JdbcPostRepositoryImpl();
    }

    @Override
    public Post createPost(String content, PostStatus postStatus, Date created) {
        Post post = new Post(content, postStatus, created);
       return postRepository.create(post);
    }

    @Override
    public Post updatePost(long id, String content, Date updated, List<Label> labels, PostStatus postStatus) {
        Post pst = new Post(id, content, postStatus, updated, labels);
      return   postRepository.update(pst);
    }

    @Override
    public void deletePost(long id) {
        postRepository.delete(id);
    }

    @Override
    public Post getPostById(long id) {
        return postRepository.getById(id);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.getAll();
    }
}
