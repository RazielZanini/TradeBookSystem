CREATE TABLE notifications (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
message VARCHAR(255) NOT NULL,
readStatus BOOLEAN not null,
trade_id BIGINT,
user_id BIGINT,
user_book_id bigint
);

CREATE INDEX idx_notifications_user_id
ON notifications (user_id);

CREATE INDEX idx_notifications_trade_id
ON notifications (trade_id);

CREATE INDEX idx_notifications_user_read
ON notifications (user_id, readStatus);