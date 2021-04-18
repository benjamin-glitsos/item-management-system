import { useState } from "react";
import ReactMarkdown from "react-markdown";
import ReactMde from "react-mde";

export default props => {
    const [selectedTab, setSelectedTab] = useState("write");
    return (
        <ReactMde
            selectedTab={selectedTab}
            onTabChange={setSelectedTab}
            generateMarkdownPreview={markdown =>
                Promise.resolve(<ReactMarkdown source={markdown} />)
            }
            {...props}
        />
    );
};
