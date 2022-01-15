import { useState } from "react";
import R from "ramda";
import { useParams } from "react-router-dom";
import { useForm } from "react-hook-form";
import useYupSchemaResolver from "hooks/useYupSchemaResolver";
import "react-mde/lib/styles/css/react-mde-all.css";
import useEdit from "hooks/useEdit";
import useQueriesClient from "hooks/useQueriesClient";
import useMutationClient from "hooks/useMutationClient";
import useQueriesFetch from "hooks/useQueriesFetch";
import useProject from "hooks/useProject";
import usePage from "hooks/usePage";
import useUser from "hooks/useUser";
import FormSection from "modules/FormSection";
import EditTemplate from "templates/EditTemplate";
import queriesData from "utilities/queriesData";
import Field from "elements/Field";

export default () => {
    const [update, setUpdate] = useState(0);

    const { username } = useParams();
    const project = useProject();
    const edit = useEdit();
    const user = useUser();

    const schemaPath = `schemas/${edit.action}-${user.namePlural}`;
    const itemPath = `${user.namePlural}/${username}`;

    const queries = useQueriesClient([schemaPath, itemPath]);
    const mutation = useMutationClient("PATCH", itemPath);

    useQueriesFetch(queries);

    const [schemaData, itemData] = queriesData(queries);

    const form = useForm({
        resolver: useYupSchemaResolver(schemaData)
    });

    const page = usePage({
        history,
        action: edit.action,
        key: itemData?.[user.keyField],
        nameSingular: user.nameSingular,
        namePlural: user.namePlural,
        projectName: project.name
    });

    const context = {
        edit,
        schemaData,
        itemData,
        mutation,
        queries,
        form,
        page
    };

    return (
        <EditTemplate context={context}>
            <FormSection title="Details">
                <Field
                    name="username"
                    columnWidths={{ lg: 6 }}
                    isControlled={false}
                    context={context}
                />
                <Field
                    name="email_address"
                    columnWidths={{ lg: 6 }}
                    isControlled={false}
                    context={context}
                />
                <Field
                    name="first_name"
                    columnWidths={{ lg: 4 }}
                    isControlled={false}
                    context={context}
                />
                <Field
                    name="last_name"
                    columnWidths={{ lg: 4 }}
                    isControlled={false}
                    context={context}
                />
                <Field
                    name="other_names"
                    columnWidths={{ lg: 4 }}
                    isControlled={false}
                    context={context}
                />
            </FormSection>
            <FormSection title="Misc.">
                <Field
                    name="additional_notes"
                    columnWidths={{ sm: 12 }}
                    isControlled={true}
                    context={context}
                />
            </FormSection>
        </EditTemplate>
    );
};
