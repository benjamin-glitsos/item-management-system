import { useState } from "react";
import ReactMarkdown from "react-markdown";
import ReactMde from "react-mde";
import formatNull from "utilities/formatNull";

export default ({ placeholder, ...props }) => {
    const [selectedTab, setSelectedTab] = useState("write");
    return (
        <ReactMde
            selectedTab={selectedTab}
            onTabChange={setSelectedTab}
            generateMarkdownPreview={markdown =>
                Promise.resolve(<ReactMarkdown source={markdown} />)
            }
            childProps={{ textArea: { placeholder } }}
            maxEditorHeight={150}
            {...props}
        />
    );
};
