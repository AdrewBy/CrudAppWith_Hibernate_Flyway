package org.ustsinau.chapter2_3.service.impl;

import org.ustsinau.chapter2_3.models.Post;
import org.ustsinau.chapter2_3.models.Writer;
import org.ustsinau.chapter2_3.repository.WriterRepository;
import org.ustsinau.chapter2_3.repository.impl.JdbcWriterRepositoryImpl;
import org.ustsinau.chapter2_3.service.WriterService;

import java.util.List;

public class WriterServiceImpl implements WriterService {
    private final WriterRepository writerRepository ;

    public WriterServiceImpl(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    public WriterServiceImpl() {
        this.writerRepository = new JdbcWriterRepositoryImpl();
    }

    @Override
    public Writer createWriter(String firstName, String lastName) {
        Writer writer = new Writer(firstName, lastName);
     return    writerRepository.create(writer);
    }

    @Override
    public Writer updateWriter(long id, String firstName, String lastName, List<Post> posts) {
        Writer wrt = new Writer(id, firstName, lastName, posts );
      return   writerRepository.update(wrt);
    }

    @Override
    public void deleteWriter(long id) {
        writerRepository.delete(id);

    }

    @Override
    public Writer getWriterById(long id) {
        return writerRepository.getById(id);

    }

    @Override
    public List<Writer> getAllWriters() {
        return writerRepository.getAll();
    }
}
