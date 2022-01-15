## Item Management System

A full-stack information system with a dockerised Model&ndash;View&ndash;Controller (MMVC) architecture.

[ims.benglitsos.com.au](https://ims.benglitsos.com.au/)

<<<<<<< HEAD
### Controller&ndash;Scala Container
=======
* Layered architecture: Routing, Middleware, Validation, Service, DAO, and Seeding layers
* JSON Schema is used for back-end and front-end validation, and custom properties are used for custom validation messages
* RESTful API design
* Strong type-safety by using monads and other Algebraic Data Types, and an SQL query builder
* Middlewares for authentication, logging, validation, and more
* Object-oriented multiple-trait-inheritance pattern
* Optimised to minimise round-trips and network load
>>>>>>> feature/react-architecture

* Structure: Routes, Middleware, Validation, Service, DAO, Seeding, and Mixins.
* Object-oriented structure with functional programming methods.
* Strong type safety using monads and other Algebraic Data Types.
* RESTful API design.
* Middlewares used for validation and logging.
* JSON Schemas are the source-of-truth for both back-end and front-end validation.

<<<<<<< HEAD
### Admin&ndash;React Container
=======
Note: refactoring in progress

* Design system inspired by Atomic Design but composed of: Elements, Modules, Templates, and Pages
* Server-state management using React Query
* Architecture uses custom React Hooks
* Functional data pipelines using Ramda
>>>>>>> feature/react-architecture

**Note:** refactoring in progress, so the code is still messy except for the [UsersEdit.jsx](https://github.com/benjamin-glitsos/item-management-system/blob/main/admin-react/src/pages/UsersEdit.jsx) page.

<<<<<<< HEAD
* Structure: Pages, Hooks, Components, and Utilities.
* Server-state management using React Query.
* Functional data transformations using Ramda.

### Database&ndash;PostgreSQL Container

* Structure: Functions, Triggers, Views, Tables, Domains, and Indexes.
* Rich metadata on all entities is stored in a separate table. The use of views and triggers allows operating as if it were in the same table.
* Use of stored functions to reduce round trips.
* Fourth normal form (4NF).
=======
* Architecture: rich metadata is abstracted into a separate table, however the use of views, ‘instead of’ triggers and stored procedures allow operating as if the metadata was in the same table
* Trigram indexes speed up text search
* Domain constraints ensure data integrity
* Fourth normal form (4NF)

### Session-Redis Container

* Stores user-login sessions
>>>>>>> feature/react-architecture
