# Rally

Rally est une application composée d'une API Spring Boot et d'une PWA React.

## Backend

Le backend se trouve dans [`backend`](backend). Il utilise Spring Boot 4,
Java 25, Maven et Lombok.

```bash
cd backend
mvn spring-boot:run
```

## Frontend

Le frontend se trouve dans [`frontend`](frontend). Il utilise React,
TypeScript, shadcn, TanStack Router, TanStack Query et Vite PWA.

```bash
cd frontend
npm install
npm run dev
```

Le serveur de développement est disponible sur `http://localhost:5173` et
redirige les requêtes `/api` vers le backend sur `http://localhost:8080`.

Pour vérifier le projet avant un commit :

```bash
npm run check
```

## Licence

Ce projet est distribué sous la licence Apache 2.0. Consultez le fichier
[`LICENSE`](LICENSE) pour plus de détails.
