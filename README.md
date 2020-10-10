# Inventory Management System

A toy inventory management system that is built with enterprise-grade architecture and principles. It is deliberately over-engineered to resemble real-world systems used in Finance, Government, Medicine, Retail, Agriculture, et al.

**This is a work-in-progress, but the current state of the project is as follows.**

## Microservice Architecture

Dockerised MVC architecture.

### Controller

**Scala (Java-platform language with Haskell-like type system).**

Contract-first API, OpenAPI (Swagger, JSON Schema) Java Interop, Algebraic Data Types, Object-Oriented in the large, Functional Programming in the small, GRASP, SOLID, Type-safety, Functional purity, REST-like/CRUD API, Composition over inheritance, Separation of concerns, DRY.

Multitier Architecture:

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

### Portal

**Angular (Typescript language).**

Single-Page App (SPA) with industry-standard Angular architecture.

### Database

**PostgreSQL**

The schema utilises a metadata table and business keys. 3NF database normalisation.
