# Website Monitoring Tool

Website monitoring tool is web application for 
monitoring urls state by periodic downloading 
pages by given urls. This app also control 
response time of the server, response http code,
 response size in a bounds and additional 
 (optional) availability some substring in 
 response.

## Functional parts
- Database connection
- Database operations
- Checking validity of the URL
- Adding new URL with own monitoring parameters
- Changing monitoring working status of each URL
- Updating URL monitoring parameters
- Removing URL from list of the urls for monitoring
- Displaying monitoring status and additional information about it

## How to praise?
**[Link to server](https://web-monitoring-tool.herokuapp.com/)**

## Description
#### How does it work?
System is waiting for adding new url and monitoring
parameters for it by client. After this the 
system save all getting url data to database and 
redirect client to the home page.

On the home page the system every **5 seconds** 
refresh it and get all urls from database. 
System create new thread for every url which
do monitoring and checking parameters which 
set client for current url. 

After checking system return **monitoring status
(OK, WARNING, CRITICAL)** and display it to client 
with additional information about status.

#### Interface
The system contains three pages:
 1. *Home page*
 2. *Page for create new url*
 3. *Page for edit monitoring parameters of url*
 
 ***Home page*** consists of a table and link 
on page for create, edit url. Also contains elements
for manage monitoring  and remove url from list.
 
 *Columns in table :*
 1. *ID* - id of url in database
 2. *Path* - path for url
 3. *Status* - monitoring status
 4. *Message* - additional info about status
 
 ***Page for create url*** displays set of fields
 with monitoring parameters.
 
 *Fields :*
  1. Path of url (http:// or https://)
  2. Monitoring period time (**set in milliseconds**)
  3. Server response time threshold for each 
  of the statuses (**set in milliseconds**)
  4. Expected response code
  5. Min size of response (**set in bytes**)
  6. Max size of response (**set in bytes**)

 ***Page for edit url parameters*** displays everything
 as the previous page.

#### Technologies

- ***Servlets and JSP*** - create UI and manage 
HTTP requests
- ***JDBC*** - database connection and operation with it
- ***PostgreSQL*** - relational database for save data
- ***Tomcat*** - web server and container for deploy app
- ***Maven*** - build tool

#### How to run it?
1. Download ***[Maven](https://maven.apache.org/download.cgi)***.
2. Open terminal and change working directory
to project directory. 
3. Write in terminal [ mvn clean install tomcat7:run ].
4. Open browser and in the search line write http://localhost:8888/.
