import {Component, ElementRef, Input} from '@angular/core';

@Component({
  selector: 'auto-complete',
  templateUrl: './auto-complete.component.html',
  host: {
    '(document:click)': 'handleClick($event)',
  },
  styleUrls: ['./auto-complete.component.scss']
})
export class AutoCompleteComponent {
  public query = '';
  public filteredList = [];
  public selected = [];
  public elementRef;

  @Input() placeholder: string;
  @Input() above: boolean;
  @Input() dataProvider = ['Hello', 'World'];

  constructor(myElement: ElementRef) {
    this.elementRef = myElement;
  }

  filter() {
    if (this.query !== "") {
      this.filteredList = this.dataProvider.filter(function(el) {
        return el.toLowerCase().indexOf(this.query.toLowerCase()) > -1;
      }.bind(this));
    } else {
      this.filteredList = [];
    }
  }

  select(item) {
    this.selected.push(item);
    this.query = '';
    this.filteredList = [];
  }

  remove(item) {
    this.selected.splice(this.selected.indexOf(item), 1);
  }

  handleClick(event) {
    var clickedComponent = event.target;
    var inside = false;
    do {
      if (clickedComponent === this.elementRef.nativeElement) {
        inside = true;
      }
      clickedComponent = clickedComponent.parentNode;
    } while (clickedComponent);
    if (!inside) {
      this.filteredList = [];
    }
  }
}
