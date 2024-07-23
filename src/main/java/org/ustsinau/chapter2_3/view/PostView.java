package org.ustsinau.chapter2_3.view;

import org.ustsinau.chapter2_3.controller.PostController;
import org.ustsinau.chapter2_3.models.Label;
import org.ustsinau.chapter2_3.models.Post;
import org.ustsinau.chapter2_3.models.PostStatus;
import org.ustsinau.chapter2_3.repository.LabelRepository;
import org.ustsinau.chapter2_3.repository.impl.JdbcLabelRepositoryImpl;

import java.io.IOException;
import java.util.*;

public class PostView {

    private final LabelRepository labelRepository = new JdbcLabelRepositoryImpl();
    private final Scanner scanner = new Scanner(System.in);
    private final HeadConsole headConsole = new HeadConsole();

    private final PostController postController = new PostController();

    public static final String ACTIONS_POST = "Введите действие:\n" +
            "1. Создать новый пост\n" +
            "2. Изменить пост и добавить лэйблы\n" +
            "3. Удалить пост\n" +
            "4. Получить информацию о посте по id\n" +
            "5. Список всех постов\n";

    public void createPost() throws IOException {
        System.out.println("Введите содержание нового поста:");
        String content = scanner.nextLine();
        PostStatus postStatus = PostStatus.UNDER_REVIEW;
        Date created = new Date();
        postController.createPost(content, postStatus, created);

        headConsole.run();
    }

    public void updatePost() throws IOException {
        System.out.println("Введите id поста для изменения его содержания:");
        long id = Long.parseLong(scanner.nextLine());

        System.out.println("Введите новое содержание поста:");
        String content = scanner.nextLine();

        System.out.println("Введите id лэйблов для нового поста через ПРОБЕЛ:");
        String labelsInput = scanner.nextLine().trim();
        List<Label> labels = new ArrayList<>();

        if (!labelsInput.isEmpty()) {
            String[] labelIds = labelsInput.split(" ");
            for (String labelId : labelIds) {
                try {
                    long labelIdLong = Long.parseLong(labelId);
                    Label label = labelRepository.getById(labelIdLong);
                    if (label != null) {
                        labels.add(label);
                    } else {
                        System.out.println("Лэйбл с id=" + labelId + " не найден.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Некорректный формат id лэйбла: " + labelId);
                }
            }
        }

        System.out.print("Введите статус поста (ACTIVE, UNDER_REVIEW, DELETED): ");
        PostStatus postStatus = PostStatus.valueOf(scanner.nextLine().toUpperCase());

        Date updated = new Date();
        postController.updatePost(id, content, updated, labels, postStatus);

        headConsole.run();
    }

    public void deletePost() throws IOException {
        System.out.println("Введите id поста для его удаления:");
        long id = Long.parseLong(scanner.nextLine());
        postController.deletePost(id);
        headConsole.run();
    }

    public void getPostById() throws IOException {
        System.out.println("Введите id поста для получения информации:");
        long id = Long.parseLong(scanner.nextLine());

        Post post = postController.getPostById(id);
        if (post != null) {
            System.out.println(post);
        } else {
            System.out.println("Пост с id=" + id + " не найден.");
        }

        headConsole.run();
    }

    public void showAllPosts() throws IOException {
        System.out.println("Список всех постов:");
        List<Post> posts = postController.showAll();
        for (Post post : posts) {
            System.out.println(post);
        }
        headConsole.run();
    }
}