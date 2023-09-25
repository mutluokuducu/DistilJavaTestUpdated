CREATE TABLE IF NOT EXISTS stock_daily
(
    id     BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    ticker VARCHAR(255) NOT NULL,
    volume INTEGER,
    open   DOUBLE PRECISION,
    low    DOUBLE PRECISION,
    high   DOUBLE PRECISION,
    close  DOUBLE PRECISION,
    date   DATE
);
CREATE UNIQUE INDEX IF NOT EXISTS unique_stock_daily ON stock_daily (ticker, date);

