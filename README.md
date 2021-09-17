## Item Management System

A full-stack information system with a dockerised <!-- Model&ndash; -->Model&ndash;View&ndash;Controller (MMVC) architecture.

[ims.benglitsos.com.au](https://ims.benglitsos.com.au/)

### Controller&ndash;Scala Container

* Structure: Routes, Middleware, Validation, Service, DAO, Seeding, and Mixins.
* Object-oriented structure with functional programming methods.
* Strong type safety using monads and other Algebraic Data Types.
* RESTful API design.
* Middlewares used for validation and logging.
* JSON Schemas are the source-of-truth for both back-end and front-end validation.

### Admin&ndash;React Container

**Note:** refactoring in progress, so the code is still messy.

* Structure: Pages, Hooks, Components, and Utilities.
* Server-state management using React Query.
* Functional data transformations using Ramda.

### Database&ndash;PostgreSQL Container

* Structure: Functions, Triggers, Views, Tables, Domains, and Indexes.
* Rich metadata on all entities is stored in a separate table. The use of views and triggers allows operating as if it were in the same table.
* Use of stored functions to reduce round trips.
* Fourth normal form (4NF).

<!-- ### Session&ndash;Redis Container -->
<!--  -->
<!-- * Stores user-login sessions -->
