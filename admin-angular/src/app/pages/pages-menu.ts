import { NbMenuItem } from "@nebular/theme";

export const MENU_ITEMS: NbMenuItem[] = [
    {
        title: "USERS",
        group: true
    },
    {
        title: "Users",
        icon: "people-outline",
        link: "/users",
        home: true
    },
    {
        title: "Forms",
        icon: "edit-2-outline",
        children: [
            {
                title: "Form Inputs",
                link: "/pages/forms/inputs"
            },
            {
                title: "Form Layouts",
                link: "/pages/forms/layouts"
            },
            {
                title: "Buttons",
                link: "/pages/forms/buttons"
            },
            {
                title: "Datepicker",
                link: "/pages/forms/datepicker"
            }
        ]
    },
    {
        title: "Miscellaneous",
        icon: "shuffle-2-outline",
        children: [
            {
                title: "404",
                link: "/pages/miscellaneous/404"
            }
        ]
    }
];
