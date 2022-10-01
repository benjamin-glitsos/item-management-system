import { createContext } from "react";
import styled from "styled-components";
import ReactMarkdown from "react-markdown";
import { titleCase } from "title-case";
import PageContainer from "%/components/Page/PageContainer";
import PageLayout from "%/components/PageLayout";
import ArticleLayout from "%/components/ArticleLayout";
import GithubButton from "%/components/GithubButton";
import ImagesLightbox from "%/components/ImagesLightbox";

const readme = `
## Item Management System

A full-stack web application with a containerised MVC architecture.

### Controller container (Scala)

This is the back-end logic and API.

* Object-oriented and Functional paradigms.
* Strong type safety.
* RESTful API design.
* JSON Schema validation.

### Front-end container (React)

This is the web interface.

* Reactive and Functional paradigms.
* Server state management.

### Database container (PostgreSQL)

This is the relational database.

* Data is normalised.
* Joined tables are simplified using views and triggers.
`;

export const Context = createContext();

const { Provider } = Context;

export default () => {
    const nameSingular = "readme";
    const namePlural = nameSingular;
    const title = titleCase(namePlural);
    const slug = nameSingular;

    const pageContainer = PageContainer({
        nameSingular,
        namePlural,
        title,
        slug,
        description: `Information about the architecture and technology stack of the ${
            process.env.PROJECT_NAME || "Item Management System"
        }.`
    });

    const pageContext = { ...pageContainer };

    return (
        <Provider value={pageContext}>
            <PageLayout
                title={pageContext.metaTitle}
                description={pageContext.description}
            >
                <ArticleLayout
                    title={pageContext.title}
                    breadcrumbs={[pageContext.homeBreadcrumb]}
                >
                    <ReactMarkdown source={readme} />
                    <h2>Codebase</h2>
                    <GithubButton />
                    <h2>Database ER Diagram</h2>
                    <DiagramStyles>
                        <ImagesLightbox
                            images={[
                                {
                                    src:
                                        process.env.PUBLIC_URL +
                                        "/images/er-diagram.svg",
                                    alt: `Entity-Relationship diagram of the database of the ${process.env.PROJECT_ABBREV}`
                                }
                            ]}
                        />
                    </DiagramStyles>
                </ArticleLayout>
            </PageLayout>
        </Provider>
    );
};

const DiagramStyles = styled.div`
    margin-top: 28px;
    img {
        max-width: 100%;
    }
`;
