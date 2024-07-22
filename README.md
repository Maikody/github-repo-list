# GitHub Repository List API

## Opis

Aplikacja Spring Boot, która udostępnia API REST do pobierania nie-forkowanych repozytoriów użytkownika GitHub wraz z informacjami o gałęziach i najnowszymi SHA commitów dla każdej gałęzi.

## Technologie

- Java 21
- Spring Boot
- Lombok
- JUnit 5
- Mockito
- Maven

## Funkcjonalności

- Aplikacja pozwala na pobieranie listy repozytoriów użytkownika GitHub, które nie są forkami.
- Dla każdego repozytorium, aplikacja zwraca listę gałęzi wraz z informacjami o najnowszych commitach (SHA).

## Endpoints

- `GET /repositories/{username}` - Zwraca nie-forkowane repozytoria użytkownika GitHub wraz z informacjami o gałęziach i najnowszymi SHA commitów dla każdej gałęzi.
- `GET /actuator/health` - Endpoint do sprawdzania stanu zdrowia aplikacji.

## Konfiguracja

Plik `application.properties` zawiera bazowy URL API GitHub:
```properties
github.api.base.url=https://api.github.com
```

## Budowanie projektu

Aby zbudować projekt, wykonaj poniższe polecenie w katalogu głównym projektu:
```bash
./mvnw clean package
```

## Uruchamianie aplikacji
### Lokalne uruchomienie

* Za pomocą Maven:
    ```bash
    ./mvnw spring-boot:run
* Za pomocą IDE:
    - Uruchom klasę `GithubRepoListApplication` w swoim IDE.

Aplikacja będzie dostępna pod adresem http://localhost:8080.

### Uruchomienie w Dockerze
Aby uruchomić aplikację w kontenerze Docker, wykonaj poniższe kroki:

* Zbuduj obraz Dockera:
    ```bash
    docker build -t github-repo-list .

* Uruchom kontener:
    ```bash
    docker run -p 8080:8080 github-repo-list
Aplikacja będzie dostępna pod adresem http://localhost:8080.

## Testowanie
### Uruchamianie testów jednostkowych

Aby uruchomić testy jednostkowe, użyj poniższego polecenia:
```bash
./mvnw test
```

## Dokumentacja API
Po uruchomieniu aplikacji możesz sprawdzić dokumentację API, przechodząc do http://localhost:8080/swagger-ui.html.
