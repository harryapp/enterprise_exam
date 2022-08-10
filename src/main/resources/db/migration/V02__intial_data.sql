insert into users
values (nextval('users_user_id_seq'), now(), 'admin@admin.com', true,
        '$2a$12$aJZigio5thHN4luSrVcQ3enLErI74CDaHqHANgoW69B8ru5uOUCrW');

insert into users
values (nextval('users_user_id_seq'), now(), 'customer@customer.com', true,
        '$2a$12$aJZigio5thHN4luSrVcQ3enLErI74CDaHqHANgoW69B8ru5uOUCrW');

insert into users
values (nextval('users_user_id_seq'), now(), 'employee@employee.com', true,
        '$2a$12$aJZigio5thHN4luSrVcQ3enLErI74CDaHqHANgoW69B8ru5uOUCrW');

insert into authorities
values (nextval('authorities_authority_id_seq'), 'ADMIN');

insert into authorities
values (nextval('authorities_authority_id_seq'), 'USER');

insert into users_authorities
values (1, 1),
       (1, 2),
       (2, 2);


insert into products
values (DEFAULT, 'milk'),
       (DEFAULT, 'sugar'),
       (DEFAULT, 'apple');

insert into customers
values (DEFAULT, 'customer1@gmail.com', '90909090', 1),
       (DEFAULT, 'customer2@gmail.com', '80808080', 2);

insert into addresses
values (DEFAULT, 'Street 1', '0101', 'Oslo', 'Norway'),
       (DEFAULT, 'Street 2', '0202', 'Bergen', 'Norway');

insert into deliveries
values (nextval('deliveries_order_id_seq'), 1, now(), null, false),
       (nextval('deliveries_order_id_seq'), 2, now(), null, false);

insert into ordered_products
values (nextval('ordered_products_ordered_product_id_seq'), 1, 1, 13),
       (nextval('ordered_products_ordered_product_id_seq'), 1, 2, 9),
       (nextval('ordered_products_ordered_product_id_seq'), 2, 1, 99),
       (nextval('ordered_products_ordered_product_id_seq'), 2, 2, 100);



