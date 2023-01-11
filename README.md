# User management service


## Dependencies

- postgres
- java 11
- maven
- kafka
- open-telemetry (optional)

## Setup

First of all, be sure to have java 11 and maven on your laptop.

Launch kafka, postgres and open-telemetry (optional).

In postgres, create a database named `usermanagement` and a user named `sa_usermanagement` with password `sa_usermanagement_pwd`.

Then, in the root of the project, run `mvn spring-boot:run` to launch the application. 

## API

The service run on port 4002