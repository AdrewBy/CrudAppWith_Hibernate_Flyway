package org.ustsinau.chapter2_2.service;

import org.ustsinau.chapter2_2.models.Label;
import org.ustsinau.chapter2_2.models.Post;
import org.ustsinau.chapter2_2.models.PostStatus;

import java.util.Date;
import java.util.List;

public interface PostService {
    Post createPost(String content, PostStatus postStatus, Date created);
    Post updatePost(long id, String content, Date updated, List<Label> labels, PostStatus postStatus);
    void deletePost(long id);
    Post getPostById(long id);
    List<Post> getAllPosts();
}
