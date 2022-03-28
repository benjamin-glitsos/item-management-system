## Item Management System

⚠️ **Down for maintenance:** ⚠️

[ims.benglitsos.com.au](https://ims.benglitsos.com.au/)


A full-stack information system with a dockerised Model&ndash;View&ndash;Controller (MMVC) architecture.

### Controller&ndash;Scala Container

* Structure: Routes, Middleware, Validation, Service, DAO, Seeding, and Mixins.
* Object-oriented structure with functional programming methods.
* Strong type safety using monads and other Algebraic Data Types.
* RESTful API design.
* Middlewares used for validation and logging.
* JSON Schemas are the source-of-truth for both back-end and front-end validation.

### Admin&ndash;React Container

**Note:** refactoring in progress, so this container’s code is messy except for the [UsersEdit.jsx](https://github.com/benjamin-glitsos/item-management-system/blob/main/admin-react/src/pages/UsersEdit.jsx) page.

* Structure: Pages, Hooks, Components, and Utilities.
* Server-state management using React Query.
* Functional data transformations using Ramda.

### Database&ndash;PostgreSQL Container

* Structure: Functions, Triggers, Views, Tables, Domains, and Indexes.
* Rich metadata on all entities is stored in a separate table. The use of views and triggers allows operating as if it were in the same table.
* Use of stored functions to reduce round trips.
* Fourth normal form (4NF).
