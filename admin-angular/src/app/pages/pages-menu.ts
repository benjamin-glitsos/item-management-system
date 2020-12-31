import { NbMenuItem } from "@nebular/theme";

export const MENU_ITEMS: NbMenuItem[] = [
    {
        title: "USERS",
        group: true
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
        title: "Tables & Data",
        icon: "grid-outline",
        children: [
            {
                title: "Smart Table",
                link: "/pages/tables/smart-table"
            },
            {
                title: "Tree Grid",
                link: "/pages/tables/tree-grid"
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
