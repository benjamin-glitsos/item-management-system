# Inventory Management System

A toy inventory management system that is built with enterprise-grade architecture and principles.

## Microservice Architecture

Dockerisation, MVC, Networking, Scalability.

<!-- ![HMS Architecture](images/hms-architecture.svg?v=8) -->

### Controller

**Scala (Java-based language).**

Contract-based API, OpenAPI (Swagger, JSON Schema) Java interop, algebraic data types, object-orientation, Functional programming, (OOP in the large; FP in the small), GRASP, SOLID, Type-safety, Functional purity, REST-like/CRUD API, Composition over inheritance, Separation of concerns, DRY.

Multilayered Architecture:

* Schema Layer
* Testing Layer
* Documentation Layer
* View Layer
* Routing Layer
* Seeding Layer
* Service Layer
* DAO/ORM Layer

Middleware Architecture:

`Req` → Ensure HTTPS → Request Logging → OpenAPI Request Validation → Authorisation → Access Control → `Controller` → Prevent Caching → Error Formatting → `Res`

### Portal

**Angular (Typescript).**

Angular architecture, Single Page App (SPA).

### Database

**PostgreSQL, Bash.**

4NF database normalisation.

[View the Database Architecture](https://benglitsos.com.au/hms/schemaspy/relationships.html)

### Database Management

**Adminer.**

## Workflow

Ticket-based Git Trunk, with Jira.
