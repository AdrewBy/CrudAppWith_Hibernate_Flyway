package org.ustsinau.chapter2_2.service.impl;

import org.ustsinau.chapter2_2.models.Writer;
import org.ustsinau.chapter2_2.repository.WriterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WriterServiceImplTest {

    @Mock
    private WriterRepository writerRepository;
    @InjectMocks
    private WriterServiceImpl writerService;

    private long id;
    private String firstName;
    private String lastName;
    private Writer writer;


    @BeforeEach
    void setUp() {
        id=1L;
        firstName = "First name";
        lastName = "Last name";
        writer = new Writer(id,firstName,lastName, List.of());
    }

    @Test
    void createWriter() {
        when(writerRepository.create(any(Writer.class))).thenReturn(writer);

        Writer result = writerService.createWriter(firstName,lastName);

        assertEquals(writer,result);
        verify(writerRepository,times(1)).create(any(Writer.class));
    }

    @Test
    void updateWriter() {
        when(writerRepository.update(any(Writer.class))).thenReturn(writer);

        Writer result = writerService.updateWriter(id,firstName,lastName,List.of());
        assertEquals(writer,result);
        verify(writerRepository,times(1)).update(any(Writer.class));
    }

    @Test
    void deleteWriter() {
        doNothing().when(writerRepository).delete(id);

        writerService.deleteWriter(id);
        verify(writerRepository,times(1)).delete(anyLong());
    }

    @Test
    void getWriterById() {
        when(writerRepository.getById(id)).thenReturn(writer);
        Writer result = writerService.getWriterById(id);
        assertEquals(writer,result);
        verify(writerRepository,times(1)).getById(anyLong());
    }

    @Test
    void getAllWriters() {
        List<Writer> writers = List.of(writer);
        when(writerRepository.getAll()).thenReturn(writers);

        List<Writer> result = writerService.getAllWriters();

        assertEquals(writers,result);
        verify(writerRepository, times(1)).getAll();
    }
}