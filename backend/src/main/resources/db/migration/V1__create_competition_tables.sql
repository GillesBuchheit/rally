create table leagues (
    id uuid primary key,
    name varchar(120) not null,
    sport varchar(16) not null check (sport in ('TENNIS', 'PADEL')),
    owner_id uuid not null
);

create index leagues_owner_id_idx on leagues (owner_id);

create table players (
    id uuid primary key,
    display_name varchar(120) not null
);

create table league_registrations (
    league_id uuid not null references leagues (id),
    player_id uuid not null references players (id),
    joined_at timestamp with time zone not null,
    primary key (league_id, player_id)
);

create index league_registrations_player_id_idx on league_registrations (player_id);
