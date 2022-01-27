# vas-test
Test Proyect 

I've made a series of assumptions for the development:

If there is no message type, we count it as a row with missing field but we cannot validate the rest of the fields, as we don't know the entity that we are processing, therefore, this rows won't be processed.

Empty fields in any row are treated like errors.

Lines with errors are not processed at all.

The metric "duration by country code" will represent the call duration grouped by origin country codes.

The processed JSON files is a counter for the number of times that the service has processed a JSON, if it process the same twice, it will count it as two different processes. 