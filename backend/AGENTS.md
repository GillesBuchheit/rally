# Backend agent instructions

## Lombok

Use Lombok consistently to remove unnecessary Java boilerplate.

- Prefer `@RequiredArgsConstructor` with `final` fields for dependency injection.
- Add `@NonNull` to required dependencies, parameters, fields, and record components.
- Prefer generated accessors such as `@Getter` over handwritten getters.
- For JPA entities, prefer `@NoArgsConstructor(access = AccessLevel.PROTECTED)`,
  package-scoped generated accessors, and a generated constructor for required
  fields.
- Use annotations such as `@EqualsAndHashCode` for value objects and composite
  identifiers when their generated semantics match the domain.
- Avoid broad annotations such as `@Data` when they would expose setters or
  generate equality semantics that are inappropriate for a domain object or
  JPA entity.
- Keep domain invariants and business validation explicit when Lombok cannot
  express them clearly or would change the expected exception semantics.
