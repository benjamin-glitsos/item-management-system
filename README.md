# Hospital MS

A toy hospital management system with enterprise-grade architecture and principles.

(Work in progress.)

## Microservice Architecture

Dockerisation, MVC, Networking, Scalability.

![HMS Architecture](images/hms-architecture.svg?v=8)

### Controller

**Scala (Java language).**

Object-orientation, Functional programming, (OOP in the large; FP in the small), GRASP, SOLID, Type-safety, Functional purity, RESTful/CRUD API, Composition over inheritance, Separation of concerns, DRY.

Multilayered Architecture:

<div>
<div style="padding: 5px; border: 1px solid red; background-color: blue; color: white;">Routing Layer</div>
<div style="padding: 5px; border: 1px solid red; background-color: blue; color: white;">Seeding Layer</div>
<div style="padding: 5px; border: 1px solid red; background-color: blue; color: white;">Service Layer</div>
<div style="padding: 5px; border: 1px solid red; background-color: blue; color: white;">DAO/ORM Layer</div>
</div>

* Routing Layer
* Seeding Layer
* Service Layer
* DAO/ORM Layer

### Portal

**Angular (Typescript).**

Angular architecture.

### Database

**PostgreSQL, Bash.**

4NF database normalisation.

[View the Database Architecture](https://benglitsos.com.au/hms/schemaspy/relationships.html)

### Database Management

**Adminer.**

## Workflow

Ticket-based Git Trunk, with Jira.
