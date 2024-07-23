CREATE TABLE IF NOT EXISTS Posts
(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    writer_id BIGINT,
    content TEXT,
    postStatus VARCHAR(50),
    created TIMESTAMP,
    updated TIMESTAMP,
    FOREIGN KEY (writer_id) REFERENCES Writers (id)
    );