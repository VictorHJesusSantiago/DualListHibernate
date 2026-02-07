package br.com.projeto.model;

import jakarta.persistence.*;

@Entity
@Table(name = "alunos")
public class Aluno {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String nome;
    @Column(nullable = false, unique = true) private String matricula;
    private String email;
    private String telefone;

    private boolean matriculadoNaDisciplina = false;

    public Aluno() {}

    public Aluno(String nome, String matricula, String email, String telefone, boolean matriculadoNaDisciplina) {
        this.nome = nome;
        this.matricula = matricula;
        this.email = email;
        this.telefone = telefone;
        this.matriculadoNaDisciplina = matriculadoNaDisciplina;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public boolean isMatriculadoNaDisciplina() { return matriculadoNaDisciplina; }
    public void setMatriculadoNaDisciplina(boolean matriculadoNaDisciplina) { this.matriculadoNaDisciplina = matriculadoNaDisciplina; }

    @Override public String toString() { return matricula + " - " + nome; }
}