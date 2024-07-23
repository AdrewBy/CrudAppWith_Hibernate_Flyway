package org.ustsinau.chapter2_3.controller;

import org.ustsinau.chapter2_3.models.Label;
import org.ustsinau.chapter2_3.service.LabelService;
import org.ustsinau.chapter2_3.service.impl.LabelServiceImpl;

import java.util.List;


public class LabelController {
    private final LabelService label = new LabelServiceImpl();

    public void createLabel(String name) {
        label.createLabel(name);
    }

    public void updateLabel(long id, String name) {
        label.updateLabel(id, name);
    }


    public void deleteLabel(Long id) {
        label.deleteLabel(id);
    }

    public Label getLabelById(Long id) {
        return label.getLabelById(id);
    }

    public List<Label> showAll() {
        return label.getAllLabels();
    }


}
