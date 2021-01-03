import { NgModule } from "@angular/core";
import { NbCardModule, NbIconModule, NbInputModule } from "@nebular/theme";

import { ThemeModule } from "../../@theme/theme.module";
import { UsersRoutingModule, routedComponents } from "./users-routing.module";
import { Ng2SmartTableModule } from "ng2-smart-table";
import { DxDropDownButtonModule, DxMenuModule } from "devextreme-angular";

@NgModule({
    imports: [
        NbCardModule,
        NbIconModule,
        NbInputModule,
        ThemeModule,
        UsersRoutingModule,
        Ng2SmartTableModule,
        DxMenuModule,
        DxDropDownButtonModule
    ],
    declarations: [...routedComponents]
})
export class UsersModule {}
