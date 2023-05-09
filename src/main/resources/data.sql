INSERT INTO user(email,firstname,lastname,password,balance) values('jane@mail.com','Jane','Doe','$2a$12$Gdlo.F5gjfyLalyBxV0tqOjKCJJyxCikeY2DkiZB0E5MPviltsq76',502);
INSERT INTO user(email,firstname,lastname,password,balance) values('john@mail.com','John','Doe','$2a$12$Gdlo.F5gjfyLalyBxV0tqOjKCJJyxCikeY2DkiZB0E5MPviltsq76',302);
INSERT INTO user(email,firstname,lastname,password,balance) values('jesse@mail.com','Jesse','Doe','$2a$12$Gdlo.F5gjfyLalyBxV0tqOjKCJJyxCikeY2DkiZB0E5MPviltsq76',2);

INSERT INTO authorities(email,authority) values('jane@mail.com', 'USER');
INSERT INTO authorities(email,authority) values('john@mail.com', 'USER');
INSERT INTO authorities(email,authority) values('jesse@mail.com', 'USER');

INSERT INTO bank(iban,swift,id_user) values('FR76 4567 8790 8798 789056781', 'AXAB BE 22 XXX',1);
INSERT INTO bank(iban,swift,id_user) values('LU76 4567 8790 8798 789056781', 'AXAB BE 22 XXX',1);
INSERT INTO bank(iban,swift,id_user) values('BE88 4567 8790 8798 567817890', 'BEBEC BE 10 XXX',2);
INSERT INTO bank(iban,swift,id_user) values('FR88 4567 8790 8798 567819999', 'BEBEC BE 10 XXX',3);

INSERT INTO bank_transaction(date,description,amount,fees,id_bank) values('2022-09-01 09:01:15.0', 'rechargement du compte',200,0.05,1);
INSERT INTO bank_transaction(date,description,amount,fees,id_bank) values('2022-03-01 14:01:15.0', 'recharge',200,0.05,2);

INSERT INTO connection(id_sender, id_receiver) values(1,2);
INSERT INTO connection(id_sender, id_receiver) values(2,1);

INSERT INTO connection_transaction(date,description,amount,fees,id_connection) values('2023-02-01 18:01:00.0', 'remboursement du resto',35,0.05,1);
INSERT INTO connection_transaction(date,description,amount,fees,id_connection) values('2022-10-01 18:01:00.0', 'voyage paris',30,0.05,1);
INSERT INTO connection_transaction(date,description,amount,fees,id_connection) values('2022-02-01 18:01:00.0', 'cadeau',25,0.05,2);


commit;