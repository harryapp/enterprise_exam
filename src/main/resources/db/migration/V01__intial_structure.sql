

create table customers
(
    customer_id    bigserial primary key,
    customer_email varchar(100),
    customer_phone varchar(15),
    address_id bigint
);

create table products
(
    product_id   bigserial primary key,
    product_name varchar(100)
);

create table deliveries
(
    order_id    bigserial primary key,
    customer_id bigint,
    order_date date,
    delivered_date date,
    is_delivered bool
);

create table addresses
(
    address_id bigserial primary key,
    street varchar(100),
    zip_code varchar(10),
    city varchar(100),
    country varchar(100)
);


create table ordered_products
(
    ordered_product_id bigserial primary key,
    order_id bigint,
    product_id bigint,
    quantity int
);


create table users
(
    user_id       bigserial primary key,
    user_created  timestamp    not null,
    user_email    varchar(100) not null,
    user_enabled  boolean      not null,
    user_password varchar(100) not null
);

create table authorities
(
    authority_id   bigserial primary key,
    authority_name varchar(100)
);

create table users_authorities
(
    user_entity_user_id      bigint,
    authorities_authority_id bigint
);