CREATE TABLE IF NOT EXISTS service(
                                      service_id INT PRIMARY KEY,
                                      name VARCHAR(256) NOT NULL

);

CREATE TABLE IF NOT EXISTS service_relations(
    parent_id INT,
    child_id INT,
    PRIMARY KEY(parent_id, child_id),
    FOREIGN KEY(parent_id) REFERENCES service(service_id) ,
    FOREIGN KEY(child_id) REFERENCES service(service_id)
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

