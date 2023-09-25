 INSERT INTO customer ("name", phone, email, street, city, state, country, profile_pic , created_at) VALUES
    ('Ram','1234567899','ram@abc.com', '5th Avenue','Downtown','LA', 'US', './pic1', LOCALTIMESTAMP(0)),
    ('John','1234567889','john@abc.com','42nd and 3rd', 'Lake Street','Boston', 'US','./pic2', LOCALTIMESTAMP(0));

INSERT INTO category (name, parent_category) VALUES
    ('FMCG', null),
    ('SOFT DRINK', 1),
    ('CARBONATED DRINK', 1),
    ('Shampoo', null),
    ('Food', null);

INSERT INTO product( name, image, description, unit_price, category_id) VALUES
    ('Pepsi', './sample', 'soft drink',60, 2),
    ('CocaCola', './sample','soft drink',60, 2),
    ('Oreo', './sample', 'Food',60, 5),
    ('Head and Shoulders', './sample', 'Shampoo',60, 4),
    ('Maggi', './sample', 'noodles',25, 5);

INSERT INTO orders ( status, total_quantity, total_price, customer_id, created_at) VALUES
	( 'IN_TRANSIT', 4, 100, 1, LOCALTIMESTAMP(0)),
    ( 'DELIVERED', 2, 135, 1, LOCALTIMESTAMP(0)),
    ( 'IN_TRANSIT', 6, 445, 2, LOCALTIMESTAMP(0));

INSERT INTO order_item (order_id, product_id, qty, price, row_total) VALUES
    (1, 4, 4, 10, 40),
    (2, 3, 2, 20, 40),
    (2, 2, 1, 50, 50),
    (1, 1, 1, 20, 20);