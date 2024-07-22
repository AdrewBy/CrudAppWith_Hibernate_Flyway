package org.ustsinau.chapter2_2.view;


import org.ustsinau.chapter2_2.controller.LabelController;
import org.ustsinau.chapter2_2.models.Label;


import java.io.IOException;
import java.util.Scanner;

public class LabelView {
    private final Scanner scanner = new Scanner(System.in);
    private final HeadConsole headConsole = new HeadConsole();
    public static final String ACTIONS_LABEL = "Введите действие:\n" +
            "1.Создать новый лэйбл\n" +
            "2.Изменить лэйбл\n" +
            "3.Удалить лэйбл\n" +
            "4.Получить лэйбл по id\n" +
            "5.Список всех лэйблов\n";

    LabelController labelContrl = new LabelController();

    public void createLabel() throws IOException {

        System.out.println("Введите имя нового лэйбла:");
        String name = scanner.next();
        labelContrl.createLabel(name);
        headConsole.run();
    }

    public void updateLabel() throws IOException {

        System.out.println("Введите id лэйбла для его изменения:");
        long id = Long.parseLong(scanner.next());
        System.out.println("Введите новое название лэйбла(состоит из одного слова):");
        String name = scanner.next();
        labelContrl.updateLabel(id, name);
        headConsole.run();
    }

    public void deleteLabel() throws IOException {

        System.out.println("Введите id лэйбла для его удаления:");
        long indexForDelete = Long.parseLong(scanner.next());
        labelContrl.deleteLabel(indexForDelete);
        headConsole.run();
    }

    public void getIdLabel() throws IOException {

        System.out.println("Введите id лэйбла для получения информации:");
        long id = Long.parseLong(scanner.next());
        System.out.println("\n" + labelContrl.getLabelById(id) + "\n");
        headConsole.run();
    }

    public void showAllLabels() throws IOException {
        System.out.println("Список всех категорий:");
        for (Label item : labelContrl.showAll()) {
            System.out.println(item);
        }
        headConsole.run();
    }
}