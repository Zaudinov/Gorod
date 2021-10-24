CREATE TABLE IF NOT EXISTS service(
                                      service_id INT PRIMARY KEY,
                                      name VARCHAR(256) NOT NULL

);



CREATE TABLE IF NOT EXISTS subscriber(
                                         subscriber_id BIGINT PRIMARY KEY,
                                         fio VARCHAR(256) NOT NULL,
                                         account VARCHAR(256) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS subscriber_service(
                                                 subscriber_id BIGINT,
                                                 service_id INT,
                                                 PRIMARY KEY(subscriber_id, service_id),
                                                 FOREIGN KEY(subscriber_id) REFERENCES subscriber(subscriber_id) ,
                                                 FOREIGN KEY(service_id) REFERENCES service(service_id)
);

CREATE TABLE IF NOT EXISTS service_hierarchy(
                                                service_id INT,
                                                parent_id INT,
                                                FOREIGN KEY (service_id) references service(service_id),
                                                FOREIGN KEY (parent_id) references service(service_id)
);

INSERT INTO SUBSCRIBER (SUBSCRIBER_ID, ACCOUNT, FIO) VALUES
(1, '1235213', 'ZII'),
(2, '2235213', 'PVV');
INSERT INTO SERVICE (SERVICE_ID,name) VALUES
(1, 'Жилищно-коммунальные услуги'),
(2, 'Отопление'),
(3, 'Вода'),
(4, 'Горячяя Вода'),
(5, 'Холодная Вода'),
(6, 'Детский сад');


INSERT INTO SUBSCRIBER_SERVICE (SUBSCRIBER_ID, SERVICE_ID) VALUES
(1, 1),
(1, 5),
(1,6),
(2, 2),
(2, 3),
(2, 4),
(2, 5);

INSERT INTO service_hierarchy(service_id, parent_id)
VALUES
    (2,1),
    (3,1),
    (4,3),
    (5,3);



