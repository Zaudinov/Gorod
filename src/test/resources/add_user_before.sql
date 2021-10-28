DELETE FROM subscriber_service;
DELETE FROM subscriber;
TRUNCATE TABLE subscriber RESTART IDENTITY;
DELETE FROM service;

INSERT INTO subscriber (subscriber_id, account, fio)
    VALUES
    (1, '1000000', 'Ivanov Ivan Ivanovich'),
    (2, '1000001', 'Petrov Petr Ivanovich');

INSERT INTO SERVICE (SERVICE_ID, name, PARENT_ID) VALUES
(1, 'Жилищно-коммунальные услуги', null),
(2, 'Отопление', 1),
(3, 'Вода', 1),
(4, 'Горячяя Вода', 3),
(5, 'Холодная Вода', 3),
(6, 'Детский сад', null),
(7, 'Ясли', 6),
(8, 'Нагрев горячей воды', 4);

INSERT INTO SUBSCRIBER_SERVICE (SUBSCRIBER_ID, SERVICE_ID) VALUES
(1, 1),
(1, 5),
(1, 6),
(1, 7),
(2, 2),
(2, 3),
(2, 4),
(2, 8),
(2, 5);