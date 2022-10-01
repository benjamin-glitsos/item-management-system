import { useState, useCallback } from "react";
import styled from "styled-components";
import Lightbox from "react-simple-image-viewer";

export default ({ images }) => {
    const [currentImage, setCurrentImage] = useState(0);
    const [isViewerOpen, setIsViewerOpen] = useState(false);

    const openImageViewer = useCallback(index => {
        setCurrentImage(index);
        setIsViewerOpen(true);
    }, []);

    const closeImageViewer = () => {
        setCurrentImage(0);
        setIsViewerOpen(false);
    };

    return (
        <LightboxStyles>
            {images.map(({ src, alt }, index) => (
                <Image
                    src={src}
                    onClick={() => openImageViewer(index)}
                    key={`ImageLightbox/${src},${index}`}
                    alt={alt}
                />
            ))}

            {isViewerOpen && (
                <Lightbox
                    src={images.map(image => image.src)}
                    currentIndex={currentImage}
                    onClose={closeImageViewer}
                    backgroundStyle={{
                        backgroundColor: "rgba(9, 30, 66, 0.54)"
                    }}
                />
            )}
        </LightboxStyles>
    );
};

const LightboxStyles = styled.div`
    .react-simple-image-viewer__close {
        font-weight: normal;
        top: 0;
    }
`;

const Image = styled.img`
    max-width: 550px;
`;
