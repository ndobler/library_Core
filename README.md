# DEVELOPER GAMES
# CORE DREAM TEAM
# Library Core (Challenge #01)
## Book Object 
    Book:
        id 
        Name - book name
        publishingYear - Year of publishing

Json example:

**{"id":11,"name":"La chica mecanica","publicationYear":2017}**

## Rest resources

    DELETE /books 
        - Delete book 
    POST /books   
        - Add a book  (Note: books names are unique)
    PUT /books
        - Modify a boiok
    GET /books/all
        - List all books order by id
    GET /books/book/{id}
        - Find a book by id
    GET /books/byName/{name}
        - Find a book by name
    GET /books/byPublicationYearBetween/{lowerYear}/{higherYear}
        - Find books with publishing dates between the entries
    GET /books/list
        - List books paging passing parameters *pageNumber*(default 1) and  "maxNumber"(default 2)

### Build and deploy in Openshift
    mvn clean package -Dquarkus.container-image.build=true  
    oc get is  
    oc new-app --name=greeting <project>/openshift-quickstart:1.0.0-SNAPSHOT  
    oc get svc  
    oc expose svc/greeting  
    oc get routes  
    curl http://<route>/greeting  

## Quarkus build info
### code-with-quarkus Project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

#### Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

#### Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

#### Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/code-with-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.


#### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)
