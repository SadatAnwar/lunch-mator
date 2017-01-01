import {Component} from '@angular/core';

@Component({
  selector: 'about',
  templateUrl: 'assets/javascripts/app/common/components/about/about.component.html'
})

export class AboutComponent {
  lines: String[];
  title: String;

  constructor() {
    this.title = "Lunch-Mator:";
    this.lines = [
      "Its simple, allow reBuy to come closer during lunch. We might have started as a small and organic company, but let's face it, " +
      "times have changed and we have grown up (to our advantage of course).",
      "This however does mean that we dont get to meet fellow colleagues from other teams. Lunch-mator, is here to" +
      " to answer that exact problem.",
      "You can now have lunch with colleagues you would not normally see during your work day! Its fun, give it a try!"
    ];
  }
}


