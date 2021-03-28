CREATE TABLE active_users( id bigserial not null constraint active_users_pkey primary key,
                                                 first_name varchar(255),
                                                 last_name varchar(255),
                                                 email varchar(255),
                                                 phone_number varchar(255));

INSERT INTO public.active_users(id,first_name,last_name,email,phone_number) VALUES (1,'John','Doe','test@gmail.com','12345678');