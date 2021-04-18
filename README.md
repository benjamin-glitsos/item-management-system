## Item Management System

A full-stack information system with a dockerised Model-View-Controller (MVC) architecture.

### Controller-Scala Container

* Layered architecture: Service, DAO, Middleware and Seeders.
* RESTful API
* JSON Schema validation
* Type-safety using Algebraic Data Types
* Java Interop
* GRASP and SOLID
* Object-Oriented in the large, Functional Programming in the small

### Admin-React Container

* React Hooks, Context and Effects
* JSON Schema form validation
* Container/Presenter pattern where useful
* Ramda functional combinator library
* Atlassian Design System

### Database-PostgreSQL Container

* DRY via use of a separate metadata table joined via Views and Triggers
* Data integrity via the heavy usage of custom domains
* Use of trigram indexes, stored procedures, and window functions.
* Fourth normal form (4NF)
