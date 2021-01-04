import { Component } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { SmartTableData } from "../../../@core/data/smart-table";

@Component({
    selector: "ngx-users-list",
    templateUrl: "./list.component.html",
    styleUrls: ["./list.component.scss"]
})
export class UsersListComponent {
    // username, email_address, created_at, edited_at
    data;

    constructor(private http: HttpClient) {}

    ngOnInit() {
        this.http
            .request("REPORT", "http://localhost:4073/api/v1/users/bengyup/", {
                body: { wow: "cool" }
            })
            .subscribe(data$ => {
                console.log(data$);
                this.data = data$;
            });
    }

    products = [
        {
            name: "Create new",
            icon: "plus"
        },
        {
            name: "Delete ____number____ items",
            icon: "trash",
            items: [
                {
                    name: "Delete",
                    icon: "trash"
                },
                {
                    name: "Hard delete",
                    icon: "trash"
                }
            ]
        }
    ].reverse();

    actions: Array<{ text: String; icon: String }> = [
        { text: "Delete", icon: "trash" },
        { text: "Hard delete", icon: "trash" }
    ];

    deleteActions: Array<{ text: String; icon: String }> = [
        { text: "Hard delete", icon: "trash" }
    ];
}
