import { Fragment } from "react";
import Modal, { ModalTransition } from "@atlaskit/modal-dialog";

export default ({ src, alt }) => {
    const [isOpen, setIsOpen] = useState(false);
    const closeHandler = () => setIsOpen(false);
    const openHandler = () => setIsOpen(true);
    return (
        <Fragment>
            <img src={src} alt={alt} onClick={openHandler} />
            <ModalTransition>
                {isOpen && (
                    <Modal
                        actions={[
                            { text: "Try it now", onClick: closeHandler },
                            { text: "Learn more" }
                        ]}
                        onClose={closeHandler}
                        heading="Test drive your new search"
                    >
                        We’ve turbocharged your search results so you can get
                        back to doing what you do best.
                    </Modal>
                )}
            </ModalTransition>
        </Fragment>
    );
};
