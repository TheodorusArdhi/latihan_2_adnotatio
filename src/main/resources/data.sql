INSERT INTO Regions (Name)
VALUES
    ('North'),
    ('East'),
    ('West'),
    ('South');

INSERT INTO Deliveries (Id, Name, Cost)
VALUES
    ('EXD', 'Express Delivery', 5000),
    ('NOD', 'North Delivery', 10000);

INSERT INTO Customers (First_Name, Last_Name, Phone_Number, Email, Deleted)
VALUES
    ('Alice', 'Johnson', '0812-3456-781', 'alice@gmail.com', false),
    ('Ben', 'Kornel', '0812-3456-782', 'ben@gmail.com', false),
    ('Charles', 'Lee', '0812-3456-783', 'charles@gmail.com', false);

INSERT INTO Products (Name, Price, Stock, Deactivated)
VALUES
    ('Keyboard', 400000, 9, false),
    ('Mouse', 100000, 9, false),
    ('Monitor', 100000, 5, false),
    ('Laptop', 7000000, 3, false),
    ('Pearbook Mini', 10000000, 2, true);

INSERT INTO Orders (Order_Date, Arrival_Date, Customer_Id, Delivery_Id, Delivery_Fee, Total_Amount)
VALUES
    ('2020-01-03', '2020-01-01', 1, 'EXD', 5000, 500000);

INSERT INTO Order_Details (Order_Id, Product_Id, Price, Quantity)
VALUES
    (1, 1, 400000, 1),
    (1, 2, 100000, 1);


-- =========================================================================
-- DUMMY DATA UNTUK TABEL CARTS (Entitas Perantara Customers <-> Products)
-- =========================================================================
INSERT INTO carts (customer_id, product_id, quantity, added_date) VALUES
                                                                      (1, 1, 2, CURRENT_TIMESTAMP), -- Alice (Customer 1) memasukkan 2 Keyboard ke keranjang
                                                                      (1, 2, 1, CURRENT_TIMESTAMP), -- Alice (Customer 1) memasukkan 1 Mouse ke keranjang
                                                                      (2, 3, 1, CURRENT_TIMESTAMP), -- Ben (Customer 2) memasukkan 1 Monitor ke keranjang
                                                                      (2, 4, 1, CURRENT_TIMESTAMP), -- Ben (Customer 2) memasukkan 1 Laptop ke keranjang
                                                                      (3, 2, 3, CURRENT_TIMESTAMP); -- Charles (Customer 3) memasukkan 3 Mouse ke keranjang