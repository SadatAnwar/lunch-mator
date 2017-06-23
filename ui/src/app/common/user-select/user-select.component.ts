import {Component, OnInit, EventEmitter} from '@angular/core';
import {CompleterItem, CompleterData} from 'ng2-completer';
import {InvitationService} from '../../services/invitation.service';
import {UserLookupService} from '../../services/user-lookup.service';
import {HipChatUser, HipChatPing, InvitationDto} from '../../types';

@Component({
  selector: 'user-select',
  templateUrl: './user-select.component.html',
  styleUrls: ['./user-select.component.scss']
})
export class UserSelectComponent implements OnInit {

  inviteUsers: HipChatPing[] = [];

  items$: EventEmitter<any> = new EventEmitter<any>();

  constructor(private userLookupService: UserLookupService, private invitationService: InvitationService) {
  }

  ngOnInit() {
  }

  protected dataService: CompleterData;
  protected selectedColor: string;

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

  public sendInvitation(lunchId: number) {
    if (this.inviteUsers.length > 0) {
      let invitation: InvitationDto = {users: this.inviteUsers, lunchId: lunchId};
      this.invitationService.invite(invitation).subscribe(() => console.log("invitation success for ", invitation));
    }
  }
}
