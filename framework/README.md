# LyricsFlow Backend
Este repositório contém a implementação de um framework para sistemas educaionais, criado a partir do projeto [LyricsFlow - Sistema de Aprendizado de Idiomas](https://github.com/JuhGuilhermino/lyricsflow_backend).
Ele foi desenvolvido para um trabalho da disiciplina DIM0162 - Engenharia de Software, no ano de 2026.

## Estrutura do Projeto
```
framework/learningcore
 ├── config/            -> Auto-configuração do Spring Boot
 ├── domain/            -> Pontos fixos: entidades base
 ├── engine/            -> Pontos fixos: motor do sitema
 ├── ports/             -> Postos flexíveis: interces
 └── repository/        -> Persistência fixa
```

## Diagrama de Classes

**[COLOCAR UMA FOTO DO DIGRAMA ATUALIZADO AQUI]**

## Como Testar Localmente
Para disponbilizar esse framework para outras aplicações locais. Abra o terminal e execute o comando: `.\mvnw clean install`.

#### Pré-requsitos

#### Compilação e Execução

## To-do
Distribuição da refatoração das classes:
**Ludmilla**
* [x] UserProgress.java
* [x] Flashcard.java
* [ ] UserProgressRepository.java
* [ ] FlashcardRepository.java
* [ ] DashboardService.java
* [ ] FlashcardService.java
* [ ] GeminiClient.java -> *método referente aos flashcards*
* [ ] DeashboardController.java
* [ ] FlashcardController.java
* [ ] DashboardDTO.java
* [ ] FlashcardAnswerDTO.java
* [ ] FlashcardDetailsResponseDTO.java


**Júlia Guilhermino**
* [x] User.java
* [x] Song.java
* [x] Task.java
* [ ] UserRepository.java
* [ ] SongRepository.java
* [ ] TaskRepository.java
* [ ] UserService.java
* [ ] SongService.java
* [ ] TaskService.java
* [ ] GeminiClient.java -> *método referente as tarefas*
* [ ] AuthController.java
* [ ] TaskController.java
* [ ] LoginRequestDTO.java
* [ ] MusicRequestDTO.java
* [ ] MusicResponseDTO.java
* [ ] TaskGenerateResponseDTO.java
* [ ] TaskStartRequestDTO.java
* [ ] TaskSubmissionDTO.java
* [ ] UserRegisterRequestDTO.java
* [ ] UserRegisterResponseDTO.java
* [ ] UserResponseDTO.java

## Autoras
* [Júlia Maria Azevedo Guilhermino](https://github.com/JuhGuilhermino) - juh.guilhermino03@gmail.com
* [Ludmilla Rorigues](https://github.com/Ludrodrigues) - ludmillarodr178@gmail.com
