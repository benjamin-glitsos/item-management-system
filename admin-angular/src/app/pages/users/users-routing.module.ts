import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

import { UsersComponent } from "./users.component";
import { UsersListComponent } from "./users-list/users-list.component";

const routes: Routes = [
    {
        path: "",
        component: UsersComponent,
        children: [
            {
                path: "users-list",
                component: UsersListComponent
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class TablesRoutingModule {}

export const routedComponents = [
    TablesComponent,
    SmartTableComponent,
    TreeGridComponent
];
