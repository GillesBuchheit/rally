# Rally API

Backend de l'application Rally, basé sur Spring Boot 4 et Java 25.

## Prérequis

- JDK 25
- Maven 3.6.3 ou supérieur

## Démarrer l'application

L’API utilise PostgreSQL avec Spring Data JPA et Flyway. La connexion cible par
défaut le projet Supabase Rally. Créez d’abord votre fichier local :

```bash
cp .env.example .env
```

Renseignez ensuite `DATABASE_PASSWORD` dans `.env`, puis lancez l’application :

```bash
mvn spring-boot:run
```

La configuration complète est décrite dans [`.env.example`](.env.example).
Le fichier `.env` est chargé localement par Spring Boot et ignoré par Git. En
production, définissez plutôt ces valeurs dans le gestionnaire de secrets de la
plateforme d’hébergement.

La configuration par défaut utilise le **Session pooler** Supabase sur le port
`5432`, compatible avec les réseaux IPv4. Si les informations de connexion du
projet changent, copiez-les depuis le bouton **Connect** du dashboard Supabase.
Le pooler transactionnel sur le port `6543` ne convient pas à Hibernate.

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
