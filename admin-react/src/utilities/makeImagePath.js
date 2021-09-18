import joinPath from "%/utilities/joinPath";

export default path => joinPath([process.env.PUBLIC_URL, "images", path]);
