CREATE TABLE IF NOT EXISTS app_user
(
    id                 int PRIMARY KEY AUTO_INCREMENT,
    created_account_at datetime(6),
    last_login         datetime(6),
    password           varchar(255),
    username           varchar(255)
);

CREATE TABLE IF NOT EXISTS app_role
(
    id   int PRIMARY KEY AUTO_INCREMENT,
    name varchar(255)
);

CREATE TABLE IF NOT EXISTS user_roles
(
    user_id int,
    role_id int
);


CREATE TABLE IF NOT EXISTS shape
(
    dtype            varchar(31),
    id               int PRIMARY KEY AUTO_INCREMENT,
    create_at        datetime(6),
    last_modified_at datetime(6),
    version          int NOT NULL,
    radius           double,
    height           double,
    length           double,
    width            double,
    created_by_id    int,
    modified_by_id   int
);

CREATE OR REPLACE VIEW all_shapes AS
SELECT s.id,
       s.dtype,
       s.create_at,
       s.last_modified_at,
       s.version,
       s.radius,
       s.height,
       s.length,
       s.width,
       s.created_by_id,
       s.modified_by_id,
       u.username AS created_by,
       u.username AS last_modified_by
FROM shape s
         JOIN app_user u ON s.created_by_id = u.id
         JOIN app_user us ON s.modified_by_id = us.id;

CREATE OR REPLACE VIEW shape_with_area_and_perimeter AS
SELECT s.id,
       s.dtype,
       s.create_at,
       s.last_modified_at,
       s.version,
       s.radius,
       s.height,
       s.length,
       s.width,
       s.created_by_id,
       s.modified_by_id,
       u.username  AS created_by,
       us.username AS last_modified_by,
       CASE
           WHEN s.dtype = 'Circle' THEN 2 * pi() * s.radius
           WHEN s.dtype = 'Rectangle' THEN 2 * s.length + 2 * s.width
           WHEN s.dtype = 'Square' THEN 4 * s.width
           END     AS perimeter,
       CASE
           WHEN s.dtype = 'Circle' THEN pi() * s.radius * s.radius
           WHEN s.dtype = 'Rectangle' THEN s.length * s.width
           WHEN s.dtype = 'Square' THEN s.width * s.width
           END     AS area
FROM shape s
         JOIN app_user u ON s.created_by_id = u.id
         JOIN app_user us ON s.modified_by_id = us.id;

CREATE OR REPLACE VIEW all_user AS
SELECT app_user.id,
       app_user.password,
       app_user.username,
       app_user.created_account_at,
       app_user.last_login,
       COUNT(s.id) AS createdShape
FROM app_user
         LEFT JOIN shape s ON app_user.id = s.created_by_id
GROUP BY app_user.id, app_user.username, app_user.password, app_user.created_account_at, app_user.last_login;
