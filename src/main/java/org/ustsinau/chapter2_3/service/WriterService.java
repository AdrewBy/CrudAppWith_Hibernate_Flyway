package org.ustsinau.chapter2_3.service;

import org.ustsinau.chapter2_3.models.Writer;
import org.ustsinau.chapter2_3.models.Post;

import java.util.List;

public interface WriterService {
    Writer createWriter(String firstName, String lastName);
    Writer updateWriter(long id, String firstName, String lastName, List<Post> posts);
    void deleteWriter(long id);
    Writer getWriterById(long id);
    List<Writer> getAllWriters();
}
