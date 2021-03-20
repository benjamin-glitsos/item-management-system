export default ({ title: _title, description }) => {
    const title = `${_title} : ${process.env.PROJECT_ABBREV || "IMS"}`;
    return { title, description };
};
