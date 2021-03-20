import DeletionMenu from "%/presenters/DeletionMenu";
import PageLengthSelect from "%/presenters/PageLengthSelect";
import ButtonGroup from "@atlaskit/button/button-group";

export default ({
    doesDataExist,
    isDeletable,
    softDeleteAction,
    hardDeleteAction,
    pageLength,
    setPageLength
}) => (
    <ButtonGroup>
        <DeletionMenu
            isVisible={doesDataExist}
            isDeletable={isDeletable}
            softDeleteAction={softDeleteAction}
            hardDeleteAction={hardDeleteAction}
        />
        <PageLengthSelect
            isVisible={doesDataExist}
            pageLength={pageLength}
            setPageLength={setPageLength}
        />
    </ButtonGroup>
);
