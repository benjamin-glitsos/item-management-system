import { Component } from "@angular/core";
import { HttpClient } from "@angular/common/http";

@Component({
    selector: "ngx-users-list",
    templateUrl: "./list.component.html",
    styleUrls: ["./list.component.scss"]
})
export class UsersListComponent {
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

    // onGridReady(params) {
    //     this.http.get("http://localhost/api/v1/users/").subscribe(data => {
    //         this.rowData = data.data;
    //     });
    // }
}
