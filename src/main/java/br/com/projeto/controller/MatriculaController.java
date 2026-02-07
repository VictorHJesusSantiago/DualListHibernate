package br.com.projeto.controller;

import br.com.projeto.dao.AlunoDAO;
import br.com.projeto.dao.DisciplinaDAO;
import br.com.projeto.model.Aluno;
import br.com.projeto.model.Disciplina;
import br.com.projeto.model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MatriculaController {

    private final AlunoDAO alunoDAO;
    private final DisciplinaDAO disciplinaDAO;

    public MatriculaController() {
        this.alunoDAO = new AlunoDAO();
        this.disciplinaDAO = new DisciplinaDAO();
    }

    public void salvarAluno(Aluno aluno) {
        alunoDAO.salvarOuAtualizar(aluno);
    }

    public void excluirAluno(Aluno aluno) {
        List<Disciplina> todasDisciplinas = disciplinaDAO.listarTodas();
        for (Disciplina d : todasDisciplinas) {
            if (d.getAlunos().contains(aluno)) {
                d.getAlunos().remove(aluno);
                disciplinaDAO.salvarOuAtualizar(d);
            }
        }
        alunoDAO.excluir(aluno);
    }

    public void criarDisciplina(String nome, Usuario usuario) {
        Disciplina d = new Disciplina(nome, usuario);
        disciplinaDAO.salvarOuAtualizar(d);
    }

    public void salvarMatriculas(Disciplina disciplina, List<Aluno> alunosMatriculados) {
        disciplina.setAlunos(alunosMatriculados);
        disciplinaDAO.salvarOuAtualizar(disciplina);
    }

    public List<Disciplina> listarDisciplinas(Usuario usuario) {
        return disciplinaDAO.listarPorUsuario(usuario);
    }

    public List<Aluno> listarAlunosDisponiveis(Disciplina disciplina) {
        List<Aluno> todos = alunoDAO.listarTodos();
        if (disciplina == null) return todos;

        List<Long> idsMatriculados = disciplina.getAlunos().stream()
                .map(Aluno::getId)
                .collect(Collectors.toList());

        return todos.stream()
                .filter(a -> !idsMatriculados.contains(a.getId()))
                .collect(Collectors.toList());
    }

    public List<Aluno> listarAlunosMatriculados(Disciplina disciplina) {
        if (disciplina == null) return new ArrayList<>();
        return disciplina.getAlunos();
    }
}