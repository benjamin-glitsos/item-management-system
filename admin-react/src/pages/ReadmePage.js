import ContentWrapper from "../components/ContentWrapper";
import ReactMarkdown from "react-markdown";
import readme from "../assets/README.md";

export default () => (
    <ContentWrapper>
        <ReactMarkdown>{readme}</ReactMarkdown>
    </ContentWrapper>
);
