INSERT INTO SUBSCRIBER (SUBSCRIBER_ID, ACCOUNT, FIO) VALUES
                                                            (1, '1235213', 'ZII');
INSERT INTO SERVICE (SERVICE_ID,name) VALUES
(1, 'Жилищно-коммунальные услуги'),
(2, 'Отопление'),
(3, 'Вода'),
(4, 'Горячяя Вода'),
(5, 'Холодная Вода');

INSERT INTO service_relations (parent_id, child_id) VALUES
                                     (1,2),
                                     (1,3),
                                     (3,4),
                                     (3,5);

INSERT INTO SUBSCRIBER_SERVICE (SUBSCRIBER_ID, SERVICE_ID) VALUES
                                                                  (1, 1),
                                                                  (1, 5)