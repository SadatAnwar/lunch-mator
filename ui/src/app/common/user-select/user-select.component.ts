import {Component, OnInit, EventEmitter} from '@angular/core';
import {CompleterItem, CompleterData} from 'ng2-completer';
import {UserLookupService} from '../../services/user-lookup.service';
import {HipChatUser, HipChatPing} from '../../types';

@Component({
  selector: 'user-select',
  templateUrl: './user-select.component.html',
  styleUrls: ['./user-select.component.scss']
})
export class UserSelectComponent implements OnInit {
  inviteUsers: HipChatPing[] = [];
  items$: EventEmitter<any> = new EventEmitter<any>();
  protected captains = ['James T. Kirk', 'Benjamin Sisko', 'Jean-Luc Picard', 'Spock', 'Jonathan Archer', 'Hikaru Sulu', 'Christopher Pike', 'Rachel Garrett'];

  constructor(private userLookupService: UserLookupService) {
  }

  ngOnInit() {
  }

  protected searchStr: string;
  protected dataService: CompleterData;
  protected selectedColor: string;
  protected searchData = ['red', 'green', 'blue', 'cyan', 'magenta', 'yellow', 'black'];

  protected onSelected(item: CompleterItem) {
    this.selectedColor = item ? item.title : "";
  }


  private value: HipChatUser[];

  public selected(item: any): void {
    this.items$.next([]);
  }

  public removeUser(item: any): void {
    this.items$.next([]);
  }

  public searchUsers(value: any): void {
    if (value.trim().length > 1) {
      this.userLookupService.search(value.trim()).subscribe((users: HipChatUser[]) => {
        const items = [];
        users.forEach((user: HipChatUser) => {
          let entry: any = {};
          entry.text = user.name;
          entry.id = user.mention_name;

          items.push(entry);
        });
        this.items$.emit(items);
      })
    }
  }

  public refreshValue(value: any[]): void {
    this.inviteUsers = [];
    value.forEach(item => {
      let hipChatPing: HipChatPing = {mention_name: item.id};
      this.inviteUsers.push(hipChatPing);
    });

  }
}
