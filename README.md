# Refeisoft-api
<p>
  Este projeto é um backend para gerenciar o acesso de alunos ao refeitório de uma faculdade. Ele oferece funcionalidades para administração de alunos, controle de créditos,               monitoramento de transações, restrições de acesso e gerenciamento de usuários administradores. 
</p>

# Funcionalidades 
<h3>Gerenciamento de Usuários Administradores</h3>

- Cadastro de usuários: Permite o usuário administrador registrar novos usuários com acesso administrativo ao sistema.
- Listagem de usuários: Exibe todos os usuários cadastrados com privilégios administrativos.
- Atualização e exclusão: Possibilidade de alterar ou remover usuários administradores existentes.
- Responsabilidades dos administradores:
  - Gerenciar os alunos cadastrados.
  - Controlar os créditos dos alunos (adição e consulta).
  - Monitorar transações de crédito.
  - Aplicar ou remover bloqueios temporários aos alunos.

<h3>Gerenciamento de Alunos</h3>

- Cadastro de alunos: Permite registrar novos alunos no sistema.
- Listagem de alunos: Exibe todos os alunos cadastrados.
- Atualização de dados: Possibilidade de alterar informações dos alunos existentes.
- Exclusão de alunos: Remove alunos do sistema.

<h3>Controle de Créditos</h3>

- Adição de créditos: Os administradores podem adicionar créditos para o aluno.
- Consulta de créditos: Permite que os administradores verifiquem a quantidade atual de créditos de um aluno.
- Consumo de créditos:
  - Realizado diretamente pelo aluno no momento do acesso ao refeitório.
  - O aluno informa seu RA (Registro Acadêmico) e o tipo de refeição (café da manhã, almoço ou jantar).
  - Um crédito é consumido, permitindo a entrada no refeitório.
  - Caso o aluno esteja bloqueado ou com créditos insuficientes, uma mensagem de erro é exibida.

<h3>Gerenciamento de Bloqueios</h3>

- Bloqueio temporário de alunos:
  - Administradores podem bloquear o acesso de um aluno até uma data específica.
  - Durante o bloqueio, o aluno não pode adicionar ou consumir créditos.
- Desbloqueio automático:
  - Uma tarefa agendada executa diariamente às 00:00h.
  - Verifica os alunos bloqueados e desbloqueia automaticamente aqueles cuja data de bloqueio tenha expirado.
- Desbloqueio manual:
  - Administradores podem desbloquear o aluno manuealmente antes da data especificada de bloqueio.
  
<h3>Registro de Transações</h3>

- Listagem de transações:
  - Exibe o histórico de adição e consumo de créditos.
  - Administradores podem filtrar transações por:
    - Nome do aluno.
    - Tipo de refeição.
    - Tipo de transação (adição ou consumo).
    - Período de datas.

<h2> Tecnologias utilizadas </h2>
<p>
  🔹<strong> Java 17 </strong> <br>
  🔹<strong> Spring Boot </strong> <br>
  🔹<strong> Spring Data JPA </strong> <br>
  🔹<strong> Spring Security </strong> <br>
  🔹<strong> JWT Token Authentication </strong> <br>
  🔹<strong> PostgreSQL </strong> <br>
  🔹<strong> OpenAPI documentation - (swagger) </strong> <br>
  🔹<strong> Maven </strong><br>
</p>

<h2>Requisitos</h2>

- Necessário ter o Java 17 e o PostgreSQL instalados em sua máquina.

<h2>Instalação</h2>

- Clone o repositório:

```bash
git clone https://github.com/MarcosSilva13/refeisoft-api.git
```
- Abra a pasta blog-api na IDE IntelliJ e instale as dependências com o Maven.<br>
- É necessário criar o banco de dados utilizando o script sql `refeisoft_db.sql` que se encontra no projeto na `src/main/resources`.
- Ajuste o `application-dev.properties` com suas configurações de banco de dados e credenciais.
- Abra o arquivo `RefeisoftApiApplication.java` e pressione `Shift+F10` para executar, ou clique no icone ▶️ de execução.
- A aplicação estará disponível no endereço: `http://localhost:8080` ou `http://localhost:8080/swagger-ui/index.html` (para visualizar com o swagger).
