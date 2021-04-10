import { Fragment } from "react";
import { useContext, useEffect, useState } from "react";
import styled from "styled-components";
import ActionsMenu from "%/presenters/ActionsMenu";
import PageLengthSelect from "%/presenters/PageLengthSelect";
import ButtonGroup from "@atlaskit/button/button-group";
import Textfield from "@atlaskit/textfield";
import { ListContext } from "%/components/List";

export default () => {
    const context = useContext(ListContext);
    const isDataEmpty = context.isDataEmpty;
    const isLoading = context.state.isLoading;
    const setPageNumber = context.setPageNumber;
    const setPageLength = context.setPageLength;
    const setSearch = context.setSearch;
    const search = context.query.search;
    const pageLength = context.state.response.data.page_length;

    const [searchState, setSearchState] = useState("");

    useEffect(() => setSearchState(search), []);

    return (
        <Fragment>
            <ButtonGroup>
                <ButtonRightSpace>
                    <ActionsMenu
                        items={context.state.selected}
                        additionalItems={[
                            {
                                title: "Deselect",
                                onClick: context.setRemoveAllSelected,
                                isVisible: context.state.selected.length > 0
                            }
                        ]}
                    />
                </ButtonRightSpace>
                <Textfield
                    aria-label="Search"
                    isCompact={true}
                    placeholder="Search"
                    value={searchState}
                    onChange={search => {
                        const value = search.target.value;
                        setSearch(value);
                        setSearchState(value);
                    }}
                />
                <PageLengthSelect
                    isDisabled={isDataEmpty}
                    pageLength={pageLength}
                    setPageLength={setPageLength}
                />
            </ButtonGroup>
        </Fragment>
    );
};

const ButtonRightSpace = styled.span`
    button {
        margin-right: 0.5em;
    }
`;
