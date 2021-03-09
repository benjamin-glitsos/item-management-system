import Breadcrumbs, { BreadcrumbsItem } from "@atlaskit/breadcrumbs";
import styled from "styled-components";

export default ({ breadcrumbs }) => (
    <DisabledStyles>
         <Breadcrumbs>
             {breadcrumbs.map(([title, path], i) => (
                 <BreadcrumbsItem
                     text={title}
                     key={`BreadcrumbBar/${i}-${title}`}
                     href={path}
                     isDisabled={i + 1 === breadcrumbs.length}
                 />
             ))}
         </Breadcrumbs>
    </DisabledStyles>
);

const DisabledStyles = styled.div`
    a[disabled] {
        cursor: default;
    }
`;
