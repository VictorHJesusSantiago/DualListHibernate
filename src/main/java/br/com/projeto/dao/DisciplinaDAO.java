package br.com.projeto.dao;

import br.com.projeto.model.Disciplina;
import br.com.projeto.model.Usuario;
import br.com.projeto.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class DisciplinaDAO {

    public void salvarOuAtualizar(Disciplina disciplina) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(disciplina);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public List<Disciplina> listarPorUsuario(Usuario usuario) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Disciplina d where d.usuario.id = :uid", Disciplina.class)
                    .setParameter("uid", usuario.getId())
                    .list();
        }
    }

    public List<Disciplina> listarTodas() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Disciplina", Disciplina.class).list();
        }
    }
}