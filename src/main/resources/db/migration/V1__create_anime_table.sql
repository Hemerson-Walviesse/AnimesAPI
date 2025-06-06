CREATE TABLE anime (
                       mal_id INT PRIMARY KEY,
                       title VARCHAR(255),
                       type VARCHAR(100),
                       episodes INT,
                       score DOUBLE,
                       image_url VARCHAR(512),
                       comentario VARCHAR(1000),
                       synopsis VARCHAR(10000)
);
CREATE TABLE comentario_anime (
                                 id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                 texto VARCHAR(1000),
                                 anime_id INT,
                                 data_criacao TIMESTAMP,
                                 ativo boolean,
                                 CONSTRAINT fk_anime FOREIGN KEY (anime_id) REFERENCES anime(mal_id)
);