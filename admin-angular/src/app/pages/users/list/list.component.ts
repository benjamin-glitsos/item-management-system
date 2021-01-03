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

    // onGridReady(params) {
    //     this.http.get("http://localhost:${process.env.CONTROLLER_PORT}/api/v1/users/").subscribe(data => {
    //         this.rowData = data.data;
    //     });
    // }
}
