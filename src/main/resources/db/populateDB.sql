DELETE FROM user_role;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@gmail.com', 'password'),
       ('Admin', 'admin@gmail.com', 'password');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2023-06-19 10:00:00', 'breakfast',1000,100000),
       ('2023-06-19 15:10:00', 'lunch', 600, 100000),
       ('2023-06-19 20:00:00', 'dinner', 500, 100000),
       ('2023-06-20 11:20:00', 'breakfast', 1200, 100001),
       ('2023-06-20 16:30:00', 'dinner', 700, 100001);

SELECT description, SUM(calories) sum FROM MEALS
GROUP BY description
HAVING Sum(calories) > 1000;

Select * from users u natural join meals m;