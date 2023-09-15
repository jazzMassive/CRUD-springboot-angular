import { Component } from '@angular/core';
import { User } from '../user';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})

export class UserListComponent {

  users: User[];

  constructor() { }

  ngOnInit(): void {
    this.users = [{
      "id" : 2,
      "firstName" : "Ivan",
      "lastName" : "Horvat",
      "email" : "ivan.horvat@gmail.com"
    },
    {
      "id" : 2,
      "firstName" : "Ivan",
      "lastName" : "Horvat",
      "email" : "ivan.horvat@gmail.com"
    }
    ]
  }
}
