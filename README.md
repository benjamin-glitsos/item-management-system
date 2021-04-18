## Item Management System

A full-stack information system with a dockerised Model-View-Controller (MVC) architecture.

### Controller-Scala Container

* Layered architecture: Service, DAO, Middleware and Seeders
* RESTful API design
* Strong type-safety by using monads and other Algebraic Data Types
* Object-oriented multiple-trait-inheritance pattern
* JSON Schema contract-first development

### Admin-React Container

* React Hooks, Context and Effects
* Container/Presenter pattern where useful
* Ramda functional combinator library
* Atlassian Design System

### Database-PostgreSQL Container

* Architecture: rich metadata is abstracted into a separate table, however views, ‘instead of’ triggers and stored procedures allow operating on data as if it were contained in a single table
* Trigram indexes speed up text search
* Domain constraints ensure data integrity
* Fourth normal form (4NF)
