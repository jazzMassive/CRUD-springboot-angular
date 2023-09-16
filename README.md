# CRUD Implementation using SpringBoot and Angular

## Requirements to run:
- Docker (v4.15.0 or newer)
- Java 17
- NodeJS (v18.16.0 or newer)
- npm (v9.5.1 or newer)


## **Setup:**
0. Make sure docker deamon is running
1. In cmd, navigate to the `crudbackend` directory
   1. Run `docker-compose up` . It should start a mySQL server instance on `localhost:3306`
   2. In your Java IDE of choice, open the `crudbackend` directory as a project and run the `CrudbackendApplication.java`
   3. If there are no errors, your backend will now be accessible on `localhost:8080`
2. In cmd, navigate to the `crudfrontend` directory
   1. Run `npm install`
   2. Once the dependencies are downloaded, run `ng serve` to start the server
   3. If there are no errors, your frontend will now be accessible on `localhost:4200`