SERVER API : http://www.helpinghandsvolunteers.org

########################################################################################################################
RUTAS (AUTENTICACION DE USUARIOS)

Se ingresan los datos a traves de un json con sus respectivos parámetros

Registros Voluntario
POST /api/auth/signup-volunteer

Registro Organización
POST /api/auth/signup-organization

Registro Master(liberado para la creación del master de plataforma)
POST /api/auth/signup-organization

Inicio de Sesión(email,password)
POST /api/auth/signin

Nota: no se necesita token con lo que al enviar los json de los usuarios o credenciales se puede dar.

########################################################################################################################

RUTAS (ROLES)

Obtener roles
GET /api/roles/

Obtener role
GET /api/roles/{id}

Agregar role
POST /api/roles

Actualizar role
PUT /api/roles/{id}

Eliminar role
DELETE /api/roles/{id}

Nota: Solo pueden realizar estas acciones el role Master

########################################################################################################################

RUTAS (CATEGORIAS)

Obtener categorias
GET /api/categories/

Obtener categoria
GET /api/categories/{id}

Agregar categoria
POST /api/categories

Actualizar categoria
PUT /api/categories/{id}

Eliminar categoria
DELETE /api/categories/{id}

Nota: Solo pueden realizar estas acciones el role Master

########################################################################################################################

RUTAS (ESTADOS)

Obtener estados
GET /api/states/

Obtener estado
GET /api/states/{id}

Agregar estado
POST /api/states

Actualizar estado
PUT /api/states/{id}

Eliminar estado
DELETE /api/states/{id}

Nota: Solo pueden realizar estas acciones el role Master

########################################################################################################################

RUTAS PERFILES DE USUARIOS(ruta autorizada para cada user correspondiente)
Profile Voluntario:
POST /api/volunteer/get-my-profile

Profile Organización:
POST /api/organization/get-my-profile

Profile Manager:
POST /api/manager/get-my-profile

Profile Master:
POST /api/master/get-my-profile

Nota: Cada usuario puede ver su información nada más con su token permitido el cual se envia en su body u json con el token

########################################################################################################################

RUTAS DE MASTER(ruta autorizada para master solamente atraves de token)

GET /api/master/get-managers

Registro Manager:
POST /api/master/add-manager

Eliminar Manager:
DELETE /api/master/delete-manager/{id}

Nota: solo un token de master puede realizar estas acciones, en el caso de agregar se envia los datos y eliminar el correo del manager.

########################################################################################################################

USUARIOS

Voluntario:
{
"name":"Andrés",
"lastname":"Deras",
"gender":"Masculino",
"email":"00362718@uca.edu.sv",
"pass":"passvol",
"birth_date":"08/03/2022",
"telephone_number":"+50371021101",
"college":"UCA",
"career":"Ing. Informática"
}

Organization:
{
“id_role”:2,
“name”:”Nelson”,
“lastname”:”Navarro”,
“gender”:”Masculino”,
“email”:”projects@helpinghandsvolunteers.org”,
“pass”:”passorg”,
“birth_date”:”08/03/2022”,
“telephone_number”:”+50322412151”,
“name_org”:”HelpingHands”,
“register_num”:”0-15689242”,
“purpose”:”Sin fines de lucro”,
“address”:”Bulevar Los Próceres, Antiguo Cuscatlán, La Libertad El Salvador, Centroamérica”,
“website”:”www.helpinghandsvolunteers.org”
}

Manager:
{
"name":"Admin",
"lastname":"Manager",
"gender":"Masculino",
"email":"00368718@uca.edu.sv",
"pass":"passmanager",
"birth_date":"08/03/2022",
"telephone_number":"+50371021101"
}

Master:
{
"name":"Admin",
"lastname":"Master",
"gender":"Masculino",
"email":"admin@helpinghandsvolunteers.org",
"pass":"passmaster",
"birth_date":"08/03/2022",
"telephone_number":"+50371021101"
}

########################################################################################################################

ROLES

{
"name": "Voluntario",
"content": "Busqueda de oportunidades para hacer su servicio social"
}

{
"name": "Organización",
"content": "Ejecutores de proyectos sin fines de lucro para ayuda comunitaria"
}

{
"name": "Administrador",
"content": "Administradores de plataforma y control de organizaciones"
}

{
"name": "Master",
"content": "Dueño del sistema"
}

########################################################################################################################

CATEGORIAS

{
"name": "Comunitario",
"description": "Servicio a las comunidades para el bien común"
}

{
"name": "Religioso",
"description": "Aporte a congregaciones de indole religiosa"
}

{
"name": "Arte",
"description": "Apoyo a los artistas"
}

{
"name": "Salud",
"description": "Jornadas médicas y aporte en medicinas"
}

{
"name": "Educativo",
"description": "Beneficios para escules de escasos recursos"
}

{
"name": "Ambiental",
"description": "Busqueda de limpieza y concientización del ambiente"
}

{
"name": "Niños",
"description": "Apoyo a la niñez y defensa de sus derechos"
}

{
"name": "Animales",
"description": "Ayuda para fauna en peligro a su equilibrio y sontenibilidad"
}

{
"name": "Otros",
"description": "Cualquier servicio a la comunidad sin fin de lucro"
}

#########################################################################################################################

ESTADOS

{
"name": "En curso",
"description": "Actualmente en estado activo"
}

{
"name": "Pendiente",
"description": "A la espera de aprobación"
}

{
"name": "Cancelado",
"description": "Solicitud que fue borrada"
}

{
"name": "Rechazado",
"description": "No se dio aprobación a la petición"
}

{
"name": "Finalizado",
"description": "Transcurso de solicitud completo"
}

##########################################################################################################################
