package org.ustsinau.chapter2_3.view;

import java.io.IOException;
import java.util.Scanner;

import static org.ustsinau.chapter2_3.view.LabelView.ACTIONS_LABEL;

public class HeadConsole {
    private final Scanner scanner =  new Scanner(System.in);
    private  final String WELCOME_MESSAGE = "Добро пожаловать!\n" +
            "Выберите раздел:\n" +
            "1.Авторы\n" +
            "2.Посты\n" +
            "3.Лэйблы\n";


    public void run() throws IOException {
        System.out.println(WELCOME_MESSAGE);

        String line = scanner.next();
        switch (line) {
            case "1":
                runWriter();
                break;
            case "2":
                runPost();
                break;
            case "3":
                runLabel();
                break;
        }
    }
    public void runWriter() throws IOException {
        WriterView writerView = new WriterView();
        System.out.println(WriterView.ACTIONS_WRITER);
        String actionWriter = scanner.next();
        switch (actionWriter) {
            case "1":
                writerView.createWriter();
                break;
            case "2":
                writerView.updateWriter();
                break;
            case "3":
                writerView.deleteWriter();
                break;
            case "4":
                writerView.getIdWriter();
                break;
            case "5":
                writerView.showAllWriter();
                break;
        }
    }
    public void runPost() throws IOException {
        PostView postView = new PostView();
        System.out.println(PostView.ACTIONS_POST);
        String actionView = scanner.next();
        switch (actionView) {
            case "1":
                postView.createPost();
                break;
            case "2":
                postView.updatePost();
                break;
            case "3":
                postView.deletePost();
                break;
            case "4":
                postView.getPostById();
                break;
            case "5":
                postView.showAllPosts();
                break;
        }
    }
    public void runLabel() throws IOException {
        LabelView labelView = new LabelView();
        System.out.println(ACTIONS_LABEL);
        String actionLabel = scanner.next();
        switch (actionLabel) {
            case "1":
                labelView.createLabel();
                break;
            case "2":
                labelView.updateLabel();
                break;
            case "3":
                labelView.deleteLabel();
                break;
            case "4":
                labelView.getIdLabel();
                break;
            case "5":
                labelView.showAllLabels();
                break;
        }
    }
}
