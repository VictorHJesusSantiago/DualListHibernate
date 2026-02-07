package br.com.projeto.controller;

import br.com.projeto.dao.UsuarioDAO;
import br.com.projeto.model.Usuario;
import br.com.projeto.util.SecurityUtil;

public class LoginController {

    private final UsuarioDAO usuarioDAO;

    public LoginController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public Usuario autenticar(String login, String senha) {
        Usuario u = usuarioDAO.buscarPorLogin(login);
        if (u != null && SecurityUtil.checkPassword(senha, u.getSenhaHash())) {
            return u;
        }
        return null;
    }

    public void registrarUsuario(String login, String senha) throws Exception {
        if (login.isEmpty() || senha.isEmpty()) {
            throw new IllegalArgumentException("Campos vazios");
        }
        String hash = SecurityUtil.hashPassword(senha);
        Usuario novo = new Usuario(login, hash);
        usuarioDAO.salvar(novo);
    }
}