DROP TABLE IF EXISTS tbl_invoice_items;
DROP TABLE IF EXISTS tbl_invoices;


CREATE TABLE tbl_invoices (
                                id BIGINT AUTO_INCREMENT  PRIMARY KEY,
                                number_invoice VARCHAR(250) NOT NULL,
                                description VARCHAR(250) NOT NULL,
                                customer_id BIGINT,
                                create_at DATE,
                                state VARCHAR(250) NOT NULL

);

CREATE TABLE tbl_invoice_items (
                              invoice_id BIGINT AUTO_INCREMENT  PRIMARY KEY,
                              quantity INTEGER,
                              price DOUBLE ,
                              product_id BIGINT,
);

