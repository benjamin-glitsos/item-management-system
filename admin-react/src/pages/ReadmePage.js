import PageMargins from "../components/PageMargins";
import ReactMarkdown from "react-markdown";
import readme from "../assets/README.md";

export default () => (
    <PageMargins>
        <ReactMarkdown>{readme}</ReactMarkdown>
    </PageMargins>
);
