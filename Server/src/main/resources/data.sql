-- Roles --
INSERT INTO roles(name, content)
VALUES
('Voluntario','Busqueda de oportunidades para hacer su servicio social'),
('Organización','Ejecutores de proyectos sin fines de lucro para ayuda comunitaria'),
('Administrador','Administradores de plataforma y control de organizaciones'),
('Master','Dueño del sistema');
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Categories --
INSERT INTO categories(name, description)
VALUES
('Comunitario','Servicio a las comunidades para el bien común'),
('Religioso','Aporte a congregaciones de indole religiosa'),
('Arte', 'Apoyo a los artistas'),
('Salud','Jornadas médicas y aporte en medicinas'),
('Educativo','Beneficios para escules de escasos recursos'),
('Ambiental','Busqueda de limpieza y concientización del ambiente'),
('Niños','Apoyo a la niñez y defensa de sus derechos'),
('Animales','Ayuda para fauna en peligro a su equilibrio y sontenibilidad'),
('Otros','Cualquier servicio a la comunidad sin fin de lucro');
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- User Master --
INSERT INTO users(name,lastname,gender,email,password,birth_date,telephone_number,id_role)
VALUES ('Admin','Master','1','admin@helpinghandsvolunteers.org','$2a$10$9Lp2siTFpD84Dh7pOcfE6eI3wLk3lkZkru4CEPCrva9Ej7cHep1o.','08/03/2022','72649170',4);
INSERT INTO masters(id)VALUES (1);

-- User Manager --
INSERT INTO users(name,lastname,gender,email,password,birth_date,telephone_number,id_role)
VALUES ('Andrés','Deras','1','aderas@helpinghandsvolunteers.org','$2a$10$/0x.mHcH70GeRMhSg6S8xuv9H1HZnC5jGXCsqOYFa/WsnXZ36cZFK','08/03/2022','71021101',3);
INSERT INTO managers(id)VALUES (2);

INSERT INTO users(name,lastname,gender,email,password,birth_date,telephone_number,id_role)
VALUES ('José','Hernandez','1','jhernandez@helpinghandsvolunteers.org','$2a$10$/0x.mHcH70GeRMhSg6S8xuv9H1HZnC5jGXCsqOYFa/WsnXZ36cZFK','08/03/2022','71021101',3);
INSERT INTO managers(id)VALUES (3);

INSERT INTO users(name,lastname,gender,email,password,birth_date,telephone_number,id_role)
VALUES ('Salvador','Pocasangre','1','spocasangre@helpinghandsvolunteers.org','$2a$10$/0x.mHcH70GeRMhSg6S8xuv9H1HZnC5jGXCsqOYFa/WsnXZ36cZFK','08/03/2022','71021101',3);
INSERT INTO managers(id)VALUES (4);

INSERT INTO users(name,lastname,gender,email,password,birth_date,telephone_number,id_role)
VALUES ('Nelson','Navarro','1','nnavarro@helpinghandsvolunteers.org','$2a$10$/0x.mHcH70GeRMhSg6S8xuv9H1HZnC5jGXCsqOYFa/WsnXZ36cZFK','08/03/2022','71021101',3);
INSERT INTO managers(id)VALUES (5);

-- User Organization --
INSERT INTO users(name,lastname,gender,email,password,birth_date,telephone_number,id_role)
VALUES ('User','Organization','1','projects@helpinghandsvolunteers.org','$2a$10$kcXqbyabWTARaMF5FJ7O0e2ZWb6D5y7dnxWL.d9aWq2ai.teTbcuO','08/03/2022','22412151',2);
INSERT INTO organizations(id,address,name_org,purpose,register_number,website)
VALUES (6,'Bulevar Los Próceres, Antiguo Cuscatlán, La Libertad El Salvador, Centroamérica','HelpingHands','Organización en búsqueda devoluntarios con muchas ansias de ayudar a la comunidad y ser agentes sin fines de lucro','048812671-7','www.helpinghandsvolunteers.org');

-- User Volunteer --
INSERT INTO users(name,lastname,gender,email,password,birth_date,telephone_number,id_role)
VALUES ('User','Volunteer','1','voluntario@uca.edu.sv','$2a$10$BeId3KYp8Ffn4HOn2pjySuhfjy8rYeeAhuev8uIQkEFvXrKOXpouS','08/03/2022','61337998',1);
INSERT INTO volunteers(id, career, college) VALUES (7,'Ingenieria Informática','UCA');
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Projects --
INSERT INTO projects(title,description,place,date,duration,response_date,create_at,id_owner,id_category,is_pending,is_finished,active)
VALUES
--Comunitarios
('Construcción de Cancha','Escuela pública necesita una cancha de fútbol para clases a niños de escasos recursos en situación de calle','Antiguo Cuscatlán','08/03/2022','3 meses','09/03/2022',date(now()),6,1,0,0,1),
--Religioso
('Pan y Chocolate','Brindamos alimentación la las noches a las personas en estado de calle con una pequeña reflexión','San Salvador','08/03/2022','3 semanas','09/03/2022',date(now()),6,2,0,0,1),
--Arte
('Clases de música','Se necesita músicos que desean impartir talleres de guitarra acústica','Santa Tecla','08/03/2022','3 meses','09/03/2022',date(now()),6,3,0,0,1),
--Salud
('Jornada Médica','Clínica municipal necesita ayudantes enfermeros o técnico para consultas con personas de la tercera edad','Tonacatepeque','08/03/2022','2 meses','09/03/2022',date(now()),6,4,0,0,1),
--Educativo
('Campaña de Alfabetización','Solicitamos jóvenes con espiritu de servicio para enseñar a leer a los habitantes del cantón "El quilito".','Armenia','08/03/2022','3 meses','09/03/2022',date(now()),6,5,0,0,1),
--Ambiental
('Reforestación de Arboles','Sembrar los arboles en peligro de extinción para su preservación','Ahuachapán','08/03/2022','3 meses','09/03/2022',date(now()),6,6,0,0,1),
--Niños
('Entrega de juguetes','Ayudanos a darles una sonrisa a los niños del huerfanato con un juguete','Lourdes Colón','08/03/2022','3 meses','09/03/2022',date(now()),6,7,0,0,1),
--Animales
('Adopción de perritos','Jóvenes amantes de los caninos dan en adopción perritos recuperados en estado de abandono','Zaragoza','08/03/2022','6 meses','09/03/2022',date(now()),6,8,0,0,1),
--Otros
('Donante de médula','Adolencente de 15 años necesita transplante de médula osea de tipo o-','Col. La Cima','08/03/2022','1 mes','09/03/2022',date(now()),6,9,0,0,1);
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Aprobaciones --
INSERT INTO approvals(id_project, id_user)
VALUES
    (1, 4),
    (2, 2),
    (3, 4),
    (4, 3),
    (5, 4),
    (6, 5),
    (7, 4),
    (8, 2),
    (9, 4);
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Images--



-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Favoritos --
INSERT INTO favorites(id_project, id_user)VALUES (1, 4);
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Comentarios --
INSERT INTO comentaries(content, create_at, id_project, id_user)
VALUES
('Gran proyecto de ayuda para los niños',date(now()),1,4),
('Muchas gracias que estan ayudando a esos abuelitos',date(now()),2,4),
('Quiero apoyar con mi donación, con mucho gusto',date(now()),1,4),
('Que bonito lo que hacen , muchas gracias',date(now()),2,4);
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Donations --
INSERT INTO donations(value, id_project, id_user, create_at)
VALUES
(400, 1, 4, date(now())),
(10,  2, 4, date(now())),
(50,  1, 4, date(now())),
(450, 2, 4, date(now())),
(400, 1, 4, date(now())),
(101, 2, 4, date(now())),
(470, 1, 4, date(now())),
(119, 2, 4, date(now()));
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------