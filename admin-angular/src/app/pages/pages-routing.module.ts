import { RouterModule, Routes } from "@angular/router";
import { NgModule } from "@angular/core";

import { PagesComponent } from "./pages.component";
import { NotFoundComponent } from "./miscellaneous/not-found/not-found.component";

const routes: Routes = [
    {
        path: "",
        component: PagesComponent,
        children: [
            {
                path: "forms",
                loadChildren: () =>
                    import("./forms/forms.module").then(m => m.FormsModule)
            },
            {
                path: "users",
                loadChildren: () =>
                    import("./users/users.module").then(m => m.UsersModule)
            },
            {
                path: "miscellaneous",
                loadChildren: () =>
                    import("./miscellaneous/miscellaneous.module").then(
                        m => m.MiscellaneousModule
                    )
            },
            {
                path: "",
                redirectTo: "users",
                pathMatch: "full"
            },
            {
                path: "**",
                component: NotFoundComponent
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class PagesRoutingModule {}
