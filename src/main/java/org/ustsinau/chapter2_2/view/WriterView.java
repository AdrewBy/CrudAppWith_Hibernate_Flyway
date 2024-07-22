package org.ustsinau.chapter2_2.view;

import org.ustsinau.chapter2_2.controller.WriterController;
import org.ustsinau.chapter2_2.models.Post;
import org.ustsinau.chapter2_2.models.Writer;
import org.ustsinau.chapter2_2.repository.PostRepository;
import org.ustsinau.chapter2_2.repository.impl.JdbcPostRepositoryImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WriterView {

    private final PostRepository postRepository = new JdbcPostRepositoryImpl();
    private final Scanner scanner = new Scanner(System.in);
    private final HeadConsole headConsole = new HeadConsole();
    private final WriterController writerController = new WriterController();

    public static final String ACTIONS_WRITER = "Введите действие:\n" +
            "1. Создать нового автора\n" +
            "2. Изменить автора\n" +
            "3. Удалить автора\n" +
            "4. Получить информацию об авторе по id\n" +
            "5. Список всех авторов\n";


    public void createWriter() throws IOException {
        System.out.println("Введите имя нового автора:");
        String firstName = scanner.next();
        System.out.println("Введите фамилию нового автора:");
        String lastName = scanner.next();

        writerController.createWriterWithoutPost(firstName, lastName);
        headConsole.run();
    }

    public void updateWriter() throws IOException {
        System.out.println("Введите индекс автора для его изменения:");
        long indexUp = Long.parseLong(scanner.next());
        System.out.println("Введите имя автора:");
        String nameWrUp = scanner.next();
        System.out.println("Введите фамилию нового автора:");
        String lastNameWrUP = scanner.next();
        scanner.nextLine(); // очистка буфера после next()

        System.out.println("Введите id постов для автора через ПРОБЕЛ:");
        String postsUp = scanner.nextLine().trim();

        List<Post> postsWriter = new ArrayList<>();

        if (!postsUp.isEmpty()) {
            String[] postIds = postsUp.split(" ");
            for (String postId : postIds) {
                try {
                    long postIdLong = Long.parseLong(postId);
                    Post post = postRepository.getById(postIdLong);
                    if (post != null) {
                        postsWriter.add(post);
                    } else {
                        System.out.println("Пост с id=" + postId + " не найден.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Некорректный формат id поста: " + postId);
                }
            }
        }

        writerController.updateWriter(indexUp, nameWrUp, lastNameWrUP, postsWriter);
        headConsole.run();
    }

    public void deleteWriter() throws IOException {
        System.out.println("Введите id автора для его удаления:");
        long indexForDelete = Long.parseLong(scanner.next());
        writerController.deleteWriter(indexForDelete);
        headConsole.run();
    }

    public void getIdWriter() throws IOException {
        System.out.println("Введите id автора для получения всей информации:");
        long id = Long.parseLong(scanner.nextLine());

        Writer writer = writerController.getValueByIndex(id);
        if (writer != null) {
            System.out.println(writer);
        } else {
            System.out.println("Автор с id=" + id + " не найден.");
        }
        headConsole.run();
    }

    public void showAllWriter() throws IOException {
        System.out.println("Список всех авторов:");
        for (Writer item : writerController.showAll()) {
            System.out.println(item);
        }
        headConsole.run();
    }
}