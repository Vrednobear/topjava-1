DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS meals;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name             VARCHAR                           NOT NULL,
    email            VARCHAR                           NOT NULL,
    password         VARCHAR                           NOT NULL,
    registered       TIMESTAMP           DEFAULT now() NOT NULL,
    enabled          BOOL                DEFAULT TRUE  NOT NULL,
    calories_per_day INTEGER             DEFAULT 2000  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_role
(
    user_id INTEGER NOT NULL,
    role    VARCHAR NOT NULL,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE meals
(
    id          INTEGER DEFAULT nextval('global_seq'),
    date_time   TIMESTAMP NOT NULL,
    description VARCHAR   NOT NULL,
    calories    INTEGER   NOT NULL,
    user_id     INTEGER   NOT NULL,
    CONSTRAINT meals_idx PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX meals_unique_date_time ON meals (user_id, date_time);

SELECT * FROM meals WHERE user_id=100000 AND (date_time>='2023-06-19 09:00:00.000000' AND date_time<'2023-06-19 23:00:00.000000');