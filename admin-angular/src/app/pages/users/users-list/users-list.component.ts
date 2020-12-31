import { Component } from "@angular/core";

@Component({
    selector: "users-list",
    templateUrl: "./users-list.component.html",
    styleUrls: ["./users-list.component.scss"]
})
export class UsersListComponent {
    columnDefs = [
        { headerName: "Make", field: "make" },
        { headerName: "Model", field: "model" },
        { headerName: "Price", field: "price" }
    ];

    rowData = [
        { make: "Toyota", model: "Celica", price: 35000 },
        { make: "Ford", model: "Mondeo", price: 32000 },
        { make: "Porsche", model: "Boxter", price: 72000 }
    ];
}
