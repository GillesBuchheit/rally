/* oxlint-disable react/only-export-components -- TanStack Router route modules export Route. */
import { useSuspenseQuery } from '@tanstack/react-query'
import { createFileRoute } from '@tanstack/react-router'
import {
  CircleCheck,
  DatabaseZap,
  RefreshCw,
  Route as RouteIcon,
  Smartphone,
  Sparkles,
} from 'lucide-react'

import { Badge } from '@/components/ui/badge'
import { Button } from '@/components/ui/button'
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from '@/components/ui/card'
import { Separator } from '@/components/ui/separator'
import { setupQueryOptions } from '@/features/setup/setup-query'

export const Route = createFileRoute('/')({
  loader: ({ context }) => context.queryClient.ensureQueryData(setupQueryOptions),
  component: HomePage,
})

const foundations = [
  {
    title: 'Interface prête',
    description: 'React 19, TypeScript, Tailwind CSS et les composants shadcn.',
    icon: Sparkles,
  },
  {
    title: 'Navigation typée',
    description: 'TanStack Router gère les routes et leur chargement.',
    icon: RouteIcon,
  },
  {
    title: 'Données maîtrisées',
    description: 'TanStack Query centralise le cache et les appels à l’API.',
    icon: DatabaseZap,
  },
  {
    title: 'Installation PWA',
    description: 'Manifest, icônes et service worker sont déjà configurés.',
    icon: Smartphone,
  },
]

function HomePage() {
  const setup = useSuspenseQuery(setupQueryOptions)

  return (
    <main className="app-background min-h-svh px-5 py-8 sm:px-8 lg:px-12 lg:py-12">
      <div className="mx-auto flex w-full max-w-6xl flex-col gap-8">
        <header className="flex items-center justify-between">
          <div className="flex items-center gap-3">
            <div className="grid size-10 place-items-center rounded-xl bg-primary text-lg font-semibold text-primary-foreground shadow-sm">
              R
            </div>
            <div>
              <p className="text-lg font-semibold leading-none">Rally</p>
              <p className="mt-1 text-xs text-muted-foreground">Application web progressive</p>
            </div>
          </div>
          <Badge variant="outline" className="bg-card/70 backdrop-blur-sm">
            <CircleCheck data-icon="inline-start" />
            Socle prêt
          </Badge>
        </header>

        <Separator />

        <section className="grid items-end gap-8 py-6 lg:grid-cols-[1.4fr_0.6fr] lg:py-14">
          <div className="max-w-3xl space-y-5">
            <Badge variant="secondary">Configuration terminée</Badge>
            <h1 className="font-heading text-4xl font-semibold tracking-tight text-balance sm:text-6xl lg:text-7xl">
              Votre prochain rallye commence ici.
            </h1>
            <p className="max-w-2xl text-base leading-7 text-muted-foreground sm:text-lg">
              Le frontend est prêt à accueillir les premiers parcours métier et à communiquer
              avec l’API Spring Boot.
            </p>
          </div>

          <Card className="bg-card/80 shadow-xl shadow-slate-950/5 backdrop-blur-sm">
            <CardHeader>
              <CardDescription>État de l’application</CardDescription>
              <CardTitle className="flex items-center gap-2 text-xl">
                <span className="size-2.5 rounded-full bg-emerald-500" />
                Opérationnelle
              </CardTitle>
            </CardHeader>
            <CardContent className="space-y-4">
              <p className="text-sm leading-6 text-muted-foreground">
                Vérifié à {new Date(setup.data.checkedAt).toLocaleTimeString('fr-FR')}.
              </p>
              <Button
                variant="outline"
                className="w-full"
                onClick={() => void setup.refetch()}
                disabled={setup.isFetching}
              >
                <RefreshCw
                  data-icon="inline-start"
                  className={setup.isFetching ? 'animate-spin' : undefined}
                />
                Vérifier la configuration
              </Button>
            </CardContent>
          </Card>
        </section>

        <section aria-labelledby="foundations-title" className="space-y-4">
          <div>
            <p className="text-sm font-medium text-muted-foreground">Fondations</p>
            <h2 id="foundations-title" className="mt-1 text-2xl font-semibold tracking-tight">
              Les outils essentiels sont en place
            </h2>
          </div>
          <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-4">
            {foundations.map((foundation) => {
              const Icon = foundation.icon

              return (
                <Card key={foundation.title} size="sm" className="bg-card/75 backdrop-blur-sm">
                  <CardHeader>
                    <div className="mb-3 grid size-9 place-items-center rounded-lg bg-accent text-accent-foreground">
                      <Icon className="size-4" aria-hidden="true" />
                    </div>
                    <CardTitle>{foundation.title}</CardTitle>
                    <CardDescription className="leading-5">{foundation.description}</CardDescription>
                  </CardHeader>
                </Card>
              )
            })}
          </div>
        </section>
      </div>
    </main>
  )
}
