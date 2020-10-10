# Inventory Management System

A toy inventory management system that is built with enterprise-grade architecture and principles. It is deliberately over-engineered to resemble real-world systems used in Finance, Government, Medicine, Retail, Agriculture, et al.

**This is a work-in-progress, but the current state of the project is as follows.**

## Microservice Architecture

Dockerised MVC architecture.

### Controller

<<<<<<< HEAD
**Scala (Java-based language).**

Contract-first API, OpenAPI (Swagger, JSON Schema) Java interop, algebraic data types, object-orientation, Functional programming, (OOP in the large; FP in the small), GRASP, SOLID, Type-safety, Functional purity, REST-like/CRUD API, Composition over inheritance, Separation of concerns, DRY.
=======
**Scala (Java-platform language with Haskell-like type system).**

Contract-first API, OpenAPI (Swagger, JSON Schema) Java Interop, Algebraic Data Types, Object-Oriented in the large, Functional Programming in the small, GRASP, SOLID, Type-safety, Functional purity, REST-like/CRUD API, Composition over inheritance, Separation of concerns, DRY.
>>>>>>> master

Multitier Architecture:

<<<<<<< HEAD
* Schema Layer
* Testing Layer
* Documentation Layer
* View Layer
=======
* Contracts Layer
* Middleware Layer
>>>>>>> master
* Routing Layer
* Validation Layer
* Service Layer
* DAO/ORM Layer
<!-- * Schema Layer -->
<!-- * Testing Layer -->
<!-- * Documentation Layer -->
<!-- * View Layer -->
<!-- * Seeding Layer -->

<!-- Middleware Architecture: -->
<!--  -->
<!-- `Req` → Ensure HTTPS → Request Logging → OpenAPI Request Validation → Authorisation → Access Control → `Controller` → Prevent Caching → Error Formatting → `Res` -->

Middleware Architecture:

`Req` → Ensure HTTPS → Request Logging → OpenAPI Request Validation → Authorisation → Access Control → `Controller` → Prevent Caching → Error Formatting → `Res`

### Portal

**Angular (Typescript language).**

<<<<<<< HEAD
Angular architecture, Single Page App (SPA).
=======
Single-Page App (SPA) with industry-standard Angular architecture.
>>>>>>> master

### Database

**PostgreSQL.**

The schema utilises a metadata table and business keys. 3NF database normalisation.
