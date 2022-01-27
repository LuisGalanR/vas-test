# vas-test
Test Proyect 

Spring Boot test proyect developed by Luis Galán Ruiz.

All code is available in this repository.

You can test it by using any IDE that allows you to do it or by executing the corresponding Maven commands in order to create a .war to deploy it in your local apache tomcat.

However I have already deployed the service in heroku in the following URL:
https://prueba-decide-luis.herokuapp.com

The following endpoints are available:

/information/{date} -> to process a JSON
/metrics -> to access the metrics of the processed JSON
/kpis -> to access the data of the service
/clear -> to clear all the data of the service if you want to start from zero.

I've made a series of assumptions for the development:

If there is no message type, we count it as a row with missing field but we cannot validate the rest of the fields, as we don't know the entity that we are processing, therefore, this rows won't be processed.

Empty fields in any row are treated like errors.

Lines with errors are not processed at all.

The metric "duration by country code" will represent the call duration grouped by origin country codes.

The processed JSON files is a counter for the number of times that the service has processed a JSON, if it process the same twice, it will count it as two different processes. 