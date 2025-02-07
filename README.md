# MarathonRio (Personal Marathon)

## 📌 Sobre o Projeto

**Personal Marathon** é um projeto backend desenvolvido para a **Maratona do Rio de Janeiro 2025**, com o objetivo de auxiliar corredores a registrarem e monitorarem seus treinos durante o período de preparação. O sistema permite que os usuários registrem seus treinos, incluindo os **treinos longos**, calculem automaticamente o **pace** e acompanhem sua evolução nas **21 semanas** até a prova, que ocorrerá no dia **22 de junho de 2025**.

## 🚀 Tecnologias

O projeto foi desenvolvido utilizando as seguintes tecnologias:

- **Spring Boot** (Java)
- **MySQL** (Banco de dados relacional)
- **Docker** (Para containerização)
- **JUnit** (Para testes automatizados)

## 🌆 Funcionalidades do Backend

- **Cadastro de Usuários**: Registra corredores e gerencia seus dados.
- **Registro de Treinos**: Acompanha os treinos, incluindo cálculos de pace.
- **Cálculo Automático de Pace**: Calcula o pace com base no tempo e distância de cada treino.
- **Treinos Longos**: Gerencia e registra treinos longos durante o período de preparação.

## 📦 Instalação e Uso

### 1. Clone o repositório:
```bash
git clone https://github.com/Jonathan-eng-jur/MarathonRio.git
 ```
### 2. Acesse o diretório do projeto:
   ```sh
   cd riodejaneiro
   ```
### 3. Construa e inicie os containers Docker:
   ```sh
   docker-compose up --build -d
   ```
### 4. Inicie o servidor de desenvolvimento:
- **Usuário: root** (Java)
- **Senha: (deixe em branco se não configurado)**
- **Banco de dados: marathon**

### 5. Rodando os testes:

Para rodar os testes automatizados, use o seguinte comando
   ```sh
   ./mvnw test
   ```
Este comando executa os testes definidos no projeto, garantindo que o sistema esteja funcionando corretamente.
## 📌 Contribuição

1. Faça um **fork** do projeto.
2. Crie uma **branch** para sua feature (`git checkout -b feature/nova-feature`).
3. Commit suas alterações seguindo o padrão (`git commit -m "feat: descrição da mudança"`).
4. Envie para o repositório remoto (`git push origin feature/nova-feature`).
5. Abra um **Pull Request**.


