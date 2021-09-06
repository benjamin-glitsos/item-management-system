## Item Management System

A full-stack information system with a dockerised <!-- Model&ndash; -->Model&ndash;View&ndash;Controller (MMVC) architecture.

### Controller&ndash;Scala Container

* Architecture: Routes, Middleware, Validation, Service, DAO, Seeding, and Mixins
* Strong type safety using monads and other Algebraic Data Types
* Object-oriented in the large, functional programming in the small
* RESTful API design
* Middlewares for validation and logging
* JSON Schemas are the source-of-truth for both back-end and front-end validation

### Admin&ndash;React Container

**Note:** refactoring in progress

* Architecture: Routes, Pages, Hooks, Components, and Utilities
* Server-state management using React Query
* Functional data transformations using Ramda

### Database&ndash;PostgreSQL Container

* Architecture: Functions, Triggers, Views, Tables, Domains, and Indexes


* Architecture: rich metadata is abstracted into a separate table, however the use of views, ‘instead of’ triggers and stored procedures allow operating as if the metadata was in the same table

* 
* Functions reduce need for round trips to the controller
* Fourth normal form (4NF)

<!-- ### Session&ndash;Redis Container -->
<!--  -->
<!-- * Stores user-login sessions -->
