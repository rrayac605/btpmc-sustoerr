#Introducción
Batch encargado de realizar la actualización de registros con estado susceptible y susceptible otras a
erroneos y erroneos otras.

#Variables de entorno

============== AMBIENTE DE QA ==============

Para que este batch pueda ser ejecutado deberán de existir las siguietnes variables de entorno en el SO anfitrión donde se ejecute el jar

- portSusToErrBatch -> 9019
- authenticationdatabaseMongo -> PMCQA01
- usrMongoCifras -> pmcmsconsulta
- pwMongoCifras -> pmcmsconsulta0
- databaseMongo -> PMCQA01
- portMongo -> 27017
- hostMongo -> 10.100.8.78
- fileLogSusToErr -> /weblogic/pmc/logs/btpmc-sustoerr.log
- cron.expression.susToErr -> 0 0 23 ? * 6#1

============== AMBIENTE DE UAT ==============

Para que este microservicio pueda ser ejecutado deberán de existir las siguietnes variables de entorno en el SO annfitrión donde se ejecute el jar

- portSusToErrBatch -> 9019
- authenticationdatabaseMongo -> PMCUAT01
- usrMongoCifras -> pmcmodifica
- pwMongoCifras -> pmcmodifica0
- databaseMongo -> PMCUAT01
- portMongo -> 27017
- hostMongo -> 10.100.8.80
- fileLogSusToErr -> /weblogic/pmc/logs/btpmc-sustoerr.log
- cron.expression.susToErr -> 0 0 23 ? * 6#1

============== AMBIENTE DE PROD ==============

Para que este microservicio pueda ser ejecutado deberán de existir las siguietnes variables de entorno en el SO annfitrión donde se ejecute el jar

- portSusToErrBatch -> 9019
- authenticationdatabaseMongo -> PMC
- usrMongoCifras -> pmcbatch
- pwMongoCifras -> 	pmCb4tch%%
- databaseMongo -> PMC
- portMongo -> 27017
- hostMongo -> 10.100.6.172
- fileLogSusToErr -> /weblogic/pmc/logs/btpmc-sustoerr.log
- cron.expression.susToErr -> 0 0 23 ? * 6#1