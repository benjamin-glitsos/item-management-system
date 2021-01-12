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
    // TODO: start using dependency injection
    constructor(@Inject(HttpClient) httpClient: HttpClient) {
        dataSource: DataSource = new CustomStore({
            key: "username",
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
                                            edited_at: ([d]) => d ? d : "-"
                                        },
                                        data
                                    )
                                )
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
        // TODO: move all of the config options into the javascript object like in this example, then you can reuse them: https://js.devexpress.com/Demos/WidgetsGallery/Demo/DataGrid/WebAPIService/AngularJS/Light/
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
