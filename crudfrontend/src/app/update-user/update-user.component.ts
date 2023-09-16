import { Component, EventEmitter } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { Router } from '@angular/router';
import { BsModalRef } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.scss']
})
export class UpdateUserComponent {

  user: User = {} as User;
  userId: number;
  event: EventEmitter<any> = new EventEmitter();

  constructor(private userService: UserService,
    private bsModalRef: BsModalRef) {

    this.userService.userData.subscribe(data => {
      this.userId = data;
      if (this.userId !== undefined){
        this.userService.getUserById(this.userId).subscribe(data => {
          this.user = data
        })
      }
    }, error => {console.log(error)});
  }

  ngOnInit() {

  }

  onSubmit(){
    console.log(this.user);
    this.updateUser();
  }

  updateUser() {
    this.userService.updateUser(this.userId, this.user).subscribe(data => {
      console.log(data);
      this.event.emit('OK');
      this.bsModalRef.hide();
    }, error => {console.log(error)})
  }

  onClose(){
    this.bsModalRef.hide()
  }
}
