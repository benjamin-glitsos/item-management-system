import { Component, Inject } from "@angular/core";
import {
    HttpClient,
    HttpHeaders,
    HttpClientModule
} from "@angular/common/http";
import { DxDataGridComponent } from "devextreme-angular";
import CustomStore from "devextreme/data/custom_store";
import DataSource from "devextreme/data/data_source";
import "rxjs/add/operator/toPromise";

@Component({
    selector: "ngx-users-list",
    templateUrl: "./list.component.html",
    styleUrls: ["./list.component.scss"]
})
export class UsersListComponent {
    // TODO: https://js.devexpress.com/Documentation/ApiReference/Data_Layer/CustomStore/
    store: CustomStore;
    dataSource: DataSource;
    constructor(@Inject(HttpClient) httpClient: HttpClient) {
        this.store = new CustomStore({
            key: "grid_id",
            load: options => {
                console.log(options);
                return httpClient
                    .request("REPORT", "http://localhost:4073/api/v1/users/", {
                        body: {
                            page_number: 1,
                            page_length: options.take
                        }
                    })
                    .toPromise()
                    .then(result => {
                        console.log(result);
                        // TODO: SET SEED FACTOR TO 10 OR MORE THEN TRY USING PAGINATION
                        // !!!!!!!!!
                        // !!!!!!!!!
                        return {
                            totalCount: 100, // TODO: result.total_items
                            data: result.data
                                .map(data =>
                                    this.zipIntoObj(this.headers, data)
                                )
                                .map(data =>
                                    this.evolve(
                                        {
                                            edited_at: ([d]) => {
                                                if (d) {
                                                    return d;
                                                } else {
                                                    return "-";
                                                }
                                            }
                                        },
                                        data
                                    )
                                )
                                .map((data, i) => ({
                                    grid_id: i + 1,
                                    ...data
                                }))
                                .map(data => {
                                    console.log(data);
                                    return data;
                                })
                        };
                    });
            }
            remove: key => {
                key
            }
            errorHandler: function (error) {
                // TODO: handle errors using the devextreme toaster popup
                console.log(error.message);
            }
        });

        this.dataSource = new DataSource({
            key: "grid_id"
        });
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
