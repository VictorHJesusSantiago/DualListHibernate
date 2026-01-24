package br.com.projeto.dao;

import br.com.projeto.model.Usuario;
import br.com.projeto.util.HibernateUtil;
import br.com.projeto.util.SecurityUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UsuarioDAO {

    public void salvar(Usuario usuario) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(usuario);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public Usuario buscarPorLogin(String login) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Usuario> q = session.createQuery("from Usuario where login = :login", Usuario.class);
            q.setParameter("login", login);
            return q.uniqueResult();
        }
    }

    public void criarUsuarioAdminSeNaoExistir() {
        Usuario admin = buscarPorLogin("admin");
        if (admin == null) {
            String senhaCriptografada = SecurityUtil.hashPassword("1234");
            Usuario novoAdmin = new Usuario("admin", senhaCriptografada);
            salvar(novoAdmin);
            System.out.println(">>> USUÁRIO PADRÃO CRIADO: admin / 1234 <<<");
        }
    }
}