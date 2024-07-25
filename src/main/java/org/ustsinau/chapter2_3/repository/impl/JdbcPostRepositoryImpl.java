package org.ustsinau.chapter2_3.repository.impl;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.ustsinau.chapter2_3.models.Post;
import org.ustsinau.chapter2_3.repository.PostRepository;


import java.util.List;


public class JdbcPostRepositoryImpl implements PostRepository {


    @Override
    public Post create(Post post) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(post);
                transaction.commit();
                return post;
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("Error creating post", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error opening session", e);
        }
    }

    @Override
    public Post update(Post post) {

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Получаем существующий пост из базы данных
            Post existingPost = session.get(Post.class, post.getId());

            if (existingPost != null) {
                Hibernate.initialize(existingPost.getLabels());
                // Обновляем поля существующего поста
                existingPost.setContent(post.getContent());
                existingPost.setPostStatus(post.getPostStatus());
                existingPost.setUpdated(post.getUpdated());

                // Обновляем коллекцию лэйблов
                existingPost.getLabels().clear();
                existingPost.getLabels().addAll(post.getLabels());


                // Сохраняем изменения
                session.update(existingPost);
            }
                transaction.commit();
                return existingPost;
            } catch(Exception e){
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new RuntimeException("Error updating post", e);
            }
        }


        @Override
        public void delete (Long id){

            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                Transaction transaction = session.beginTransaction();
                try {
                    Post post = session.get(Post.class, id);
                    if (post != null) {
                        session.remove(post);
                        transaction.commit();
                    } else {
                        System.out.println("Post not found with id: " + id);
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
        public Post getById (Long id){

            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                Post post = session.get(Post.class, id);
                Hibernate.initialize(post.getLabels());
                return post;
            } catch (Exception e) {
                throw new RuntimeException("Error getting post by id", e);
            }
        }

        private List<Post> getAllPostsInternal () {

            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                String hql = "FROM Post p LEFT JOIN FETCH p.labels order by p.id";
                Query<Post> query = session.createQuery(hql, Post.class);
                return query.list();
            } catch (Exception e) {
                throw new RuntimeException("Error retrieving all posts", e);
            }
        }


        @Override
        public List<Post> getAll () {
            return getAllPostsInternal();
        }

    }