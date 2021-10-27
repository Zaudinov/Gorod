CREATE TABLE IF NOT EXISTS subscriber(
    subscriber_id bigint PRIMARY KEY,
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

INSERT INTO SUBSCRIBER (SUBSCRIBER_ID, ACCOUNT, FIO) VALUES
(1, '1235213', 'ZII'),
(2, '2235213', 'PVV');

INSERT INTO SERVICE (SERVICE_ID,name, PARENT_ID) VALUES
(1, 'Жилищно-коммунальные услуги', null),
(2, 'Отопление', 1),
(3, 'Вода',1),
(4, 'Горячяя Вода',3),
(5, 'Холодная Вода',3),
(6, 'Детский сад', null),
(7, 'Ясли', 6);


INSERT INTO SUBSCRIBER_SERVICE (SUBSCRIBER_ID, SERVICE_ID) VALUES
(1, 1),
(1, 5),
(1,6),
(1,7),
(2, 2),
(2, 3),
(2, 4),
(2, 5);

