# LyricsFlow Framework
Este repositório contém a implementação do projeto [LiricsFlow](https://github.com/JuhGuilhermino/LyricsFlow) a partir de um framework.
O framework para sistemas educacionais foiconstruido a partir da refatoração do projeto original, como trabalho da disiciplina DIM0162 - Engenharia de Software, no ano de 2026.

## Framework
### Estrutura do Backend
**ATUALIZAR!!!!**
```
 └── src/main/java/com/example/lyricsflowfw/
    ├── core/                    -> Diretório do framework
    │   ├── diretorio1/          ->
    │   └── diretororio2/        ->
    │
    ├── app/                     -> Aplicação que utiliza o framework
    │   ├── diretorio1/          ->
    │   └── diretororio2/        ->
```
### Diagrama de Classe UML

## Como o Executar o LyricsFlow
As instruções abaixo são para o sistema operacional Windows.
### Pré-requsitos
* Java JDK 17 ou superior.
* PostgreSQL ativo (com o banco de dados do projeto criado)
* Uma chave de API do Gemini configurada nas suas variáveis de ambiente.
### Backend
Apra o terminal na raiz do projeto (que contém o arquivo `pom.xml`) e utize os seguintes comandos:
```powershell
# Limpar cache antigo e compilar o código do zero
.\mvnw clean compile

# Informar a chave da API do Gemini antes de rodar a aplicação
$env:GENAI_API_KEY="SUA-CHAVE”

# Rodar aplicação: http://localhost:8080
.\mvnw spring-boot:run
```

### Frontend

## To-do
* [ ] Refatorar pontos fixos
* [ ] Refatorar pontos variáveis

## Autoras
* [Júlia Maria Azevedo Guilhermino](https://github.com/JuhGuilhermino) - juh.guilhermino03@gmail.com
* [Ludmilla Rorigues](https://github.com/Ludrodrigues) - ludmillarodr178@gmail.com