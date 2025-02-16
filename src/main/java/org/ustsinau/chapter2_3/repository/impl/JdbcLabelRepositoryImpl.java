package org.ustsinau.chapter2_3.repository.impl;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.ustsinau.chapter2_3.models.Label;
import org.ustsinau.chapter2_3.repository.LabelRepository;

import java.util.List;


public class JdbcLabelRepositoryImpl implements LabelRepository {

    @Override
    public Label create(Label label) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                // persist не возвращает идентификатор в отличии от save
                session.persist(label);
                transaction.commit();
                return label;
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("Error creating label", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error opening session", e);
        }

    }

    @Override
    public Label update(Label newLabel) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Label mergedLabel = session.merge(newLabel);
                transaction.commit();
                return mergedLabel;

            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("Error updating label", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error opening session", e);
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            try {
                // Получаем управляемый объект Label из базы данных
                Label label = session.get(Label.class, id);
                if (label != null) {
                    // Удаляем все записи из таблицы post_labels, ссылающиеся на этот label
                    Query deletePostLabelsQuery = session.createNativeQuery("DELETE FROM post_labels WHERE label_id = :labelId");

                    deletePostLabelsQuery.setParameter("labelId", id);
                    deletePostLabelsQuery.executeUpdate();

                    // Теперь удаляем саму запись из таблицы labels
                    session.remove(label);
                    transaction.commit();
                } else {
                    System.out.println("Label not found with id: " + id);
                }
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new RuntimeException("Error deleting label", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error opening session", e);
        }
    }


    @Override
    public Label getById(Long id) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Label.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Error getting label by id", e);
        }
    }
    public List<Label> getAll() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = "FROM Label  l order by l.id";
            Query<Label> query = session.createQuery(hql, Label.class);
            return query.list();

        } catch (Exception e) {
            throw new RuntimeException("Error retrieving all labels", e);
        }
    }

}

