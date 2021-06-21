CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  email VARCHAR(100) NOT NULL,
  first_name VARCHAR(100),
  last_name VARCHAR(100),
  password VARCHAR(100)
);


CREATE TABLE games (
  id SERIAL PRIMARY KEY,
  first_player BIGINT,
  second_player BIGINT,
  status VARCHAR(50),
  winner BIGINT,
  loser BIGINT,
  createdDate TIMESTAMP,
  completion_date TIMESTAMP
)