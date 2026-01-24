<div align="center">
  <br />
  <img src="https://cdn-icons-png.flaticon.com/512/226/226777.png" alt="Logo Java Project" width="120" style="border-radius: 10%;">

  <h1 style="border-bottom: none; font-size: 2.5em; margin-bottom: 0;">Sistema de Matrícula Acadêmica</h1>

  <strong style="font-size: 1.2em; color: #555;">
    Componente de Seleção em Lista Dupla (Dual List) com Persistência Hibernate
  </strong>

  <br />
  <br />

  <p style="font-size: 1.1em; max-width: 700px;">
    Uma solução desktop robusta desenvolvida em <strong>Java Swing</strong> sob a arquitetura <strong>MVC</strong>. O projeto foca na implementação de um componente visual reutilizável para seleção de itens e na persistência de dados utilizando <strong>Hibernate ORM</strong>.
  </p>

  <p>
    <img src="https://img.shields.io/badge/status-conclu%C3%ADdo-brightgreen?style=for-the-badge" alt="Status do Projeto: Concluído">
    <img src="https://img.shields.io/badge/Java-23-ED8B00?style=for-the-badge&logo=openjdk" alt="Versão do Java">
    <img src="https://img.shields.io/badge/Hibernate-6.4.1-59666C?style=for-the-badge&logo=hibernate" alt="Versão do Hibernate">
    <img src="https://img.shields.io/badge/H2_Database-InMemory-blue?style=for-the-badge" alt="Banco de Dados">
  </p>
</div>

---

## 📖 Sobre o Projeto

O **Sistema de Matrícula Acadêmica** é uma aplicação desenvolvida como atividade avaliativa para demonstrar competências avançadas em Desenvolvimento Desktop e Engenharia de Software. O objetivo central foi criar um componente de interface gráfica complexo ("Dual List Selector") para gerenciar a inclusão de alunos em disciplinas.

Do ponto de vista técnico, o projeto é uma implementação estrita dos princípios de **Programação Orientada a Objetos (POO)** e da arquitetura **Model-View-Controller (MVC)**. Diferente de soluções simples, este projeto implementa um *Look and Feel* moderno (FlatLaf), criptografia de senhas profissional e um banco de dados em memória para portabilidade total.

A aplicação simula um ambiente administrativo onde um gestor pode cadastrar alunos, realizar manutenções cadastrais (CRUD) e efetivar matrículas movendo registros entre listas visuais, com persistência automática das alterações.

## ✨ Funcionalidades Principais

O sistema foi desenvolvido com foco na experiência do usuário e na integridade dos dados:

* **👥 Gestão de Alunos (CRUD):** Cadastro completo, edição e exclusão de alunos com validação de dados.
* **⇄ Componente Dual List (Genérico):** Componente visual customizado (`DualListSelector<T>`) que permite mover itens entre duas listas ("Disponíveis" e "Selecionados") com botões de ação intuitivos.
* **🔐 Sistema de Autenticação:** Tela de Login e Registro com validação de credenciais e feedback visual.
* **🛡️ Segurança de Dados:** As senhas dos usuários nunca são salvas em texto plano; utiliza-se o algoritmo **BCrypt** para hashing seguro.
* **🎨 Interface Moderna:** Utilização da biblioteca **FlatLaf** para garantir um visual limpo, responsivo e profissional, similar a aplicações web modernas ou nativas do Windows 11.
* **💾 Persistência ORM:** Uso do **Hibernate** para mapeamento objeto-relacional, eliminando a necessidade de SQL manual e garantindo independência de banco de dados.
* **🌱 Database Seeder:** Sistema de "Migration" automática que popula o banco de dados com 1.100 alunos fictícios e cria um usuário administrador padrão na primeira execução.
* **⚡ Banco em Memória (H2):** A aplicação roda sem necessidade de instalação de SGBD externo, criando o banco automaticamente ao iniciar.

---

## 🚀 Como Instalar e Rodar o Projeto

Siga os passos abaixo para executar a aplicação em sua máquina local.

### Pré-requisitos

* **Java JDK 23** instalado (o projeto utiliza recursos modernos da linguagem).
* **Maven** instalado (ou utilize o wrapper se sua IDE fornecer).
* **Git** (opcional, para clonar o repositório).
* **IDE Recomendada:** IntelliJ IDEA (Community ou Ultimate).

### Passo a Passo

1.  **Clone o repositório** (ou baixe o ZIP):
    ```bash
    git clone [https://github.com/seu-usuario/DualListHibernate.git](https://github.com/seu-usuario/DualListHibernate.git)
    ```

2.  **Abra o projeto na IDE:**
    * No IntelliJ IDEA, vá em `File` > `Open` e selecione a pasta do projeto.
    * Aguarde a IDE reconhecer o arquivo `pom.xml` e baixar as dependências do Maven automaticamente.

3.  **Configure o SDK (Se necessário):**
    * Vá em `File` > `Project Structure` > `Project`.
    * Certifique-se de que o **SDK** está definido para a versão **23**.

4.  **Execute a Aplicação:**
    * Navegue até `src/main/java/br/com/projeto/MainApp.java`.
    * Clique no ícone de "Play" verde ao lado do método `main`.

### 🔑 Acesso ao Sistema

Na primeira execução, o sistema criará automaticamente um usuário administrador.

* **Usuário:** `admin`
* **Senha:** `1234`

---

## 🛠️ Requisitos e Regras de Negócio

A lógica do sistema foi modelada para garantir consistência nas matrículas e segurança no acesso.

### Regras de Negócio (RN)
<div style="width: 100%; overflow-x: auto;">
  <table width="100%" style="border-collapse: collapse; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.1); font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;">
    <thead style="background-color: #ED8B00; color: white;">
      <tr>
        <th style="padding: 12px 15px; text-align: left;">ID</th>
        <th style="padding: 12px 15px; text-align: left;">Ator</th>
        <th style="padding: 12px 15px; text-align: left;">Descrição da regra</th>
        <th style="padding: 12px 15px; text-align: left;">Justificativa</th>
      </tr>
    </thead>
    <tbody style="background-color: #fff; color: #333;">
      <tr style="border-bottom: 1px solid #ddd; background-color: #f9f9f9;">
        <td style="padding: 12px 15px;">RN-001</td>
        <td style="padding: 12px 15px;">Sistema</td>
        <td style="padding: 12px 15px;">A matrícula de um aluno é definida por um estado booleano (`matriculado = true`) persistido no banco.</td>
        <td style="padding: 12px 15px;">Simplicidade e eficiência na query de separação das listas.</td>
      </tr>
      <tr style="border-bottom: 1px solid #ddd;">
        <td style="padding: 12px 15px;">RN-002</td>
        <td style="padding: 12px 15px;">Sistema</td>
        <td style="padding: 12px 15px;">Na primeira execução, se não houver usuários, deve-se criar automaticamente o usuário `admin` com senha `1234`.</td>
        <td style="padding: 12px 15px;">Garantir acesso imediato ao sistema sem configuração manual.</td>
      </tr>
      <tr style="border-bottom: 1px solid #ddd; background-color: #f9f9f9;">
        <td style="padding: 12px 15px;">RN-003</td>
        <td style="padding: 12px 15px;">Usuário</td>
        <td style="padding: 12px 15px;">O campo "Matrícula" do aluno deve ser único no banco de dados.</td>
        <td style="padding: 12px 15px;">Garantir a unicidade do registro acadêmico.</td>
      </tr>
      <tr style="border-bottom: 1px solid #ddd;">
        <td style="padding: 12px 15px;">RN-004</td>
        <td style="padding: 12px 15px;">Sistema</td>
        <td style="padding: 12px 15px;">Senhas de novos usuários devem ser criptografadas via BCrypt antes da persistência.</td>
        <td style="padding: 12px 15px;">Segurança básica contra vazamento de dados.</td>
      </tr>
      <tr style="border-bottom: 1px solid #ddd; background-color: #f9f9f9;">
        <td style="padding: 12px 15px;">RN-005</td>
        <td style="padding: 12px 15px;">Usuário</td>
        <td style="padding: 12px 15px;">A exclusão de um aluno é permanente e remove o registro do banco de dados.</td>
        <td style="padding: 12px 15px;">Limpeza de dados (Hard Delete) conforme requisito da atividade.</td>
      </tr>
    </tbody>
  </table>
</div>

### Requisitos Funcionais (RF)
<div style="width: 100%; overflow-x: auto;">
  <table width="100%" style="border-collapse: collapse; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.1); font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;">
    <thead style="background-color: #ED8B00; color: white;">
      <tr>
        <th style="padding: 12px 15px; text-align: left;">ID</th>
        <th style="padding: 12px 15px; text-align: left;">Módulo</th>
        <th style="padding: 12px 15px; text-align: left;">Nome do requisito</th>
        <th style="padding: 12px 15px; text-align: left;">Prioridade</th>
      </tr>
    </thead>
    <tbody style="background-color: #fff; color: #333;">
      <tr style="border-bottom: 1px solid #ddd; background-color: #f9f9f9;">
        <td style="padding: 12px 15px;">RF-001</td>
        <td style="padding: 12px 15px;">Autenticação</td>
        <td style="padding: 12px 15px;">O sistema deve permitir login e cadastro de novos administradores.</td>
        <td style="padding: 12px 15px;">Essencial</td>
      </tr>
      <tr style="border-bottom: 1px solid #ddd;">
        <td style="padding: 12px 15px;">RF-002</td>
        <td style="padding: 12px 15px;">Gestão Acadêmica</td>
        <td style="padding: 12px 15px;">O sistema deve permitir criar, editar e excluir alunos (CRUD).</td>
        <td style="padding: 12px 15px;">Essencial</td>
      </tr>
      <tr style="border-bottom: 1px solid #ddd; background-color: #f9f9f9;">
        <td style="padding: 12px 15px;">RF-003</td>
        <td style="padding: 12px 15px;">Matrícula</td>
        <td style="padding: 12px 15px;">O usuário deve poder mover alunos entre a lista de "Disponíveis" e "Matriculados".</td>
        <td style="padding: 12px 15px;">Essencial</td>
      </tr>
      <tr style="border-bottom: 1px solid #ddd;">
        <td style="padding: 12px 15px;">RF-004</td>
        <td style="padding: 12px 15px;">Matrícula</td>
        <td style="padding: 12px 15px;">Ao clicar em "Finalizar Matrícula", o status dos alunos deve ser salvo no banco.</td>
        <td style="padding: 12px 15px;">Essencial</td>
      </tr>
      <tr style="border-bottom: 1px solid #ddd; background-color: #f9f9f9;">
        <td style="padding: 12px 15px;">RF-005</td>
        <td style="padding: 12px 15px;">Interface</td>
        <td style="padding: 12px 15px;">A lista deve exibir um ícone (avatar) ao lado do nome do aluno.</td>
        <td style="padding: 12px 15px;">Média</td>
      </tr>
    </tbody>
  </table>
</div>

### Requisitos Não-Funcionais (RNF)
<div style="width: 100%; overflow-x: auto;">
  <table width="100%" style="border-collapse: collapse; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.1); font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;">
    <thead style="background-color: #ED8B00; color: white;">
      <tr>
        <th style="padding: 12px 15px; text-align: left;">ID</th>
        <th style="padding: 12px 15px; text-align: left;">Atributo</th>
        <th style="padding: 12px 15px; text-align: left;">Descrição</th>
      </tr>
    </thead>
    <tbody style="background-color: #fff; color: #333;">
      <tr style="border-bottom: 1px solid #ddd; background-color: #f9f9f9;">
        <td style="padding: 12px 15px;">RNF-001</td>
        <td style="padding: 12px 15px;">Usabilidade</td>
        <td style="padding: 12px 15px;">A interface deve utilizar o tema FlatLaf para visual moderno e responsivo.</td>
      </tr>
      <tr style="border-bottom: 1px solid #ddd;">
        <td style="padding: 12px 15px;">RNF-002</td>
        <td style="padding: 12px 15px;">Portabilidade</td>
        <td style="padding: 12px 15px;">O banco de dados deve ser H2 (Embedded) para rodar sem instalação prévia.</td>
      </tr>
      <tr style="border-bottom: 1px solid #ddd; background-color: #f9f9f9;">
        <td style="padding: 12px 15px;">RNF-003</td>
        <td style="padding: 12px 15px;">Manutenibilidade</td>
        <td style="padding: 12px 15px;">O código deve seguir estritamente o padrão MVC e usar Generics no componente visual.</td>
      </tr>
    </tbody>
  </table>
</div>

---

## 💻 Ambiente de Desenvolvimento

O projeto foi configurado para garantir compatibilidade e facilidade de execução em qualquer ambiente Java moderno.

<div style="width: 100%; overflow-x: auto;">
  <table width="100%" style="border-collapse: collapse; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.1); font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;">
    <thead style="background-color: #444; color: white;">
      <tr>
        <th style="padding: 12px 15px; text-align: left;">Categoria</th>
        <th style="padding: 12px 15px; text-align: left;">Ferramenta</th>
        <th style="padding: 12px 15px; text-align: left;">Versão</th>
        <th style="padding: 12px 15px; text-align: left;">Propósito</th>
      </tr>
    </thead>
    <tbody style="background-color: #fff; color: #333;">
      <tr style="border-bottom: 1px solid #ddd; background-color: #f9f9f9;">
        <td style="padding: 12px 15px;">IDE</td>
        <td style="padding: 12px 15px;"><strong>IntelliJ IDEA</strong></td>
        <td style="padding: 12px 15px;">2024.x</td>
        <td style="padding: 12px 15px;">Ambiente de desenvolvimento integrado (Community ou Ultimate).</td>
      </tr>
      <tr style="border-bottom: 1px solid #ddd;">
        <td style="padding: 12px 15px;">Linguagem</td>
        <td style="padding: 12px 15px;"><strong>Java JDK</strong></td>
        <td style="padding: 12px 15px;">23</td>
        <td style="padding: 12px 15px;">Versão mais recente do Java para aproveitar novos recursos de sintaxe.</td>
      </tr>
      <tr style="border-bottom: 1px solid #ddd; background-color: #f9f9f9;">
        <td style="padding: 12px 15px;">Build Tool</td>
        <td style="padding: 12px 15px;"><strong>Maven</strong></td>
        <td style="padding: 12px 15px;">3.8+</td>
        <td style="padding: 12px 15px;">Gerenciamento de dependências e ciclo de vida do projeto.</td>
      </tr>
    </tbody>
  </table>
</div>

---

## 🚀 Stack Tecnológica e Justificativa

A escolha das tecnologias visou unir a robustez do desenvolvimento Enterprise com a agilidade de configuração acadêmica.

<div style="width: 100%; overflow-x: auto;">
  <table width="100%" style="border-collapse: collapse; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.1); font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;">
    <thead style="background-color: #444; color: white;">
      <tr>
        <th style="padding: 12px 15px; text-align: left;">Tecnologia</th>
        <th style="padding: 12px 15px; text-align: left;">Versão</th>
        <th style="padding: 12px 15px; text-align: left;">Por que foi escolhida?</th>
      </tr>
    </thead>
    <tbody style="background-color: #fff; color: #333;">
      <tr style="border-bottom: 1px solid #ddd; background-color: #f9f9f9;">
        <td style="padding: 12px 15px;"><strong>Java Swing</strong></td>
        <td style="padding: 12px 15px;">Nativa</td>
        <td style="padding: 12px 15px;">
          <strong>Padrão da Indústria (Legacy/Desktop):</strong> Embora antigo, o Swing é a biblioteca gráfica padrão do Java. Para modernizá-lo, aplicamos padrões de design atuais e customização via código (Graphics2D).
        </td>
      </tr>
      <tr style="border-bottom: 1px solid #ddd;">
        <td style="padding: 12px 15px;"><strong>Hibernate ORM</strong></td>
        <td style="padding: 12px 15px;">6.4.1</td>
        <td style="padding: 12px 15px;">
          <strong>Abstração de Dados:</strong> Permite manipular o banco de dados utilizando objetos Java puros, facilitando a manutenção e a troca de banco de dados se necessário, além de prevenir SQL Injection nativamente.
        </td>
      </tr>
      <tr style="border-bottom: 1px solid #ddd; background-color: #f9f9f9;">
        <td style="padding: 12px 15px;"><strong>FlatLaf</strong></td>
        <td style="padding: 12px 15px;">3.2.5</td>
        <td style="padding: 12px 15px;">
          <strong>Modernização Visual:</strong> O Swing padrão tem aparência datada ("Metal"). O FlatLaf traz uma aparência "Flat", limpa e profissional, similar aos sistemas operacionais modernos, sem custo de performance.
        </td>
      </tr>
      <tr style="border-bottom: 1px solid #ddd;">
        <td style="padding: 12px 15px;"><strong>H2 Database</strong></td>
        <td style="padding: 12px 15px;">2.2.224</td>
        <td style="padding: 12px 15px;">
          <strong>Portabilidade:</strong> Banco de dados em memória ou arquivo que roda embutido na aplicação. Permite que o professor/avaliador rode o projeto sem precisar instalar MySQL ou PostgreSQL na máquina.
        </td>
      </tr>
      <tr style="border-bottom: 1px solid #ddd; background-color: #f9f9f9;">
        <td style="padding: 12px 15px;"><strong>jBCrypt</strong></td>
        <td style="padding: 12px 15px;">0.4</td>
        <td style="padding: 12px 15px;">
          <strong>Segurança:</strong> Biblioteca padrão para hashing de senhas. Garante que, mesmo que o banco seja comprometido, as senhas dos usuários não sejam expostas.
        </td>
      </tr>
    </tbody>
  </table>
</div>

---

## 🔒 Segurança

A aplicação, embora acadêmica, implementa conceitos reais de segurança da informação.

<div style="width: 100%; overflow-x: auto;">
  <table width="100%" style="border-collapse: collapse; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.1); font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;">
    <thead style="background-color: #444; color: white;">
      <tr>
        <th style="padding: 12px 15px; text-align: left;">Tópico</th>
        <th style="padding: 12px 15px; text-align: left;">Implementação</th>
        <th style="padding: 12px 15px; text-align: left;">Justificativa</th>
      </tr>
    </thead>
    <tbody style="background-color: #fff; color: #333;">
      <tr style="border-bottom: 1px solid #ddd; background-color: #f9f9f9;">
        <td style="padding: 12px 15px;"><strong>Hashing de Senhas</strong></td>
        <td style="padding: 12px 15px;"><strong>BCrypt</strong> com Salt automático.</td>
        <td style="padding: 12px 15px;">
          Ao contrário do MD5 ou SHA simples, o BCrypt é lento propositalmente, tornando ataques de força bruta inviáveis. O uso de Salt impede ataques de <em>Rainbow Table</em>.
        </td>
      </tr>
      <tr style="border-bottom: 1px solid #ddd;">
        <td style="padding: 12px 15px;"><strong>Transação Atômica</strong></td>
        <td style="padding: 12px 15px;">Hibernate Transactions</td>
        <td style="padding: 12px 15px;">
          Todas as operações de escrita (salvar aluno, matricular) ocorrem dentro de transações. Se algo falhar no meio do processo, o <code>rollback</code> garante que o banco não fique inconsistente.
        </td>
      </tr>
    </tbody>
  </table>
</div>

---

## 💡 Notas de Arquitetura

* **Componente Genérico:** A classe `DualListSelector<T>` foi projetada usando **Generics**. Isso significa que ela não serve apenas para "Alunos". Com poucas linhas, ela poderia ser reutilizada para selecionar "Produtos", "Funcionários" ou qualquer outra entidade do sistema.
* **Custom Renderer:** Para exibir o ícone do "bonequinho" na lista, não usamos imagens fixas. Implementamos um `ListCellRenderer` personalizado que desenha o ícone vetorialmente via código (Java 2D API), garantindo que ele nunca fique pixelado ou dependa de arquivos externos.
* **Seeder Automático:** A classe `DatabaseSeeder` verifica a cada inicialização se o banco está vazio. Se estiver, gera 1.100 alunos com nomes aleatórios e matricula 100 deles automaticamente, facilitando testes de carga e visualização.

---

## 👨‍💻 Autor

<div style="width: 100%; overflow-x: auto;">
  <table width="100%" style="border-collapse: collapse; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.1); font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f9f9f9;">
    <tr>
      <td style="padding: 20px; width: 100px; text-align: center;">
        <img src="https://avatars.githubusercontent.com/u/142981329?v=4" width="90" alt="Avatar do Victor" style="border-radius: 50%;">
      </td>
      <td style="padding: 20px; color: #333;">
        <strong style="font-size: 1.3em; color: #ED8B00;">Victor Henrique Jesus Santiago</strong><br>
        Desenvolvedor Full Stack<br><br>
        📧 <a href="mailto:victorhenriquedejesussantiago@gmail.com" style="color: #ED8B00; text-decoration: none;">victorhenriquedejesussantiago@gmail.com</a><br>
        👔 <a href="https://www.linkedin.com/in/victor-henrique-de-jesus-santiago/" style="color: #ED8B00; text-decoration: none;">LinkedIn/victorhjsantiago</a><br>
        🐙 <a href="https://github.com/VictorHJesusSantiago" style="color: #ED8B00; text-decoration: none;">GitHub/VictorHJesusSantiago</a>
      </td>
    </tr>
  </table>
</div>