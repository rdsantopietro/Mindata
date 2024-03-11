# Mindata  
Consideraciones:  

-No se agregan Pageables en las solicitudes porque no se solicita  
-No Se agrega una entidad entidad "Ability" o "Power"  dado que no se especificó en el documento. Se agregará un atributo String ability  
-Se decide implementar configurar las creaciones de las tablas con liquibase luego de plantear los primeros tests.  
-Se decime escribir los métodos de tests de SuperHeroServiceTest utilizando SuperHeroServiceImpl para no tener que levantar el contexto de Spring.  
-Se decide no evaluar los objetos para actualizar   
-Se hacen algunos test unitarios y de integración de ejemplo, sin contemplar todos los escenarios posibles  
-Se documenta la aplicación en /v3/api-docs. Se acitva Swagger en /swagger-ui/index.html  
-Se dockeriza la app, se agrega un compose para exponer el puerto de la app.  
-Se agrega segurida a la app, se dejan dos usuarios user:user admin:admin, con sus respectivos roles. Se setea el metodo delete del resource solo para roles Admin  





