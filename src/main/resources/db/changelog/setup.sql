CREATE TABLE player(
    id INT NOT NULL generated always as identity,
    email VARCHAR(30) NOT NULL,
    name VARCHAR (30) NOT NULL,
    profile_pic VARCHAR(50),
    PRIMARY KEY (id),
    UNIQUE(email)
);

CREATE TABLE game(
    id INT NOT NULL generated always as identity,
    name varchar(30) NOT NULL,
    coverImage varchar(50),
    genre varchar (15) NOT NULL,
    PRIMARY KEY  (id)
);

CREATE TABLE achievement(
    id INT NOT NULL generated always as identity,
    name varchar(30) NOT NULL,
    description varchar(50) NOT NULL,
    picture varchar(50),
    gameId INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (gameId) references game(id)
);

CREATE TABLE ownership(
    playerId INT NOT NULL,
    gameId INT NOT NULL,
    PRIMARY KEY (playerId,gameId),
    FOREIGN KEY (playerId) REFERENCES player(id),
    FOREIGN KEY (gameId) REFERENCES game(id)
);

CREATE TABLE unlocks(
    playerId INT NOT NULL,
    achievementId INT NOT NULL,
    PRIMARY KEY (playerId,achievementId),
    FOREIGN KEY (playerId) REFERENCES player(id),
    FOREIGN KEY (achievementId) REFERENCES achievement(id)
);