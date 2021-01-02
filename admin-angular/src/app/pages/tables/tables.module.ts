import { NgModule } from "@angular/core";
import { NbCardModule, NbIconModule, NbInputModule } from "@nebular/theme";
import { Ng2SmartTableModule } from "ng2-smart-table";

import { ThemeModule } from "../../@theme/theme.module";
import { TablesRoutingModule, routedComponents } from "./tables-routing.module";
import { AgGridModule } from "ag-grid-angular";

@NgModule({
    imports: [
        NbCardModule,
        NbIconModule,
        NbInputModule,
        ThemeModule,
        TablesRoutingModule,
        Ng2SmartTableModule,
        AgGridModule.withComponents([])
    ],
    declarations: [...routedComponents]
})
export class TablesModule {}
