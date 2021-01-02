import { Component } from "@angular/core";

@Component({
    selector: "ngx-users-list",
    templateUrl: "./list.component.html",
    styleUrls: ["./list.component.scss"]
})
export class UsersListComponent {
    defaultColDef = { type: "nonEditableColumn" };

    columnDefs = [
        { headerName: "Username", field: "username" },
        { headerName: "Email Address", field: "email_address" },
        { headerName: "Edited At", field: "edited_at" },
        { headerName: "Actions", field: "actions" }
    ];

    rowData = [
        {
            username: "Toyota",
            email_address: "Celica",
            edited_at: 35000,
            actions: "..."
        }
    ];
}
