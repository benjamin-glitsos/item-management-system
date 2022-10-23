import { createContext } from "react";
import styled from "styled-components";
import ReactMarkdown from "react-markdown";
import PageContainer from "%/components/Page/PageContainer";
import PageLayout from "%/components/PageLayout";
import ArticleLayout from "%/components/ArticleLayout";
import GithubButton from "%/components/GithubButton";
import ImagesLightbox from "%/components/ImagesLightbox";
import sentenceCase from "%/utilities/sentenceCase";

const readme = `
A full-stack web application with a containerised MVC architecture, structured as follows.

### Controller container (Scala)

This is the business logic and API.

* Uses Scala, based on Java language.
* Object-oriented and Functional paradigms.
* Strong type safety.
* RESTful API design.
* JSON Schema validation.

### Front-end container (React)

This is the user interface.

* Reactive and Functional paradigms.
* Server state management.

### Database container (PostgreSQL)

This is the relational database.

* Joined tables are abstracted using views and triggers.
* Structure is normalised.
`;

export const Context = createContext();

const { Provider } = Context;

export default () => {
    const nameSingular = "item management system";
    const namePlural = nameSingular;
    const title = "Item Management System";
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
                    <h2>View the code</h2>
                    <GithubButton />
                    <h2>Database schema</h2>
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
