## Item Management System

A full-stack information system with a dockerised <!-- Model&ndash; -->Model&ndash;View&ndash;Controller (MMVC) architecture.

### Controller&ndash;Scala Container

* Architecture: Routing, Middleware, Validation, Service, DAO, and Seeding
* Object-oriented mixin pattern
* Strong type-safety using monads and other Algebraic Data Types
* RESTful API design
* Middlewares for authentication, logging, validation, and more
* Optimised to minimise round-trips and network load
* JSON Schemas are the source-of-truth for both back-end and front-end validation

### Admin&ndash;React Container

**Note:** refactoring in progress

* Architecture: Pages, Component, and Hooks
* Server-state management using React Query
* Functional data pipelines using Ramda

### Database&ndash;PostgreSQL Container

* Architecture: rich metadata is abstracted into a separate table, however the use of views, ‘instead of’ triggers and stored procedures allow operating as if the metadata was in the same table
* Trigram indexes speed up text search
* Domain constraints ensure data integrity
* Fourth normal form (4NF)

<!-- ### Session&ndash;Redis Container -->
<!--  -->
<!-- * Stores user-login sessions -->
