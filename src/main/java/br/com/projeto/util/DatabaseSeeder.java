package br.com.projeto.util;

import br.com.projeto.dao.AlunoDAO;
import br.com.projeto.model.Aluno;
import java.util.Random;

public class DatabaseSeeder {

    private static final String[] NOMES = {
            "Ana", "Bruno", "Carlos", "Daniela", "Eduardo", "Fernanda", "Gabriel", "Helena", "Igor", "Julia",
            "Lucas", "Mariana", "Nicolas", "Olivia", "Pedro", "Rafaela", "Samuel", "Tatiana", "Vitor", "Yasmin"
    };

    private static final String[] SOBRENOMES = {
            "Silva", "Santos", "Oliveira", "Souza", "Rodrigues", "Ferreira", "Almeida", "Pereira", "Lima", "Gomes",
            "Costa", "Martins", "Araujo", "Barbosa", "Ribeiro", "Alves", "Cardoso", "Teixeira", "Rocha", "Dias"
    };

    private static final String[] DOMINIOS = {"gmail.com", "hotmail.com", "outlook.com", "uol.com.br", "bol.com.br"};

    public static void run() {
        AlunoDAO dao = new AlunoDAO();
        if (dao.listarTodos().isEmpty()) {
            Random random = new Random();
            for (int i = 0; i < 30; i++) {
                String nome = NOMES[random.nextInt(NOMES.length)] + " " + SOBRENOMES[random.nextInt(SOBRENOMES.length)];
                String matricula = "2024" + String.format("%04d", random.nextInt(10000));
                String email = gerarEmail(nome, random);
                String telefone = gerarTelefone(random);

                Aluno aluno = new Aluno(nome, matricula, email, telefone);
                try {
                    dao.salvarOuAtualizar(aluno);
                } catch (Exception ignored) {
                    // Ignora duplicações de matrícula geradas aleatoriamente
                }
            }
            System.out.println(">>> SEEDER EXECUTADO: Alunos aleatórios inseridos.");
        }
    }

    private static String gerarEmail(String nome, Random random) {
        String[] partes = nome.toLowerCase().split(" ");
        return partes[0] + "." + partes[1] + random.nextInt(99) + "@" + DOMINIOS[random.nextInt(DOMINIOS.length)];
    }

    private static String gerarTelefone(Random random) {
        int ddd = 11 + random.nextInt(89);
        int parte1 = 90000 + random.nextInt(9999);
        int parte2 = 1000 + random.nextInt(8999);
        return String.valueOf(ddd) + parte1 + parte2;
    }
}