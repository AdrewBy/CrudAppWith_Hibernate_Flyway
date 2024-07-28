package org.ustsinau.chapter2_3.repository.impl;


import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.ustsinau.chapter2_3.models.Post;
import org.ustsinau.chapter2_3.models.Writer;
import org.ustsinau.chapter2_3.repository.WriterRepository;


import java.util.*;


public class JdbcWriterRepositoryImpl implements WriterRepository {


    @Override
    public Writer create(Writer writer) {

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
                // Обновляем поля существующего Writer
                existingWriter.setFirstName(writer.getFirstName());
                existingWriter.setLastName(writer.getLastName());

                existingWriter.getPosts().clear();  // Удаляем старые посты
                // Добавляем новые посты
                for (Post post : writer.getPosts()) {
                    post.setWriter(existingWriter);  // Устанавливаем связь с текущим Writer
                    existingWriter.getPosts().add(post);  // Добавляем пост в коллекцию
                }

                session.merge(existingWriter);
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

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {

                Writer writer = session.get(Writer.class, id);
                if (writer != null) {
                    // Отключаем связь между Writer и его постами
                    for (Post post : writer.getPosts()) {
                        post.setWriter(null);  // Удаляем связь
                        session.merge(post);  // Сохраняем изменения, чтобы обновить запись Post
                    }

                    // Теперь удаляем автора
                    session.remove(writer);
                    transaction.commit();
                } else {
                    System.out.println("Writer not found with id: " + id);
                }
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new RuntimeException("Error deleting writer", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error opening session", e);
        }
    }

    public List<Writer> getAll() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Writer w ORDER BY w.id";
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

            for (Post post : writer.getPosts()) {
                Hibernate.initialize(post.getLabels());
            }

            return writer;
        } catch (Exception e) {
            throw new RuntimeException("Error getting writer by id", e);
        }
    }
}