package org.ustsinau.chapter2_3.controller;

import org.ustsinau.chapter2_3.models.Post;
import org.ustsinau.chapter2_3.models.Writer;
import org.ustsinau.chapter2_3.service.WriterService;
import org.ustsinau.chapter2_3.service.impl.WriterServiceImpl;

import java.util.List;

public class WriterController {
    private final WriterService writerService = new WriterServiceImpl();

    public void createWriterWithoutPost(String firstName, String lastName) {
        writerService.createWriter(firstName, lastName);

    }

    public void updateWriter(long id, String firstName, String lastName,  List<Post> posts) {
        writerService.updateWriter(id, firstName, lastName, posts );
    }

    public void deleteWriter(long id) {
        writerService.deleteWriter(id);
    }

    public Writer getValueByIndex(long id)  {
        return writerService.getWriterById(id);
    }
    public List<Writer> showAll() {
        return writerService.getAllWriters();
    }
}
