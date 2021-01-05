import { Component } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { SmartTableData } from "../../../@core/data/smart-table";

@Component({
    selector: "ngx-users-list",
    templateUrl: "./list.component.html",
    styleUrls: ["./list.component.scss"]
})
export class UsersListComponent {
    headers = ["username", "email_address", "created_at", "edited_at"];

    zipIntoObj = (xs, ys) => {
        var obj = {};
        xs.forEach((key, i) => (obj[key] = ys[i]));
        return obj;
    };

    evolve = (transformations, object) => {
        var result = {};
        var transformation, key, type;
        for (key in object) {
            transformation = transformations[key];
            type = typeof transformation;
            result[key] =
                type === "function"
                    ? transformation(object[key])
                    : transformation && type === "object"
                    ? evolve(transformation, object[key])
                    : object[key];
        }
        return result;
    };

    data = [];

    constructor(private http: HttpClient) {}

    ngOnInit() {
        this.http
            .request("REPORT", "http://localhost:4073/api/v1/users/", {
                body: {
                    page_number: 1,
                    page_length: 25
                }
            })
            .subscribe(data$ => {
                this.data = data$.data
                    .map(data => this.zipIntoObj(this.headers, data))
                    .map(data => this.evolve({ edited_at: x => null }, data));
                console.log(this.data);
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
