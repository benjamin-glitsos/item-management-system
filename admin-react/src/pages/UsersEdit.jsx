import { createContext } from "react";
import R from "ramda";
import { useHistory, useParams } from "react-router-dom";
import { useForm } from "react-hook-form";
import "react-mde/lib/styles/css/react-mde-all.css";
import someProp from "utilities/someProp";
import useEdit from "hooks/useEdit";
import useQueries from "hooks/useQueries";
import useProject from "hooks/useProject";
import usePage from "hooks/usePage";
import useUser from "hooks/useUser";
import useYupSchemaResolver from "hooks/useYupSchemaResolver";
import LoadingSpinnerContent from "modules/LoadingSpinnerContent";
import ErrorMessageContent from "modules/ErrorMessageContent";
import UsersEditForm from "modules/UsersEditForm";
import EditTemplate from "templates/EditTemplate";

export const UsersEditContext = createContext();

export default () => {
    const { username } = useParams();
    const history = useHistory();

    const project = useProject();
    const edit = useEdit();
    const user = useUser();

    const queries = useQueries([
        ["schemas", `${edit.action}-${user.namePlural}`],
        [user.namePlural, username]
    ]);

    // const editClient = body =>
    //     useEditClient({
    //         path: [user.namePlural, username],
    //         body
    //     });
    //     TODO: make this mutation hook useEditClient again. I accidentally deleted it

    const schema = {
        $schema: "http://json-schema.org/draft-07/schema#",
        type: "object",
        properties: {
            name: {
                description: "Name of the person",
                type: "string"
            },
            email: {
                type: "string",
                format: "email",
                emailDescription: "lalalala",
                maxLength: 50,
                minLength: 1
            },
            fooorbar: {
                type: "string",
                matches: "(foo|bar)"
            },
            age: {
                description: "Age of person",
                type: "number",
                exclusiveMinimum: 0,
                required: true
            },
            characterType: {
                enum: ["good", "bad"],
                enum_titles: ["Good", "Bad"],
                type: "string",
                title: "Type of people",
                propertyOrder: 3
            },
            additionalNotes: {
                maxLength: 1048576,
                type: "string"
            }
        },
        required: ["name", "email"]
    };

    // const form = useForm({
    //     resolver: useYupSchemaResolver(schema)
    //     // queries[0]
    // });
    //
    const [schemaQuery, usersQuery] = queries.map(
        R.path("data", "data", "data")
    );

    const form = useForm({
        resolver: useYupSchemaResolver({
            schemaQuery,
            originalData: usersQuery
        })
    });

    if (someProp("isLoading", queries)) {
        return <LoadingSpinnerContent maxWidth={edit.maxWidth} />;
    }

    if (someProp("isError", queries)) {
        return <ErrorMessageContent maxWidth={edit.maxWidth} />;
    }

    const page = usePage({
        history,
        action: edit.action,
        key: usersQuery[user.keyField],
        nameSingular: user.nameSingular,
        namePlural: user.namePlural,
        projectName: project.name
    });

    const context = {
        page,
        edit,
        form,
        schemaQuery,
        usersQuery
    };

    return (
        <UsersEditContext.Provider value={context}>
            <EditTemplate
                context={UsersEditContext}
                form={<UsersEditForm context={UsersEditContext} />}
            />
        </UsersEditContext.Provider>
    );
};
