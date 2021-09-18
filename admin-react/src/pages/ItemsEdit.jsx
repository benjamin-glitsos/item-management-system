import { createContext } from "react";
import { useHistory, useParams } from "react-router-dom";
import { useForm } from "react-hook-form";
import Textfield from "@atlaskit/textfield";
import MarkdownTextarea from "%/components/MarkdownTextarea";
import SidebarLayout from "%/components/SidebarLayout";
import "react-mde/lib/styles/css/react-mde-all.css";
import useEdit from "%/hooks/useEdit";
import useQueriesClient from "%/hooks/useQueriesClient";
import useEditClient from "%/hooks/useEditClient";
import useProject from "%/hooks/useProject";
import usePage from "%/hooks/usePage";
import useItem from "%/hooks/useItem";
import useYupSchemaResolver from "%/hooks/useYupSchemaResolver";
import Page2 from "%/components/Page2";
import Content from "%/components/Content";
import EditSidebar2 from "%/components/EditSidebar2";
import Form from "%/components/Form";
import LoadingBanner from "%/components/LoadingBanner";
import RegisteredField2 from "%/components/RegisteredField2";
import ControlledField2 from "%/components/ControlledField2";
import FormSubheading from "%/components/FormSubheading";
import ErrorBanner from "%/components/ErrorBanner";
import InputDate from "%/components/InputDate";
import InputNumber from "%/components/InputNumber";
import InputCurrency from "%/components/InputCurrency";
import someProp from "%/utilities/someProp";
import getQueryData from "%/utilities/getQueryData";

export const ItemsEditContext = createContext();

export default () => {
    const { key } = useParams();
    const history = useHistory();

    const project = useProject();
    const edit = useEdit();
    const entity = useItem();

    const queries = useQueriesClient({
        paths: [
            [`schemas/${edit.action}-${entity.namePlural}`],
            [entity.namePlural, key]
        ],
        config: { refetchOnWindowFocus: false }
    });

    const [schemaData, entityData] = queries.map(getQueryData);

    const form = useForm({
        resolver: useYupSchemaResolver({ schemaData, originalData: entityData })
    });

    const page = usePage({
        history,
        action: edit.action,
        key: entityData?.[entity.keyField],
        nameSingular: entity.nameSingular,
        namePlural: entity.namePlural,
        projectName: project.name
    });

    const breadcrumbs = [page.breadcrumb, entity.breadcrumb, edit.breadcrumb];

    const mutation = useEditClient({
        path: [entity.namePlural, key],
        context: {
            namePlural: entity.namePlural,
            keyField: entity.keyField,
            history,
            originalData: entityData,
            setValue: form.setValue
        }
    });

    const context = {
        page,
        edit,
        entity,
        breadcrumbs,
        form,
        mutation,
        schemaData,
        entityData
    };

    if (someProp("isLoading", queries)) {
        return <LoadingBanner />;
    }

    if (someProp("isError", queries)) {
        return <ErrorBanner />;
    }

    return (
        <ItemsEditContext.Provider value={context}>
            <Page2 context={ItemsEditContext}>
                <SidebarLayout
                    sidebar={<EditSidebar2 context={ItemsEditContext} />}
                >
                    <Form context={ItemsEditContext}>
                        <FormSubheading level={3}>Details</FormSubheading>
                        <RegisteredField2
                            name="name"
                            title="Name"
                            Component={Textfield}
                            columnWidths={{ lg: 6, md: 12 }}
                            context={ItemsEditContext}
                        />
                        <RegisteredField2
                            name="sku"
                            title="SKU"
                            Component={Textfield}
                            columnWidths={{ lg: 3, md: 6 }}
                            context={ItemsEditContext}
                        />
                        <RegisteredField2
                            name="upc"
                            title="UPC"
                            Component={Textfield}
                            columnWidths={{ lg: 3, md: 6 }}
                            context={ItemsEditContext}
                        />
                        <ControlledField2
                            name="description"
                            title="Description"
                            Component={MarkdownTextarea}
                            columnWidths={{ sm: 12 }}
                            context={ItemsEditContext}
                        />
                        <FormSubheading level={3}>Stock</FormSubheading>
                        <ControlledField2
                            name="acquisition_date"
                            title="Acquisition date"
                            Component={InputDate}
                            columnWidths={{ lg: 6 }}
                            context={ItemsEditContext}
                        />
                        <ControlledField2
                            name="expiration_date"
                            title="Expiration date"
                            Component={InputDate}
                            columnWidths={{ lg: 6 }}
                            context={ItemsEditContext}
                        />
                        <ControlledField2
                            name="unit_cost"
                            title="Unit cost"
                            Component={InputCurrency}
                            columnWidths={{ lg: 3, md: 6 }}
                            context={ItemsEditContext}
                        />
                        <ControlledField2
                            name="unit_price"
                            title="Unit price"
                            Component={InputCurrency}
                            columnWidths={{ lg: 3, md: 6 }}
                            context={ItemsEditContext}
                        />
                        <ControlledField2
                            name="quantity_available"
                            title="Quantity available"
                            Component={InputNumber}
                            columnWidths={{ lg: 3, md: 6 }}
                            context={ItemsEditContext}
                        />
                        <ControlledField2
                            name="quantity_sold"
                            title="Quantity sold"
                            Component={InputNumber}
                            columnWidths={{ lg: 3, md: 6 }}
                            context={ItemsEditContext}
                        />
                        <FormSubheading level={3}>Misc.</FormSubheading>
                        <ControlledField2
                            name="additional_notes"
                            title="Additional notes"
                            Component={MarkdownTextarea}
                            columnWidths={{ sm: 12 }}
                            context={ItemsEditContext}
                        />
                    </Form>
                </SidebarLayout>
            </Page2>
        </ItemsEditContext.Provider>
    );
};
