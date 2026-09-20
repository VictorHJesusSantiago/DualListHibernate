package br.com.projeto.dao;

import br.com.projeto.model.Aluno;
import br.com.projeto.model.Disciplina;
import br.com.projeto.model.Usuario;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PersistenceContractTest {

    private final AlunoDAO alunoDAO = new AlunoDAO();
    private final DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Test
    void alunoDaoPersistsUpdatesOrdersAndDeletesStudents() {
        String suffix = UUID.randomUUID().toString();
        Aluno aluno = new Aluno("Bruna Teste", "MAT-" + suffix, "bruna@example.test", "11999990000");

        alunoDAO.salvarOuAtualizar(aluno);

        Aluno salvo = alunoDAO.listarTodos().stream()
                .filter(item -> item.getMatricula().equals("MAT-" + suffix))
                .findFirst()
                .orElseThrow();
        assertNotNull(salvo.getId());

        salvo.setNome("Ana Teste");
        alunoDAO.salvarOuAtualizar(salvo);

        List<Aluno> alunos = alunoDAO.listarTodos();
        assertEquals("Ana Teste", alunos.stream()
                .filter(item -> item.getMatricula().equals("MAT-" + suffix))
                .findFirst()
                .orElseThrow()
                .getNome());

        alunoDAO.excluir(salvo);

        assertFalse(alunoDAO.listarTodos().stream()
                .anyMatch(item -> item.getMatricula().equals("MAT-" + suffix)));
    }

    @Test
    void disciplinaDaoPersistsUserScopedEnrollmentRelationship() {
        String suffix = UUID.randomUUID().toString();
        Usuario usuario = new Usuario("prof-" + suffix, "hash");
        usuarioDAO.salvar(usuario);

        Aluno aluno = new Aluno("Carlos Teste", "MAT-" + suffix, "carlos@example.test", "11888880000");
        alunoDAO.salvarOuAtualizar(aluno);
        Aluno alunoPersistido = alunoDAO.listarTodos().stream()
                .filter(item -> item.getMatricula().equals("MAT-" + suffix))
                .findFirst()
                .orElseThrow();

        Disciplina disciplina = new Disciplina("Banco de Dados " + suffix, usuario);
        disciplina.setAlunos(List.of(alunoPersistido));
        disciplinaDAO.salvarOuAtualizar(disciplina);

        List<Disciplina> disciplinas = disciplinaDAO.listarPorUsuario(usuario);

        Disciplina encontrada = disciplinas.stream()
                .filter(item -> item.getNome().equals("Banco de Dados " + suffix))
                .findFirst()
                .orElseThrow();
        assertEquals(1, encontrada.getAlunos().size());
        assertEquals(alunoPersistido.getMatricula(), encontrada.getAlunos().get(0).getMatricula());
    }
}
