import { Component, Inject } from "@angular/core";
import {
    HttpClient,
    HttpHeaders,
    HttpClientModule,
    HttpParams
} from "@angular/common/http";
import { SmartTableData } from "../../../@core/data/smart-table";
import { DxDataGridComponent } from "devextreme-angular";
import DataSource from "devextreme/data/data_source";
import "rxjs/add/operator/toPromise";

@Component({
    selector: "ngx-users-list",
    templateUrl: "./list.component.html",
    styleUrls: ["./list.component.scss"]
})
export class UsersListComponent {
    headers = ["username"];

    gridDataSource: any = {};
    constructor(@Inject(HttpClient) httpClient: HttpClient) {
        this.gridDataSource = new DataSource({
            key: "id",
            load: loadOptions => {
                ["requireTotalCount"].forEach(function (i) {
                    if (i in loadOptions && isNotEmpty(loadOptions[i]))
                        params = params.set(i, JSON.stringify(loadOptions[i]));
                });
                return httpClient
                    .request("REPORT", "http://localhost:4073/api/v1/users/", {
                        body: {
                            page_number: 1,
                            page_length: 25
                        }
                    })
                    .toPromise()
                    .then(result => {
                        console.log(result);
                        return {
                            totalCount: result.total_items,
                            data: result.data
                                .map(data =>
                                    this.zipIntoObj(this.headers, data)
                                )
                                // .map(data =>
                                //     this.evolve({ edited_at: ([d]) => d }, data)
                                // )
                                .map(data => ({ username: data.username }))
                                .map((data, i) => ({
                                    id: i + 1,
                                    ...data
                                }))
                                .map(data => {
                                    console.log(data);
                                    return data;
                                })
                        };
                    });
            }
        });
    }

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
