CREATE TABLE "player"(
    id INT NOT NULL generated always as identity,
    email VARCHAR(30) NOT NULL,
    name VARCHAR (30) NOT NULL,
    profile_pic VARCHAR(300),
    PRIMARY KEY (id),
    UNIQUE(email)
);

CREATE TABLE "game"(
    id INT NOT NULL generated always as identity,
    name varchar(30) NOT NULL,
    cover_image varchar(300),
    genre varchar (15) NOT NULL,
    PRIMARY KEY  (id)
);

CREATE TABLE "achievement"(
    id INT NOT NULL generated always as identity,
    name varchar(30) NOT NULL,
    description varchar(50) NOT NULL,
    picture varchar(300),
    game_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (game_id) references game(id)
);

CREATE TABLE "ownership"(
    player_id INT NOT NULL,
    game_id INT NOT NULL,
    PRIMARY KEY (player_id,game_id),
    FOREIGN KEY (player_id) REFERENCES player(id),
    FOREIGN KEY (game_id) REFERENCES game(id)
);

CREATE TABLE "unlocks"(
    player_id INT NOT NULL,
    achievement_id INT NOT NULL,
    PRIMARY KEY (player_id,achievement_id),
    FOREIGN KEY (player_id) REFERENCES player(id),
    FOREIGN KEY (achievement_id) REFERENCES achievement(id)
);