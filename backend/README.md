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

## Domaine Compétition

Le premier modèle se trouve dans `fr.rally.competition` :

- `domain` contient les ligues, les joueurs, les inscriptions et le sport, sans
  dépendance à Spring ou à JPA ;
- `application` contient les cas d’usage et les ports de persistance ;
- `infrastructure` contient les adaptateurs JPA et leur configuration Spring.

Une ligue appartient à un identifiant de compte (`ownerId`) et concerne un seul
sport. Un joueur possède un identifiant propre, indépendant de son nom et du
compte utilisateur. La table `league_registrations` relie les joueurs aux ligues
avec une clé primaire `(league_id, player_id)` : le même joueur peut rejoindre
plusieurs ligues, mais une seule fois chacune.

La migration Flyway `V1__create_competition_tables.sql` crée ces tables dans le
schéma configuré par `DATABASE_SCHEMA` (`rally` par défaut). Les tests exécutent
la migration sur H2 et valident le schéma avec Hibernate.

Il n’y a pas encore de contrôleur HTTP ni d’authentification. Les cas d’usage
reçoivent un `actorId` pour les opérations sur les inscriptions et vérifient
qu’il correspond au propriétaire de la ligue. Lors de l’ajout de l’API, cet
identifiant devra provenir d’une identité authentifiée, jamais d’un champ
librement fourni par le client.
