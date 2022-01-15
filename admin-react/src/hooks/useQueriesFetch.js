import { useEffect } from "react";

export default queries =>
    useEffect(async () => {
        for await (const query of queries) {
            query.refetch();
        }
    }, []);
