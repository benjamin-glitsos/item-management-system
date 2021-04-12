import { Fragment } from "react";
import { useContext, useEffect, useState } from "react";
import styled from "styled-components";
import ActionsMenu from "%/components/ActionsMenu";
import PageLengthSelect from "%/components/PageLengthSelect";
import ButtonGroup from "@atlaskit/button/button-group";
import Textfield from "@atlaskit/textfield";
import { ListContext } from "%/components/List/List";

export default () => {
    const context = useContext(ListContext);
    const isDataEmpty = context.isDataEmpty;
    const isLoading = context.state.isLoading;
    const setPageNumber = context.setPageNumber;
    const setPageLength = context.setPageLength;
    const setSearch = context.setSearch;
    const setDeselectAll = context.setDeselectAll;
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
                                onClick: context.setDeselectAll,
                                isVisible: context.state.selected.length > 0
                            }
                        ]}
                        deleteItemsAction={context.deleteItemsAction}
                    />
                </ButtonRightSpace>
                <Textfield
                    aria-label="Search"
                    isCompact={true}
                    placeholder="Search"
                    value={searchState}
                    onChange={search => {
                        const value = search.target.value;
                        setDeselectAll();
                        setSearch(value);
                        setSearchState(value);
                    }}
                />
                <PageLengthSelect
                    isDisabled={isDataEmpty}
                    pageLength={pageLength}
                    setPageLength={setPageLength}
                    setDeselectAll={setDeselectAll}
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
