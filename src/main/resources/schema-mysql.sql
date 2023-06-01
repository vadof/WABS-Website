CREATE TABLE IF NOT EXISTS topics (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(255),
                        color VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS patches (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         topic BIGINT,
                         patch DECIMAL(10,2),
                         title VARCHAR(100),
                         description TEXT,
                         release_date DATETIME,
                         published BOOLEAN,
                         image BIGINT,
                         FOREIGN KEY (topic) REFERENCES topics(id),
                         FOREIGN KEY (image) REFERENCES images(id)
);

CREATE TABLE IF NOT EXISTS images (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(255),
                        originalFileName VARCHAR(255),
                        size BIGINT,
                        contentType VARCHAR(255),
                        bytes LONGBLOB
);

CREATE TABLE IF NOT EXISTS players_statistics (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        playerId BIGINT,
                        total_games INT,
                        victories INT,
                        defeats INT,
                        kd DECIMAL(10,2),
                        kills INT,
                        damage_dealt INT,
                        deaths INT,
                        damage_received INT,
                        FOREIGN KEY (playerId) REFERENCES players(id)
);

CREATE TABLE IF NOT EXISTS players (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        username VARCHAR(255) UNIQUE,
                        password VARCHAR(255),
                        player_statistics BIGINT,
                        player_email BIGINT,
                        registered DATE,
                        FOREIGN KEY (player_statistics) REFERENCES players_statistics(id),
                        FOREIGN KEY (player_email) REFERENCES emails(id)
);

CREATE TABLE IF NOT EXISTS player_match_statistics (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        match_id BIGINT,
                        player_id BIGINT,
                        kills INT,
                        damage_dealt INT,
                        deaths INT,
                        damage_received INT,
                        FOREIGN KEY (match_id) REFERENCES matches(id),
                        FOREIGN KEY (player_id) REFERENCES players(id)
);

CREATE TABLE IF NOT EXISTS matches (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        win BOOLEAN,
                        date DATETIME
);

CREATE TABLE IF NOT EXISTS emails (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        email VARCHAR(255) UNIQUE,
                        updates BOOLEAN
);

CREATE TABLE IF NOT EXISTS faqs (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        question VARCHAR(255),
                        answer TEXT
);