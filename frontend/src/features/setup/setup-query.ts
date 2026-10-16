import { queryOptions } from '@tanstack/react-query'

export const setupQueryOptions = queryOptions({
  queryKey: ['application', 'setup'],
  queryFn: () =>
    Promise.resolve({
      status: 'ready' as const,
      checkedAt: new Date().toISOString(),
    }),
  staleTime: Number.POSITIVE_INFINITY,
})
