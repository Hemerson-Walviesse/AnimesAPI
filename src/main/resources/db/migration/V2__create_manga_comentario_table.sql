CREATE TABLE manga (
                       mal_id INT PRIMARY KEY,
                       title VARCHAR(255),
                       type VARCHAR(100),
                       chapters INT,
                       score DOUBLE,
                       image_url VARCHAR(512),
                       comentario VARCHAR(1000),
                       synopsis VARCHAR(10000)
);

CREATE TABLE comentario_manga (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            texto VARCHAR(1000),
                            manga_id INT,
                            data_criacao TIMESTAMP,
                            CONSTRAINT fk_manga FOREIGN KEY (manga_id) REFERENCES manga(mal_id)
);
