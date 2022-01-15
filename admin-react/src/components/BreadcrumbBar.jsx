import Breadcrumbs, { BreadcrumbsItem } from "@atlaskit/breadcrumbs";
import styled from "styled-components";
import { useHistory } from "react-router-dom";

export default ({ breadcrumbs }) => {
    const history = useHistory();
    const handleClick = path => {
        history.push(`/${path}`);
    };

    const breadcrumbsData = breadcrumbs
        .reduce((acc, [currTitle, currPath], i) => {
            const prev = acc[i - 1];
            var path = currPath;
            if (prev) {
                const prevPath = prev.path;
                if (prevPath === "/") {
                    path = prevPath + currPath;
                } else {
                    path = [prevPath, currPath].join("/");
                }
            }
            return [...acc, { title: currTitle, key: currPath, path }];
        }, [])
        .map(({ key, path, title }, i) => ({
            text: title,
            key: `BreadcrumbBar/${key},${i}`,
            href: path,
            isDisabled: i + 1 === breadcrumbs.length
        }));

    return (
        <BreadcrumbBar>
            {breadcrumbsData.map(props => (
                <BreadcrumbsItem
                    {...props}
                    onClick={() => handleClick(props.href)}
                />
            ))}
        </BreadcrumbBar>
    );
};

const BreadcrumbBar = styled(Breadcrumbs)`
    a[disabled] {
        cursor: default !important;
    }
`;
