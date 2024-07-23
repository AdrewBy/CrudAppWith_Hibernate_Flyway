package org.ustsinau.chapter2_3.controller;

import org.ustsinau.chapter2_3.models.Label;
import org.ustsinau.chapter2_3.models.Post;
import org.ustsinau.chapter2_3.models.PostStatus;
import org.ustsinau.chapter2_3.service.PostService;
import org.ustsinau.chapter2_3.service.impl.PostServiceImpl;

import java.util.*;

public class PostController {

    private final PostService posts = new PostServiceImpl();


    public void createPost(String content, PostStatus postStatus, Date created) {
        posts.createPost(content, postStatus, created);
    }

    public void updatePost(long id, String content, Date updated, List<Label> labels, PostStatus postStatus) {
        posts.updatePost(id, content, updated, labels, postStatus);
    }

    public void deletePost(long id) {
        posts.deletePost(id);
    }

    public Post getPostById(long id) {
        return posts.getPostById(id);
    }

    public List<Post> showAll() {
        return posts.getAllPosts();
    }

}
