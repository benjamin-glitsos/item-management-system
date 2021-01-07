import { Component, ViewChild } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { SmartTableData } from "../../../@core/data/smart-table";
import { DxDataGridComponent } from "devextreme-angular";

@Component({
    selector: "ngx-users-list",
    templateUrl: "./list.component.html",
    styleUrls: ["./list.component.scss"]
})
export class UsersListComponent {
    @ViewChild("listDataGrid", { static: false }) dataGrid: DxDataGridComponent;

    refreshDataGrid() {
        this.dataGrid.instance
            .refresh()
            .then(function () {})
            .catch(function (error) {});
    }

    headers = ["username", "email_address", "created_at", "edited_at"];

    zipIntoObj = (xs, ys) => {
        // TODO: check Ramda source code for their version of this function
        var obj = {};
        xs.forEach((key, i) => (obj[key] = ys[i]));
        return obj;
    };

    evolve = (transformations, object) => {
        // TODO: check if this is the latest version of function from Ramda source code
        var result = {};
        var transformation, key, type;
        for (key in object) {
            transformation = transformations[key];
            type = typeof transformation;
            result[key] =
                type === "function"
                    ? transformation(object[key])
                    : transformation && type === "object"
                    ? this.evolve(transformation, object[key])
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
                    .map(data => this.evolve({ edited_at: ([d]) => d }, data))
                    .map((data, i) => ({
                        id: i + 1,
                        ...data
                    }));
                console.log(this.data);
                this.refreshDataGrid();
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
