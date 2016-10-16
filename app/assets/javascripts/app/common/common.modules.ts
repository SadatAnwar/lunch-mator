import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {routing} from "app/authentication/authentication.routing";
import {ErrorDetailComponent} from "app/common/components/error/error-detail.component";

@NgModule({
  imports: [CommonModule, FormsModule, routing],
  declarations: [ErrorDetailComponent],
  exports: [ErrorDetailComponent]
})
export class CommonModules {
}
