package br.com.projeto.util;

import br.com.projeto.dao.AlunoDAO;
import br.com.projeto.model.Aluno;

public class DatabaseSeeder {
    public static void run() {
        AlunoDAO dao = new AlunoDAO();
        if (dao.listarTodos().isEmpty()) {
            dao.salvarOuAtualizar(new Aluno("João Silva", "2023001", "joao@email.com", "11999990001", false));
            dao.salvarOuAtualizar(new Aluno("Maria Souza", "2023002", "maria@email.com", "11999990002", false));
            dao.salvarOuAtualizar(new Aluno("Pedro Santos", "2023003", "pedro@email.com", "11999990003", false));
            dao.salvarOuAtualizar(new Aluno("Ana Oliveira", "2023004", "ana@email.com", "11999990004", true));
            dao.salvarOuAtualizar(new Aluno("Lucas Ferreira", "2023005", "lucas@email.com", "11999990005", true));
        }
    }
}