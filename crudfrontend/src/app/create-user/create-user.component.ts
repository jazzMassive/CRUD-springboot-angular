import { Component, EventEmitter } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { Router } from '@angular/router';
import { BsModalRef } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.scss']
})
export class CreateUserComponent {


  user: User = new User();
  event: EventEmitter<any> = new EventEmitter();

  constructor(private userService: UserService,
              private bsModalRef: BsModalRef) {}

  ngOnInit(): void {
  }

  onSubmit(){
    console.log(this.user);
    this.saveUser()
  }

  saveUser() {
    this.userService.createUser(this.user).subscribe(data =>{
      console.log(data);
      this.event.emit('OK');
      this.bsModalRef.hide();
    });
  }

  onClose(){
    this.bsModalRef.hide()
  }
}
