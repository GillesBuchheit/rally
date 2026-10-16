# Frontend Rally

PWA React de Rally, construite avec TypeScript et Vite.

## Prérequis

- Node.js 24 ou une version LTS récente
- npm 11 ou une version compatible

## Démarrer

```bash
npm install
npm run dev
```

L’application est disponible sur `http://localhost:5173`. Les appels à `/api`
sont redirigés vers `http://localhost:8080` pendant le développement.

## Commandes

```bash
npm run dev                 # serveur de développement
npm run check               # lint, build PWA et vérification TypeScript
npm run preview             # prévisualisation du build de production
npm run generate:pwa-assets # régénération des icônes depuis public/rally.svg
```

## Organisation

- `src/routes` contient les routes TanStack Router.
- `src/features` contient les fonctionnalités métier et leurs requêtes.
- `src/components/ui` contient les composants shadcn.
- `src/router-context.ts` expose les dépendances partagées avec les routes.
