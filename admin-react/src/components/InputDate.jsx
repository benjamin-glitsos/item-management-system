import { DatePicker } from "@atlaskit/datetime-picker";

export default ({ ...props }) => (
    <DatePicker
        dateFormat="DD/MM/YYYY"
        weekStartDay={0}
        hideIcon={true}
        {...props}
    />
);
