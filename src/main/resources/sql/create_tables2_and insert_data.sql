CREATE TABLE IF NOT EXISTS service(
                                      service_id INT PRIMARY KEY,
                                      parent_id INT,
                                      name VARCHAR(256) NOT NULL


);
ALTER TABLE service add foreign key (parent_id) references service(service_id);



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

INSERT INTO SUBSCRIBER (SUBSCRIBER_ID, ACCOUNT, FIO) VALUES
(1, '1235213', 'ZII'),
(2, '2235213', 'PVV');
INSERT INTO SERVICE (SERVICE_ID,name, parent_id) VALUES
(1, 'Жилищно-коммунальные услуги'),
(2, 'Отопление',1),
(3, 'Вода',1),
(4, 'Горячяя Вода',3),
(5, 'Холодная Вода',3);


INSERT INTO SUBSCRIBER_SERVICE (SUBSCRIBER_ID, SERVICE_ID) VALUES
(1, 1),
(1, 5),
(2, 2),
(2, 3),
(2, 4),
(2, 5);

