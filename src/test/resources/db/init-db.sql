-- create user table
create schema leon_web;
create table leon_web.users
(
    id uuid default gen_random_uuid() not null
        primary key,
    username varchar(50) not null
        unique,
    password text not null,
    email varchar(100) not null
        unique,
    enabled boolean default true,
    locked boolean default false,
    role varchar(30) not null,
    created_at timestamp default now(),
    updated_at timestamp default now()
);
-- insert user data
INSERT INTO leon_web.users (id, username, password, email, enabled, locked, role, created_at, updated_at) VALUES ('9094abc9-f14b-434b-88a0-e6bf71eef08c', 'ADMIN', '$argon2id$v=19$m=16384,t=2,p=1$5VxqLCDWyWS0/upvouegpw$lCmpz/DiZOsRrPkXKvEOc6zTrCFYxZ4AF7fJaXjFrK4', 'admin@gmail.com', true, false, 'GAY', '2025-07-01 16:29:25.878007', '2025-07-01 16:29:25.878007');
INSERT INTO leon_web.users (id, username, password, email, enabled, locked, role, created_at, updated_at) VALUES ('b70385a5-57d7-42d6-9408-343512c1e7f7', 'USER', '$argon2id$v=19$m=16384,t=2,p=1$gpYYpyl0W8snA1FEkQfZXA$a2xBriVHbRiUipImiMgkYRP1VhnQL19/cuz81FP6wqc', 'user@gmail.com', true, false, 'USER', '2025-08-05 09:05:16.295014', '2025-08-05 09:05:16.295014');
INSERT INTO leon_web.users (id, username, password, email, enabled, locked, role, created_at, updated_at) VALUES ('b70385a5-57d7-42d6-9408-343512c1e7f8', 'USER2', '$argon2id$v=19$m=16384,t=2,p=1$gpYYpyl0W8snA1FEkQfZXA$a2xBriVHbRiUipImiMgkYRP1VhnQL19/cuz81FP6wq8', 'user2@gmail.com', true, false, 'USER', '2025-08-05 09:05:16.295014', '2025-08-05 09:05:16.295014');
INSERT INTO leon_web.users (id, username, password, email, enabled, locked, role, created_at, updated_at) VALUES ('b70385a5-57d7-42d6-9408-343512c1e7f2', 'USER3', '$argon2id$v=19$m=16384,t=2,p=1$gpYYpyl0W8snA1FEkQfZXA$a2xBriVHbRiUipImiMgkYRP1VhnQL19/cuz81FP6wq8', 'user3@gmail.com', true, false, 'USER', '2025-08-05 09:05:16.295014', '2025-08-05 09:05:16.295014');

