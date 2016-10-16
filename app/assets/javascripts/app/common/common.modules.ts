import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {routing} from 'app/authentication/authentication.routing';
import {ErrorDetailComponent} from 'app/common/components/error/error-detail.component';
import {AlertComponent} from './components/alert/alert.component';

@NgModule({
  imports: [CommonModule, FormsModule, routing],
  declarations: [ErrorDetailComponent, AlertComponent],
  exports: [ErrorDetailComponent, AlertComponent]
})
export class CommonModules {
}
