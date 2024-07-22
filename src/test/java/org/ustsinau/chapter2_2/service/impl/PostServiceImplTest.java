package org.ustsinau.chapter2_2.service.impl;

import org.ustsinau.chapter2_2.models.Post;
import org.ustsinau.chapter2_2.models.PostStatus;
import org.ustsinau.chapter2_2.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private  PostServiceImpl postService;
    private long id;
    private Post post;
    private String content;
    private Date created;

    private Date updated;
    PostStatus postStatus;

    @BeforeEach
    void setUp() {
        id = 1L;
        content = "Test Content";
        postStatus = PostStatus.ACTIVE;
        created = new Date();
        updated = new Date();
        post = new Post(id, content, postStatus, created, List.of());
    }

    @Test
    void createPost() {
        when(postRepository.create(any(Post.class))).thenReturn(post);

        Post result = postService.createPost(content, postStatus, created);

        assertEquals(post, result);
        verify(postRepository, times(1)).create(any(Post.class));
    }

    @Test
    void updatePost() {
        when(postRepository.update(any(Post.class))).thenReturn(post);

        Post result = postService.updatePost(id, content, updated, List.of(), postStatus);

        assertEquals(post, result);
        verify(postRepository, times(1)).update(any(Post.class));
    }

    @Test
    void deletePost() {
        doNothing().when(postRepository).delete(id);

        postService.deletePost(id);

        verify(postRepository, times(1)).delete(id);
    }

    @Test
    void getPostById() {
        when(postRepository.getById(id)).thenReturn(post);

        Post result = postService.getPostById(id);

        assertEquals(post, result);
        verify(postRepository, times(1)).getById(id);
    }

    @Test
    void getAllPosts() {
        List<Post> posts = List.of(post);
        when(postRepository.getAll()).thenReturn(posts);

        List<Post> result = postService.getAllPosts();

        assertEquals(posts, result);
        verify(postRepository, times(1)).getAll();
    }
}