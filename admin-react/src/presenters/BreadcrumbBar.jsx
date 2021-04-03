import Breadcrumbs, { BreadcrumbsItem } from "@atlaskit/breadcrumbs";
import styled from "styled-components";
import { useHistory } from "react-router-dom";

export default ({ breadcrumbs }) => {
    const history = useHistory();
    const handleClick = path => {
        history.push(`/${path}`);
    };

    const breadcrumbsData = breadcrumbs.reduce(
        (acc, [currTitle, currPath], i) => {
            const prev = acc[i - 1];
            var path = currPath;
            if (prev) {
                const prevPath = prev[2];
                if (prevPath === "/") {
                    path = prevPath + currPath;
                } else {
                    path = [prevPath, currPath].join("/");
                }
            }
            return [...acc, [currTitle, currPath, path]];
        },
        []
    );
    console.log(breadcrumbsData);
    // breadcrumbs.reduce((acc, [currTitle, currPath], i) => {
    //     // var path = "";
    //     // if (acc) {
    //     //     if (acc[i].hasOwnProperty("href")) {
    //     //         path = "has";
    //     //     } else {
    //     //         path = "not";
    //     //     }
    //     // }
    //     // const prevPath = acc[i] !== [] && acc[i].href;
    //     // const path =
    //     //     prevPath && currPath !== "/"
    //     //         ? [prevPath, currPath].join("/")
    //     //         : currPath;
    //     const path = "";
    //     return [
    //         ...acc,
    //         {
    //             text: currTitle,
    //             key: `BreadcrumbBar/${i}-${currTitle}`,
    //             href: path,
    //             isDisabled: i + 1 === breadcrumbs.length
    //         }
    //     ];
    // }, []);

    // const breadcrumbsData = breadcrumbs.reduce(
    //     ([accTitle, accPath], [currTitle, currPath], i) => ({
    //         text: currTitle,
    //         key: `BreadcrumbBar/${i}-${currTitle}`,
    //         href: [current.href, path].join("/"),
    //         isDisabled: i + 1 === breadcrumbs.length
    //     }),
    //     { href: "" }
    // );

    // .map(props => (
    //     <BreadcrumbsItem
    //         {...props}
    //         onClick={() => handleClick(props.href)}
    //     />
    // ));

    return (
        <DisabledStyles>
            <Breadcrumbs></Breadcrumbs>
        </DisabledStyles>
    );
};

const DisabledStyles = styled.div`
    a[disabled] {
        cursor: default;
    }
`;
