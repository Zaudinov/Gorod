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
)