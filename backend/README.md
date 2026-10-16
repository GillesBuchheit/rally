# Rally API

Backend de l'application Rally, basé sur Spring Boot 4 et Java 25.

## Prérequis

- JDK 25
- Maven 3.6.3 ou supérieur

## Démarrer l'application

```bash
mvn spring-boot:run
```

L'API démarre sur `http://localhost:8080`.

## Lancer les tests

```bash
mvn test
```

## Construire l'application

```bash
mvn clean package
```

Le code métier sera organisé par domaine fonctionnel selon les principes de la
Clean Architecture et du Domain-Driven Design.
