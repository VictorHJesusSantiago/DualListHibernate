package br.com.projeto.dao;

import br.com.projeto.model.Aluno;
import br.com.projeto.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class AlunoDAO {

    public void salvarOuAtualizar(Aluno aluno) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(aluno); // Merge serve para Salvar (novo) ou Atualizar (existente)
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void excluir(Aluno aluno) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(session.contains(aluno) ? aluno : session.merge(aluno));
            tx.commit();
        }
    }

    public List<Aluno> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Aluno order by nome", Aluno.class).list();
        }
    }
}