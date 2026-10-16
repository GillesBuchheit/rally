# Instructions for agents

## Commit messages

Always use the [Conventional Commits](https://www.conventionalcommits.org/)
specification for every Git commit.

Use this format:

```text
<type>(<optional scope>): <description>
```

Allowed types include `feat`, `fix`, `docs`, `style`, `refactor`, `perf`,
`test`, `build`, `ci`, `chore`, and `revert`.

Keep the description concise, use the imperative mood, start it with a
lowercase letter, and do not end it with a period.

Examples:

```text
feat(backend): add club creation endpoint
fix(api): validate empty club names
chore: configure Maven compiler for Java 25
```

Mark breaking changes with `!` before the colon or with a
`BREAKING CHANGE:` footer.
