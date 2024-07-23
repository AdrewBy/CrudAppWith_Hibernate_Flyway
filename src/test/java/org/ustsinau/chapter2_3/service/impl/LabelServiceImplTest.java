package org.ustsinau.chapter2_3.service.impl;

import org.ustsinau.chapter2_3.models.Label;
import org.ustsinau.chapter2_3.repository.LabelRepository;
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
class LabelServiceImplTest {

    @Mock
    private LabelRepository labelRepository;

    @InjectMocks
    private LabelServiceImpl labelServiceImpl;

    private Label label;
    private String name;
    private long id;

    @BeforeEach
    void setUp() {
        id = 1L;
        name = "Test Label";
        label = new Label(id, name);
    }

    @Test
    void createLabel() {
        // Мокируем метод create
        when(labelRepository.create(any(Label.class))).thenReturn(label);

        // Вызываем метод, который тестируем
        Label result = labelServiceImpl.createLabel(name);

        // Проверяем результат
        assertEquals(label, result);

        // Проверяем, что метод create был вызван один раз с любым объектом Label
        verify(labelRepository, times(1)).create(any(Label.class));
    }

    @Test
    void updateLabel() {
        when(labelRepository.update(any(Label.class))).thenReturn(label);
        Label result = labelServiceImpl.updateLabel(id, name);

        // Проверяем результат
        assertEquals(label, result);
        // Проверяем, что метод create был вызван один раз с любым объектом Label
        verify(labelRepository, times(1)).update(any(Label.class));
    }

    @Test
    void deleteLabel() {
        doNothing().when(labelRepository).delete(id);
        labelServiceImpl.deleteLabel(id);
        // Проверяем, что метод delete был вызван один раз с любым знаением
        verify(labelRepository, times(1)).delete(anyLong());
    }

    @Test
    void getLabelById() {
        when(labelRepository.getById(id)).thenReturn(label);

        Label labelResult = labelServiceImpl.getLabelById(id);
        assertEquals(label, labelResult);

        verify(labelRepository, times(1)).getById(anyLong());
    }

    @Test
    void getAllLabels() {
        List<Label> labels = List.of(label);

        when(labelRepository.getAll()).thenReturn(labels);

        List<Label> labels2 = labelRepository.getAll();

        assertEquals(labels, labels2);

        verify(labelRepository, times(1)).getAll();
    }
}
