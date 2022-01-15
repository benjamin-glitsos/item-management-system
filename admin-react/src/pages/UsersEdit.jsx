import { useState, useEffect } from "react";
import R from "ramda";
import { useParams } from "react-router-dom";
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
import Textfield from "@atlaskit/textfield";
import RegisteredField from "elements/RegisteredField";
import ControlledField from "elements/ControlledField";
import MarkdownTextarea from "elements/MarkdownTextarea";

export default () => {
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

    const page = usePage({
        history,
        action: edit.action,
        key: itemData?.[user.keyField],
        nameSingular: user.nameSingular,
        namePlural: user.namePlural,
        projectName: project.name
    });

    const context = {
        page,
        edit,
        schemaData,
        itemData,
        mutation,
        queries
    };

    // TODO:
    // * EditTemplate will contain EditContext which will get passed the context values
    // * Modules will consume context. Elements will be reusable so will never consume context

    return (
        <EditTemplate context={context}>
            <FormSection title="Details">
                <RegisteredField
                    name="username"
                    title="Username"
                    Component={Textfield}
                    columnWidths={{ lg: 6 }}
                    isDisabled={false}
                    context={context}
                />
                <RegisteredField
                    name="email_address"
                    title="Email address"
                    Component={Textfield}
                    columnWidths={{ lg: 6 }}
                    isDisabled={false}
                    context={context}
                />
                <RegisteredField
                    name="first_name"
                    title="First name"
                    Component={Textfield}
                    columnWidths={{ lg: 4 }}
                    isDisabled={false}
                    context={context}
                />
                <RegisteredField
                    name="last_name"
                    title="Last name"
                    Component={Textfield}
                    columnWidths={{ lg: 4 }}
                    isDisabled={false}
                    context={context}
                />
                <RegisteredField
                    name="other_names"
                    title="Other names"
                    Component={Textfield}
                    columnWidths={{ lg: 4 }}
                    isDisabled={false}
                    context={context}
                />
            </FormSection>
            <FormSection title="Misc.">
                <ControlledField
                    name="additional_notes"
                    title="Additional notes"
                    Component={MarkdownTextarea}
                    columnWidths={{ sm: 12 }}
                    isDisabled={false}
                    context={context}
                />
            </FormSection>
        </EditTemplate>
    );
};
