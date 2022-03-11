INSERT INTO player(email, name, profile_pic)
VALUES ('player1@email.com', 'player1name', 'urlprofilepic'),
       ('player2@email.com', 'player2name', 'urlprofilepic');

INSERT INTO player(name, email)
VALUES ('player3name','player3mail@gmail.com');

INSERT INTO game(name, genre,coverImage)
VALUES ('Horizon Forbidden West','Adventure',null),
       ('Lost Ark','Action-MMORPG',null),
       ('Enter the Gungeon','Roguelike',null);

INSERT INTO achievement(gameId, name, description)
VALUES (3,'Gungeon Acolyte','Complete the Tutorial')