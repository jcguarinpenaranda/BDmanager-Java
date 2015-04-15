#BDmanager-Java 
==============

BDManager-Java is a set of two classes (BDManager and Row) that let you make requests to a Mysql DB fast and easy from Java, just with a few lines.

In the class "main.java", you can find the examples of what can be done:

- Inserts
- Queries
- Updates
- Deletes
- Insert Blobs
- Get a Blob

##Class Row

There's no need to modify it. It is just a helper of the class BDManager, that stores all the data returned from a Mysql Query, as an ``` ArrayList<String> ```

<h4>Attributes:</h4>

- ``` int columns```: The number of columns in the ArrayList of Strings given when initialized the class.

<h4>Methods:</h4>

- ``` public Row(ArrayList<String> values)```: Constructor for initializing the class, with an Arraylist of Strings.

- ``` public String getValueAt(int num)```:  Use this to get the value at given position of a Row fetched. See the examples below some clarity.


##Class BDManager

It contains all the necessary methods for the queries, inserts, updates or deletes that you can make to a Mysql database. It's got its own Javadoc for making it easier to understand how to use it.


<h4>Attributes:</h4>

- ``` public final String JDBC_DRIVER```: The JDBC Driver, always "com.mysql.jdbc.Driver"


<h4>Methods:</h4>

- ```public BDManager(String dbname, String username, String password) ```: The constructor of the class.

- ```public ArrayList<Row> query(String query) ```: Method for making queries into the database. This will make ONLY Selects. The query may be for example: "select * from animals;"

- ```public int update(String update) ``` : Method for making inserts, updates or deletes, returning the number of rows affected. The update String may be for example: " insert into animals (id, name) values ('1', 'Horse')" 

- ``` public void updateBlob(String query, String blobRoute)```: Method for Inserting, deleting or updating a Blob. The blobRoute has to be the global route for the Blob you are referencing. For example: D:\\MyFiles\Images\image.png

- ``` public ImageIcon getImage(String query, String columnName)```: Method for getting a blob as an object of the class ImageIcon.  The parameter columName has to be the name of the column that contains the image you want to return. If, for example, we have the table *people*:

name | email | photo
-----|-------|------
Juan | email@company.com | BLOB

then your calling to the getImage method would be:

```java
	//initialize bdmanager

	ImageIcon image = bdmanager.getImage("select * from people where name = 'Juan'", "photo");
```

##Examples:

Let's start by making an instance of the class BDManager to make fast and easy queries in Mysql:

```java

BDManager bdmanager = new BDManager("database_name","user","password");

```

==================
and now let's return some results from a query on the database:

```java

ArrayList<Row> results = bdmanager.query("select * from table_name");

```

That's pretty much it. The data is returned as an Arraylist of Rows, which are basically ArrayList of Strings, containing in each position, the result of a column from the database.

==================

Let's see another example to make some clarity:

How about getting the results of a table of employees that looks like this:

<b>Employees (table name: employees)</b>
<table>
	<thead>
		<th>id</th>
		<th>name</th>
		<th>email</th>
		<th>telephone</th>
	</thead>
	<tbody>
		<tr>
			<td>1</td>
			<td>Johny</td>
			<td>johny@company.com</td>
			<td>+011354987</td>
		</tr>
		<tr>
			<td>2</td>
			<td>Katherine</td>
			<td>katherine@company.com</td>
			<td>+011324786</td>
		</tr>
	</tbody>
</table>

To do so, we are going to do the following:

```java
ArrayList<Row> employees = bdmanager.query("select * from employees");

System.out.println(employees.toString());
```

and this would be the outcome:

```java
	[[1 Johny johny@company.com +011354987] [2 Katherine katherine@company.com +011324786]]	
```

Please, notice that each result is an ArrayLists of Strings, so, if you have booleans, numbers, blobs, etc, they will be casted to String.

==================

From the last example, we saw how to get all the employees in a query, but, what if we want to get only the first of them? well, we would do:

```java
ArrayList<Row> employees = bdmanager.query("select * from employees");

Row employee = employees.get(0);

System.out.println(employee.toString());

```

and the outcome would be:

```java
	[1 Johny johny@company.com +011354987]
```

with all the fields as Strings

==================

Finally, to see an specific column of a row, for example the name of the first employee, we would do:

```java

ArrayList<Row> employees = bdmanager.query("select * from employees where name = 'Johny'");

Row johny = employees.get(0); //to get the first row

System.out.println(johny.getValueAt(1)); //because Rows are zero-indexed
//and name would be the index number 1

```

and the outcome would be:

```java
	Johny
```

##Important

Feel free to use BDManager as you want. Feel free to modify it as you like for your own projects.
The main objective of it is to provide an easy access to Mysql from Java, and if you find ways to improve it, please tell me.

By Juan Camilo Guarin Peñaranda
jcguarinpenaranda@gmail.com
http://otherwise-studios.com

