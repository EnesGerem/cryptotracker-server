# CryptoTracker

An application server for live tracking cryptocurrency prices.

# Design Decisions

1. Classes must be in places relevant to each other. For example, 'UserService' and 'UserRepository' should be located under 'user' package
2. Entities must extend a standard template created for entity classes.
3. All entities must have audit columns.
4. Test classes must extend a standard template created for test classes depending on the test type.
5. Application constants must be independent of the code.
6. Lombok should be used for logging, getters, setters and constructors.
7. Caller layer and service layer should be abstracted.
8. Naming conventions should be followed.
9. All methods should be tested except Lombok methods and controller methods.

## Running the Application

To build the application,

````shell
mvn clean package
````

To run the application,

```shell
docker build . -t cryptotracker:latest
docker-compose up
```

Application can be tested with provided postman collection at location ```/postman```

