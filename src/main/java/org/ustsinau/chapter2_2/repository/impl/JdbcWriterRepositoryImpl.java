package org.ustsinau.chapter2_2.repository.impl;


import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.ustsinau.chapter2_2.models.Post;
import org.ustsinau.chapter2_2.models.Writer;
import org.ustsinau.chapter2_2.repository.WriterRepository;


import java.util.*;


public class JdbcWriterRepositoryImpl implements WriterRepository {


    @Override
    public Writer create(Writer writer) {

        //    String sql = "INSERT INTO writers (firstName, lastName) VALUES (?, ?)";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(writer);
                transaction.commit();
                return writer;
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("Error creating writer", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error opening session", e);
        }
    }


    @Override
    public Writer update(Writer writer) {

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Получаем управляемый объект Writer из базы данных
            Writer existingWriter = session.get(Writer.class, writer.getId());

            if (existingWriter != null) {
                // Инициализируем ленивую коллекцию постов
                Hibernate.initialize(existingWriter.getPosts());
                for (Post post : existingWriter.getPosts()) {
                    Hibernate.initialize(post.getLabels());
                }
                // Обновляем поля существующего Writer
                existingWriter.setFirstName(writer.getFirstName());
                existingWriter.setLastName(writer.getLastName());

                // Обновляем коллекцию постов
                existingWriter.getPosts().clear();  // Удаляем старые посты
                existingWriter.getPosts().addAll(writer.getPosts());  // Добавляем новые посты

                // Устанавливаем связь между постами и Writer
                for (Post post : existingWriter.getPosts()) {
                    post.setWriter(existingWriter);
                }

                session.update(existingWriter);
            }

            transaction.commit();
            return existingWriter;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error updating writer", e);
        }
    }


    @Override
    public void delete(Long id) {
        //     String sql = "Delete from writers where id = ?";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Writer writer = session.get(Writer.class, id);
                if (writer != null) {
                    session.remove(writer);
                    transaction.commit();
                } else {
                    System.out.println("Writer not found with id: " + id);
                }
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("Error deleting writer", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error opening session", e);
        }
    }

    @Override
    public List<Writer> getAll() {
        return getAllWritersInternal();
    }


    private List<Writer> getAllWritersInternal() {
//        String sql = "SELECT " +
//                "w.id AS writer_id, w.firstName, w.lastName, " +
//                "p.id AS post_id, p.content, p.created, p.updated, p.postStatus, " +
//                "l.id AS label_id, l.name " +
//                "FROM writers w " +
//                "LEFT JOIN posts p ON w.id = p.writer_id " +
//                "LEFT JOIN post_label pl ON p.id = pl.post_id " +
//                "LEFT JOIN labels l ON pl.label_id = l.id";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Writer w";
            Query<Writer> query = session.createQuery(hql, Writer.class);
            List<Writer> writers = query.list();

            // Инициализация связанных коллекций
            for (Writer writer : writers) {
                Hibernate.initialize(writer.getPosts());
                for (Post post : writer.getPosts()) {
                    Hibernate.initialize(post.getLabels());
                }
            }

            return writers;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving all writers", e);
        }
    }

    @Override
    public Writer getById(Long id) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Writer writer = session.get(Writer.class, id);
            Hibernate.initialize(writer.getPosts()); // Явное инициализирование коллекции
            Hibernate.initialize(writer.getPosts());
            for (Post post : writer.getPosts()) {
                Hibernate.initialize(post.getLabels());
            }

            return writer;
        } catch (Exception e) {
            throw new RuntimeException("Error getting writer by id", e);
        }
    }
}