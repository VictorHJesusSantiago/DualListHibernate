package br.com.projeto.controller;

import br.com.projeto.dao.AlunoDAO;
import br.com.projeto.model.Aluno;
import br.com.projeto.view.DualListSelector;

import javax.swing.*;
import java.util.List;

// A classe está marcada como não usada porque o MainApp assumiu suas funções
@SuppressWarnings("unused")
public class MatriculaController {

    private final AlunoDAO alunoDAO;
    private final DualListSelector<Aluno> viewSelector;

    public MatriculaController(DualListSelector<Aluno> viewSelector) {
        this.viewSelector = viewSelector;
        this.alunoDAO = new AlunoDAO();
    }

    public void carregarDadosIniciais() {
        List<Aluno> lista = alunoDAO.listarTodos();
        if (lista.isEmpty()) {
            // Atualizado com 'false' no final para bater com o novo construtor do Aluno
            alunoDAO.salvarOuAtualizar(new Aluno("João Silva", "2023001", "joao@email.com", "9999-1111", false));
            alunoDAO.salvarOuAtualizar(new Aluno("Maria Oliveira", "2023002", "maria@email.com", "9999-2222", false));
            alunoDAO.salvarOuAtualizar(new Aluno("Carlos Santos", "2023003", "carlos@email.com", "9999-3333", false));
            alunoDAO.salvarOuAtualizar(new Aluno("Ana Souza", "2023004", "ana@email.com", "9999-4444", true));
            alunoDAO.salvarOuAtualizar(new Aluno("Roberto Lima", "2023005", "beto@email.com", "9999-5555", true));
            lista = alunoDAO.listarTodos();
        }
        viewSelector.setSourceItems(lista);
    }

    public void salvarMatricula() {
        List<Aluno> alunosMatriculados = viewSelector.getTargetItems();
        if (alunosMatriculados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum aluno selecionado.");
            return;
        }
        JOptionPane.showMessageDialog(null, "Matrícula realizada com sucesso!");
    }
}