#BDmanager-Java
==============

BDManager es un conjunto de clases (BDManager y Row) que permite realizar consultas a 
una base de datos Mysql rápidamente desde Java, sin tener que escribir gran cantidad de código.

En la clase Main, se encuentran los ejemplos de lo que se puede hacer:

- Inserts
- Consultas
- Updates
- Deletes
- Ingresar Blobs
- Retornar un Blob

##Clase Row

No hace falta modificarla. Sólamente es un helper de la clase BDManager, que almacena
todos los datos, como un ArrayList<String> de una fila de la base de datos Mysql.

##Clase BDManager

Contiene todos los métodos necesarios para las consultas, inserts, updates o deletes a 
la base de datos Mysql. Tiene su propio Javadoc para facilitar el uso de los métodos.

##Ejemplos:

Inicializar la clase BDManager para hacer consultas rápidas en JAVA sobre Mysql:

BDManager bdmanager = new BDManager('base_de_datos','root','root')

==================
para llamar un método de la clase: (ej. consulta)

ArrayList< Row > resultados = bdmanager.consulta("select * from nombre_tabla");

==================
para ver los datos resultados de una consulta:

ArrayList< Row > resultados = bdmanager.consulta("select * from nombre_tabla");

System.out.println(resultados.toString());

==================
para ver los contenidos de toda una fila encontrada:

ArrayList< Row > resultados = bdmanager.consulta("select * from nombre_tabla");

Row resultado_fila = resultados.get(0);

System.out.println(resultado_fila.toString());

==================
para ver un dato en específico proveniente de una columna:

ArrayList< Row > resultados = bdmanager.consulta("select * from nombre_tabla");

String contenido_columna = resultados.get(0).getValueAt(0);

System.out.println(contenido_columna);
