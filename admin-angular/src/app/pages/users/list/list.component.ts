import { Component } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { SmartTableData } from "../../../@core/data/smart-table";
import { LocalDataSource } from "ng2-smart-table";

@Component({
    selector: "ngx-users-list",
    templateUrl: "./list.component.html",
    styleUrls: ["./list.component.scss"]
})
export class UsersListComponent {
    settings = {
        hideSubHeader: true,
        actions: {
            position: "right"
        },
        selectMode: "multi",
        add: {
            addButtonContent: '<i class="nb-plus"></i>',
            createButtonContent: '<i class="nb-checkmark"></i>',
            cancelButtonContent: '<i class="nb-close"></i>'
        },
        edit: {
            editButtonContent: '<i class="nb-edit"></i>',
            saveButtonContent: '<i class="nb-checkmark"></i>',
            cancelButtonContent: '<i class="nb-close"></i>'
        },
        delete: {
            deleteButtonContent: '<i class="nb-trash"></i>',
            confirmDelete: true
        },
        columns: {
            id: {
                title: "ID",
                type: "number"
            },
            firstName: {
                title: "First Name",
                type: "string"
            },
            lastName: {
                title: "Last Name",
                type: "string"
            },
            username: {
                title: "Username",
                type: "string"
            },
            email: {
                title: "E-mail",
                type: "string"
            },
            age: {
                title: "Age",
                type: "number"
            }
        }
    };

    source: LocalDataSource = new LocalDataSource();

    constructor(private service: SmartTableData) {
        const data = this.service.getData();
        this.source.load(data);
    }

    onDeleteConfirm(event): void {
        if (window.confirm("Are you sure you want to delete?")) {
            event.confirm.resolve();
        } else {
            event.confirm.reject();
        }
    }

    defaultColDef = { editable: false, flex: 1, minWidth: 100 };

    columnDefs = [
        { headerName: "Username", field: "username" },
        { headerName: "Email Address", field: "email_address" },
        {
            headerName: "Created At",
            field: "created_at",
            filter: "agDateColumnFilter"
        },
        {
            headerName: "Edited At",
            field: "edited_at",
            filter: "agDateColumnFilter"
        },
        { headerName: "Actions", field: "actions", maxWidth: 200 }
    ];

    rowData = [
        {
            username: "Toyota",
            email_address: "Celica",
            created_at: 34000,
            edited_at: 35000,
            actions: "..."
        }
    ];

    actions: Array<{ id: Number; text: String; icon: String }> = [
        { id: 1, text: "My profile", icon: "user" },
        { id: 2, text: "Messages", icon: "email" },
        { id: 3, text: "Contacts", icon: "group" },
        { id: 4, text: "Log out", icon: "runner" }
    ];

    logAction(e) {
        console.log(e.itemData.text + " was clicked");
    }

    logButtonClick() {
        console.log("Main button was clicked");
    }

    products: Product[] = [
        {
            id: "1",
            name: "Video Players",
            items: [
                {
                    id: "1_1",
                    name: "HD Video Player",
                    price: 220,
                    icon: "images/products/1.png"
                },
                {
                    id: "1_2",
                    name: "SuperHD Video Player",
                    icon: "images/products/2.png",
                    price: 270
                }
            ]
        },
        {
            id: "2",
            name: "Televisions",
            items: [
                {
                    id: "2_1",
                    name: "SuperLCD 42",
                    icon: "images/products/7.png",
                    price: 1200
                },
                {
                    id: "2_2",
                    name: "SuperLED 42",
                    icon: "images/products/5.png",
                    price: 1450
                },
                {
                    id: "2_3",
                    name: "SuperLED 50",
                    icon: "images/products/4.png",
                    price: 1600
                },
                {
                    id: "2_4",
                    name: "SuperLCD 55 (Not available)",
                    icon: "images/products/6.png",
                    price: 1350,
                    disabled: true
                },
                {
                    id: "2_5",
                    name: "SuperLCD 70",
                    icon: "images/products/9.png",
                    price: 4000
                }
            ]
        },
        {
            id: "3",
            name: "Monitors",
            items: [
                {
                    id: "3_1",
                    name: '19"',
                    items: [
                        {
                            id: "3_1_1",
                            name: "DesktopLCD 19",
                            icon: "images/products/10.png",
                            price: 160
                        }
                    ]
                },
                {
                    id: "3_2",
                    name: '21"',
                    items: [
                        {
                            id: "3_2_1",
                            name: "DesktopLCD 21",
                            icon: "images/products/12.png",
                            price: 170
                        },
                        {
                            id: "3_2_2",
                            name: "DesktopLED 21",
                            icon: "images/products/13.png",
                            price: 175
                        }
                    ]
                }
            ]
        },
        {
            id: "4",
            name: "Projectors",
            items: [
                {
                    id: "4_1",
                    name: "Projector Plus",
                    icon: "images/products/14.png",
                    price: 550
                },
                {
                    id: "4_2",
                    name: "Projector PlusHD",
                    icon: "images/products/15.png",
                    price: 750
                }
            ]
        }
    ];

    // onGridReady(params) {
    //     this.http.get("http://localhost:${process.env.CONTROLLER_PORT}/api/v1/users/").subscribe(data => {
    //         this.rowData = data.data;
    //     });
    // }
}
