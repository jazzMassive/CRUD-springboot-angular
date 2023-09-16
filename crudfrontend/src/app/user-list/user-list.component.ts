import { Component } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { CreateUserComponent } from '../create-user/create-user.component';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})

export class UserListComponent {

  users: User[];
  bsModalRef: BsModalRef;

  constructor(private userService: UserService, private bsModalService: BsModalService) { }

  ngOnInit(): void {
    this.getUsers();
  }

  private getUsers(){
    this.userService.getUsersList().subscribe(data => {
      this.users = data;
    });
  }

  addUser(){
    this.bsModalRef = this.bsModalService.show(CreateUserComponent);
    this.bsModalRef.content.event.subscribe(result => {
      if (result == "OK"){
        this.getUsers();
      }
    })
  }
}
