INSERT INTO users(id, created_at, updated_at, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled, password, user_role, username) VALUES (10001, '2022-10-07 10:00:00.000', '2022-10-07 10:00:00.000', true, true, true, true, 'password', 'USER',  'james');
INSERT INTO users(id, created_at, updated_at, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled, password, user_role, username) VALUES (10002, '2022-10-07 10:00:00.000', '2022-10-07 10:00:00.000', true, true, true, true, 'password', 'ADMIN', 'bayzat');
INSERT INTO users(id, created_at, updated_at, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled, password, user_role, username) VALUES (10003, '2022-10-07 10:00:00.000', '2022-10-07 10:00:00.000', true, true, true, true, 'password', 'USER',  'jennifer');

INSERT INTO currency(id, created_at, updated_at, current_price, enabled, name, symbol) VALUES (10001, '2022-10-07 10:00:00.000', '2022-10-07 10:00:00.000', 10000.00, true,  'Cardano',  'ADA');
INSERT INTO currency(id, created_at, updated_at, current_price, enabled, name, symbol) VALUES (10002, '2022-10-07 10:00:00.000', '2022-10-07 10:00:00.000', 20000.00, true,  'Bitcoin',  'BTC');
INSERT INTO currency(id, created_at, updated_at, current_price, enabled, name, symbol) VALUES (10003, '2022-10-07 10:00:00.000', '2022-10-07 10:00:00.000', 30000.00, true,  'Ripple',   'XRP');
INSERT INTO currency(id, created_at, updated_at, current_price, enabled, name, symbol) VALUES (10004, '2022-10-07 10:00:00.000', '2022-10-07 10:00:00.000', 40000.00, true,  'Cronos',   'CRO');
INSERT INTO currency(id, created_at, updated_at, current_price, enabled, name, symbol) VALUES (10005, '2022-10-07 10:00:00.000', '2022-10-07 10:00:00.000', 50000.00, false, 'Cosmos',   'ATOM');

INSERT INTO alert(id, created_at, updated_at, status, target_price, currency_id, user_id) VALUES (10001, '2022-10-07 10:00:00.000', '2022-10-07 10:00:00.000', 'NEW',       11000.00, 10001, 10001);
INSERT INTO alert(id, created_at, updated_at, status, target_price, currency_id, user_id) VALUES (10002, '2022-10-07 10:00:00.000', '2022-10-07 10:00:00.000', 'ACKED',     12000.00, 10001, 10001);
INSERT INTO alert(id, created_at, updated_at, status, target_price, currency_id, user_id) VALUES (10003, '2022-10-07 10:00:00.000', '2022-10-07 10:00:00.000', 'NEW',       13000.00, 10002, 10002);
INSERT INTO alert(id, created_at, updated_at, status, target_price, currency_id, user_id) VALUES (10004, '2022-10-07 10:00:00.000', '2022-10-07 10:00:00.000', 'NEW',       33000.00, 10003, 10002);
INSERT INTO alert(id, created_at, updated_at, status, target_price, currency_id, user_id) VALUES (10005, '2022-10-07 10:00:00.000', '2022-10-07 10:00:00.000', 'ACKED',     45000.00, 10004, 10002);
INSERT INTO alert(id, created_at, updated_at, status, target_price, currency_id, user_id) VALUES (10006, '2022-10-07 10:00:00.000', '2022-10-07 10:00:00.000', 'CANCELLED', 50000.00, 10004, 10002);
INSERT INTO alert(id, created_at, updated_at, status, target_price, currency_id, user_id) VALUES (10007, '2022-10-07 10:00:00.000', '2022-10-07 10:00:00.000', 'TRIGGERED', 9000.00,  10001, 10002);
INSERT INTO alert(id, created_at, updated_at, status, target_price, currency_id, user_id) VALUES (10008, '2022-10-07 10:00:00.000', '2022-10-07 10:00:00.000', 'TRIGGERED', 19000.00, 10002, 10003);
INSERT INTO alert(id, created_at, updated_at, status, target_price, currency_id, user_id) VALUES (10009, '2022-10-07 10:00:00.000', '2022-10-07 10:00:00.000', 'TRIGGERED', 29000.00, 10003, 10003);
INSERT INTO alert(id, created_at, updated_at, status, target_price, currency_id, user_id) VALUES (10010, '2022-10-07 10:00:00.000', '2022-10-07 10:00:00.000', 'TRIGGERED', 39000.00, 10004, 10003);

INSERT INTO parameter(id, created_at, updated_at, key, value) VALUES (1, '2022-10-07 10:00:00.000', '2022-10-07 10:00:00.000', 'UNSUPPORTED_CURRENCIES', 'ETH,LTC,ZKN,MRD,LPR,GBZ');