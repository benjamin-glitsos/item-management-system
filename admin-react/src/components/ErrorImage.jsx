import makeImagePath from "%/utilities/makeImagePath";

export default () => (
    <img
        src={makeImagePath(["/images/error.svg"])}
        alt="Illustration that represents an 'error' - a man picks up a shape that has fallen to the ground"
    />
);
