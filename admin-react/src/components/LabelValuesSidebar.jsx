import { Fragment } from "react";

import LabelValueItem from "%/components/LabelValueItem";

export default ({ items }) => (
    <Fragment>
        {Object.entries(items).map(([label, value], i) =>
            value !== undefined ? (
                <LabelValueItem
                    key={`LabelValueItem/${label},${i}`}
                    label={label}
                    value={value}
                />
            ) : null
        )}
    </Fragment>
);
