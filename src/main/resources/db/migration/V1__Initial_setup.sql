DROP TABLE IF EXISTS order_tickets;
DROP TABLE IF EXISTS ratings;
DROP TABLE IF EXISTS activity_logs;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS users;

DROP TYPE IF EXISTS ticket_status;
DROP TYPE IF EXISTS order_status;
DROP TYPE IF EXISTS event_categories;
DROP TYPE IF EXISTS user_roles;

CREATE TYPE event_categories AS ENUM (
  'MUSIC',
  'THEATRE',
  'SPORTS',
  'CONFERENCE',
  'WORKSHOP',
  'FESTIVAL',
  'EXHIBITION',
  'SEMINAR',
  'WEBINAR',
  'COMPETITION'
);

CREATE TYPE ticket_status AS ENUM (
  'AVAILABLE',
  'RESERVED',
  'SOLD',
  'CANCELLED'
);

CREATE TYPE order_status AS ENUM (
  'PENDING',
  'COMPLETED',
  'CANCELLED',
  'REFUNDED'
);

CREATE TYPE user_roles AS ENUM (
  'ADMIN',
  'USER'
);

CREATE TABLE users (
  id UUID PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  age INT NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  role user_roles NOT NULL,
  phone_number VARCHAR(20),
  address VARCHAR(255),
  is_active BOOLEAN DEFAULT TRUE NOT NULL,
  created_at TIMESTAMP DEFAULT NOW() NOT NULL,
  updated_at TIMESTAMP DEFAULT NOW() NOT NULL
);

CREATE TABLE events (
  id UUID PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  description TEXT NOT NULL,
  location VARCHAR(255) NOT NULL,
  start_date TIMESTAMP NOT NULL,
  end_date TIMESTAMP NOT NULL,
  category event_categories NOT NULL,
  organizer_id UUID NOT NULL REFERENCES users(id),
  max_capacity INT NOT NULL,
  remaining_capacity INT NOT NULL,
  status VARCHAR(50) NOT NULL DEFAULT 'Scheduled',
  price DECIMAL(10, 2) NOT NULL,
  created_at TIMESTAMP DEFAULT NOW() NOT NULL,
  updated_at TIMESTAMP DEFAULT NOW() NOT NULL
);

CREATE TABLE tickets (
  id UUID PRIMARY KEY,
  event_id UUID NOT NULL REFERENCES events(id),
  price DECIMAL(10, 2) NOT NULL,
  seat_number VARCHAR(20),
  purchase_date TIMESTAMP DEFAULT NOW() NOT NULL,
  ticket_status ticket_status NOT NULL,
  qr_code VARCHAR(255) NOT NULL,
  batch_number INT NOT NULL,
  created_at TIMESTAMP DEFAULT NOW() NOT NULL,
  updated_at TIMESTAMP DEFAULT NOW() NOT NULL
);

CREATE TABLE orders (
  id UUID PRIMARY KEY,
  user_id UUID NOT NULL REFERENCES users(id),
  total_amount DECIMAL(10, 2) NOT NULL,
  order_date TIMESTAMP DEFAULT NOW() NOT NULL,
  order_status order_status NOT NULL,
  payment_method VARCHAR(50) NOT NULL,
  created_at TIMESTAMP DEFAULT NOW() NOT NULL,
  updated_at TIMESTAMP DEFAULT NOW() NOT NULL
);

CREATE TABLE order_tickets (
  order_id UUID NOT NULL REFERENCES orders(id),
  ticket_id UUID NOT NULL REFERENCES tickets(id),
  quantity INT NOT NULL,
  PRIMARY KEY (order_id, ticket_id)
);

CREATE TABLE ratings (
  id UUID PRIMARY KEY,
  event_id UUID NOT NULL REFERENCES events(id),
  user_id UUID NOT NULL REFERENCES users(id),
  rating DECIMAL(2, 1) NOT NULL,
  review TEXT,
  created_at TIMESTAMP DEFAULT NOW() NOT NULL,
  updated_at TIMESTAMP DEFAULT NOW() NOT NULL
);

CREATE TABLE activity_logs (
  id UUID PRIMARY KEY,
  user_id UUID NOT NULL REFERENCES users(id),
  activity TEXT NOT NULL,
  ip_address VARCHAR(45) NOT NULL,
  created_at TIMESTAMP DEFAULT NOW() NOT NULL
);