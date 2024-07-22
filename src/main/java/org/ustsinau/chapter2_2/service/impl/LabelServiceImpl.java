package org.ustsinau.chapter2_2.service.impl;

import org.ustsinau.chapter2_2.models.Label;
import org.ustsinau.chapter2_2.repository.LabelRepository;
import org.ustsinau.chapter2_2.repository.impl.JdbcLabelRepositoryImpl;
import org.ustsinau.chapter2_2.service.LabelService;

import java.util.List;

public class LabelServiceImpl implements LabelService {

    private final LabelRepository labelRep;

    public LabelServiceImpl() {
        this.labelRep = new JdbcLabelRepositoryImpl();
    }

    public LabelServiceImpl(LabelRepository labelRep) {
        this.labelRep = labelRep;
    }

    @Override
    public Label createLabel(String name) {
        Label label = new Label(name);
        return labelRep.create(label);
    }

    @Override
    public Label updateLabel(long id, String name) {
        Label label = new Label(id, name);
        return labelRep.update(label);

    }

    @Override
    public void deleteLabel(long id) {
        labelRep.delete(id);
    }

    @Override
    public Label getLabelById(long id) {
        return labelRep.getById(id);
    }

    @Override
    public List<Label> getAllLabels() {
        return labelRep.getAll();
    }
}
