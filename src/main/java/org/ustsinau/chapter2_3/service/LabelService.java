package org.ustsinau.chapter2_3.service;

import org.ustsinau.chapter2_3.models.Label;

import java.util.List;

public interface LabelService {

    Label createLabel(String name);
    Label updateLabel(long id, String name);
    void deleteLabel(long id);
    Label getLabelById(long id);

    List<Label> getAllLabels();
}
