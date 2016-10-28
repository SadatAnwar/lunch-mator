import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {LunchItemComponent} from "app/lunch/components/item/lunch-item.component";
import {LunchListComponent} from "app/lunch/components/list/lunch-list.component";
import {routing} from "app/lunch/lunch.routing";
import {CommonModules} from "../common/common.modules";
import {Ng2CompleterModule} from "ng2-completer";
import {AddLunchComponent} from "./components/addlunch/add-lunch.component";
import {FormsModule} from "@angular/forms";
import {LunchService} from "./service/lunch.service";
import {MyLunchListComponent} from "./components/mylunch/lunch-list.component";
import {LunchDetailComponent} from "./components/detail/lunch-detail.component";
import {CalenderService} from './service/calander.service';

@NgModule({
  imports: [CommonModule, CommonModules, Ng2CompleterModule, FormsModule, routing],
  declarations: [LunchItemComponent, LunchListComponent, LunchDetailComponent, MyLunchListComponent, AddLunchComponent],
  providers: [LunchService, CalenderService]
})
export class LunchModule {
}
