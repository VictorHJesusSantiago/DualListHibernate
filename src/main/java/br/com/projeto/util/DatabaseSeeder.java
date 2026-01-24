package br.com.projeto.util;

import br.com.projeto.dao.AlunoDAO;
import br.com.projeto.model.Aluno;
import java.util.Random;

public class DatabaseSeeder {

    private static final String[] NOMES = { "Ana", "Bruno", "Carlos", "Daniela", "Eduardo", "Fernanda", "Gabriel", "Helena", "Igor", "Julia", "Lucas", "Mariana", "Nicolas", "Olivia", "Pedro", "Rafaela", "Samuel", "Tatiane", "Vitor", "Yasmin" };
    private static final String[] SOBRENOMES = { "Silva", "Santos", "Oliveira", "Souza", "Lima", "Pereira", "Ferreira", "Costa", "Rodrigues", "Almeida", "Nascimento", "Alves", "Carvalho", "Mendes", "Ribeiro" };

    public static void run() {
        AlunoDAO dao = new AlunoDAO();

        // Só roda se o banco estiver vazio (para não duplicar)
        if (!dao.listarTodos().isEmpty()) {
            return;
        }

        System.out.println(">>> INICIANDO MIGRATION: GERANDO 1100 ALUNOS...");
        Random random = new Random();

        for (int i = 1; i <= 1000; i++) {
            String nome = gerarNomeAleatorio(random);
            String matricula = "2026" + String.format("%04d", i);
            Aluno a = new Aluno(nome, matricula, matricula + "@escola.com", "9999-0000", false);
            dao.salvarOuAtualizar(a);
        }

        for (int i = 1001; i <= 1100; i++) {
            String nome = gerarNomeAleatorio(random);
            String matricula = "2026" + String.format("%04d", i);
            Aluno a = new Aluno(nome, matricula, matricula + "@escola.com", "9999-0000", true);
            dao.salvarOuAtualizar(a);
        }

        System.out.println(">>> MIGRATION CONCLUÍDA COM SUCESSO! <<<");
    }

    private static String gerarNomeAleatorio(Random r) {
        return NOMES[r.nextInt(NOMES.length)] + " " +
                SOBRENOMES[r.nextInt(SOBRENOMES.length)] + " " +
                SOBRENOMES[r.nextInt(SOBRENOMES.length)];
    }
}