DROP TABLE IF EXISTS lending;

CREATE TABLE lending (
    id INT AUTO_INCREMENT PRIMARY KEY,
    max_return_date DATE NOT NULL,
    isbn VARCHAR(50) NOT NULL,
    user_id VARCHAR(100) NOT NULL,
    user_type VARCHAR(50) NOT NULL
);

CREATE INDEX idx_user_id ON lending(user_id);
CREATE INDEX idx_max_return_date ON lending(max_return_date);