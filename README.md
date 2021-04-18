## Item Management System

A full-stack information system with a dockerised Model-View-Controller (MVC) architecture.

### Controller-Scala Container

* Layered architecture: Service, DAO, Middleware and Seeders.
* RESTful API design
* Strong type-safety by using monads and other Algebraic Data Types
* Object-oriented multiple-trait-inheritance pattern
* JSON Schema contract-first development

### Admin-React Container

* React Hooks, Context and Effects
* Container/Presenter pattern where useful
* Atlassian Design System

### Database-PostgreSQL Container

* DRY via use of a separate metadata table joined via Views and Triggers
* Data integrity via the heavy usage of custom domains
* Use of trigram indexes, stored procedures, and window functions.
* Fourth normal form (4NF)
