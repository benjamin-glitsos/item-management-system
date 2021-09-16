// import { useMutation as useReactMutation } from "react-query";
// import client from "%/utilities/client";
// import toast from "%/utilities/toast";
// import unspecifiedErrorToast from "%/utilities/unspecifiedErrorToast";
//
// export default ({
//     method,
//     path,
//     body,
//     clientOptions = {},
//     mutationOptions = {}
// }) =>
//     useReactMutation(
//         path,
//         () => client({ method, path, body, clientOptions }),
//         {
//             retry: false,
//             ...mutationOptions
//         }
//     );
