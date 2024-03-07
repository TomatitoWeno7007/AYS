INSERT INTO `genres`(`id`, `name`)
VALUES ('1','Comedia'),
('2','Drama'),
('3','Terror'),
('4','Fantasía'),
('5','Ciencia Ficción'),
('6','Aventura'),
('7','Animación'),
('8','Misterio');


INSERT INTO `film`(`id`, `description`, `duration`, `img`, `name`, `rating`, `views`, `url`)
VALUES ('1','Basada en el genero de terror','1:30:26','nohay','Terror terrorio','2',0,'30'),
('2','Basada en el genero de comedia','1:00:30','nohay','Comedia comediosa','1',0,'47'),
('3','Cuando un niño descubre una piedra, pasan cosas','2:36:11','nohay','Harry Potter','5',0,'102'),
('4','Basada en el genero de comedia','1:30:26','nohay','Spiderman','2',0,'30'),
('5','Basada en el genero de comedia','1:00:30','nohay','Spiderman 2','1',0,'47'),
('6','Basada en el genero de comedia','2:36:11','nohay','Spiderman 3','5',0,'102'),
('7','El hombre verde','1:00:30','nohay','Hulk','1',0,'47'),
('8','Miedo por todas partes','2:36:11','nohay','Basada en el genero de comedia','5',0,'102'),
('9','Muchos perros por la ciudad','1:30:26','nohay','101 Dalmatas','2',0,'30'),
('10','Comedia comediosa','1:00:30','nohay','La vida de Pi','1',0,'47'),
('11','Miedo por todas partes','2:36:11','nohay','Spiderman 4','5',0,'102');

INSERT INTO `film_genre`(`film_id`, `genre_id`) VALUES ('1','6'),
('1','8'),
('2','8'),
('3','1'),
('3','2'),
('3','3'),
('4','8'),
('5','8'),
('6','1'),
('6','2'),
('7','8'),
('8','8'),
('8','1'),
('8','2'),
('9','8'),
('10','7'),
('11','1');


INSERT INTO `serie`(`id`, `description`, `img`, `name`, `rating`, `views`)
VALUES ('1','Trata de resolver misterios','pop','Sherlock Holmes','5','260'),
('2','Sherlock descubre a Watson','','Vivaldi','5','192'),
('3','Ash compra a Pikachu','','Pokemon 62','1','22'),
('4','La encrucijada','','Grandes','5','192'),
('5','El salvajismo','','Visigodos','5','260'),
('6','Sherlock descubre a Moriarty','','Aztecas','5','192'),
('7','Ash compra a Charizard','','Francia','1','22'),
('8','Ash compra a Squitrle','','Toledo','1','22'),
('9','Ash compra a Venusaur','','Traicion','1','22'),
('10','Sherlock pelea con Moriarty p1','','Venganza','5','192'),
('11','Sherlock pelea con Moriarty p2','','Todo','5','192'),
('12','Ash pierde la liga','','Nada','1','22');


INSERT INTO `serie_genre`(`serie_id`, `genre_id`) VALUES ('1','6'),
('1','8'),('2','8'),
('3','1'),('3','2'),('3','3'),
('4','8'),('5','8'),('6','1'),
('6','2'),('7','8'),('8','8'),
('8','1'),('8','2'),('9','8'),
('10','7'),('11','1'),('12','2');


INSERT INTO `card` (`id`, `card_number`, `card_user`, `cvv`, `expiration_date`) VALUES
(1, NULL, NULL, NULL, NULL),
(2, NULL, NULL, NULL, NULL),
(3, NULL, NULL, NULL, NULL),
(4, '987654', 'Montesito', '123', '2024-02-15'),
(5, NULL, NULL, NULL, NULL);


INSERT INTO `user` (`id`, `admin`, `date_birth`, `email`, `img`, `last_name`, `name`, `password`, `second_last_name`, `card_id`) VALUES
(1, b'1', NULL, 'jose@jose.com', NULL, NULL, NULL, '123', NULL, NULL),
(2, b'0', NULL, 'user@user.com', NULL, NULL, NULL, '123', NULL, 1),
(3, b'0', NULL, 'user2@user.com', NULL, NULL, NULL, '123', NULL, 2),
(4, b'0', NULL, 'fran@fran.com', NULL, NULL, NULL, '123', NULL, 3),
(5, b'1', NULL, 'admin@admin.com', NULL, NULL, NULL, '123', NULL, NULL),
(6, b'0', '2017-02-05', 'user3@user.com', NULL, NULL, NULL, '123', NULL, 4),
(7, b'0', NULL, 'averaver@ahas.com', NULL, NULL, NULL, '123', NULL, 5);


INSERT INTO `chapter` (`id`, `duration`, `img`, `name`, `number`, `synopsis`, `url`) VALUES
(1, '22:36', 'nohay', 'El Comienzo', 1, 'Parece que empieza la aventura de una forma inusual', NULL),
(2, '19:56', 'nohay', 'Primera Pista', 2, 'Después del primer asesinato, parece que se encuentran con la bestia.', NULL),
(3, '20:13', 'nohay', 'Mi primer Pokemon', 1, 'Ash empieza la aventura de capturar a los 4398116 pokemon.', NULL),
(4, '22:36', 'nohay', 'El Amanecer', 3, 'Amanece de forma sorprendente', NULL),
(5, '22:36', 'nohay', 'Ahora Bulbasaur', 2, 'Por fin obtiene al inicial', NULL);

INSERT INTO `season` (`id`, `number`) VALUES
(1, 1),
(2, 2),
(3, 1);

INSERT INTO `season_chapters` (`season_id`, `chapters_id`) VALUES
(1, 1),
(1, 2),
(3, 3),
(2, 4),
(3, 5);

INSERT INTO `serie_seasons` (`serie_id`, `seasons_id`) VALUES
(1, 1),
(1, 2),
(3, 3);
