insert into users (mail, password_hash) values ('parent', '$2a$10$g0slOC0i7Z/0pwyqfyZpW.Hczej/eUnLVKUK34ItTn1w3/Nd3ITp2');
insert into roles (role, user_id) values ('PARENT', last_insert_id());