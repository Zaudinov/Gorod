CREATE TABLE IF NOT EXISTS subscriber(
    subscriber_id bigint IDENTITY PRIMARY KEY,
    account VARCHAR(128) not null,
    fio VARCHAR(256) NOT NULL

);


CREATE TABLE IF NOT EXISTS service(
    service_id int PRIMARY KEY,
    name VARCHAR(128),
    parent_id int foreign key references service(SERVICE_ID) on delete cascade on update cascade
);

CREATE TABLE IF NOT EXISTS subscriber_service(
    subscriber_id bigint foreign key references subscriber(subscriber_id) on delete cascade on update cascade,
    service_id int foreign key references service(service_id) on delete cascade on update cascade
);

