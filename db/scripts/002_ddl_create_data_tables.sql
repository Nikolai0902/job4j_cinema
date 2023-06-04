INSERT INTO files (name, path) VALUES ('tirist', 'files/1.JPG');
INSERT INTO files (name, path) VALUES ('avatar', 'files/2.JPG');
INSERT INTO files (name, path) VALUES ('operation', 'files/3.JPG');

INSERT INTO genres (name) VALUES ('action movie');
INSERT INTO genres (name) VALUES ('fantasy');
INSERT INTO genres (name) VALUES ('comedy');

INSERT INTO films (name, description, "year", genre_id, minimal_age, duration_in_minutes, file_id) VALUES ('tirist','an action movie about a spy', 2012, 1, 21, 180, 1);
INSERT INTO films (name, description, "year", genre_id, minimal_age, duration_in_minutes, file_id) VALUES ('avatar','sci-fi movie', 2009, 2, 18, 120, 2);
INSERT INTO films (name, description, "year", genre_id, minimal_age, duration_in_minutes, file_id) VALUES ('operation','comedy of the USSR', 1970, 3, 16, 120, 3);

INSERT INTO halls (name, row_count, place_count, description) VALUES ('Hall 1', 10, 50, 'Hall 3d');
INSERT INTO halls (name, row_count, place_count, description) VALUES ('Hall 2', 5, 25, 'Hall 4d');

INSERT INTO film_sessions (film_id, halls_id, start_time, end_time, price) VALUES (1, 1, '2023-10-10 12:00:30', '2023-10-10 15:00:30', 300);
INSERT INTO film_sessions (film_id, halls_id, start_time, end_time, price) VALUES (2, 2, '2023-10-10 12:00:30', '2023-10-10 14:00:30', 250);
INSERT INTO film_sessions (film_id, halls_id, start_time, end_time, price) VALUES (3, 1, '2023-10-10 15:30:30', '2023-10-10 17:30:30', 100);
INSERT INTO film_sessions (film_id, halls_id, start_time, end_time, price) VALUES (1, 2, '2023-10-10 14:30:30', '2023-10-10 17:30:30', 500);
INSERT INTO film_sessions (film_id, halls_id, start_time, end_time, price) VALUES (3, 2, '2023-10-10 18:00:30', '2023-10-10 20:00:30', 300);