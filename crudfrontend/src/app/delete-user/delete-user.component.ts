import { Component, EventEmitter } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-delete-user',
  templateUrl: './delete-user.component.html',
  styleUrls: ['./delete-user.component.scss']
})
export class DeleteUserComponent {

  id: number;

  constructor(private userService: UserService,
              private route: ActivatedRoute,
              private router: Router) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
  }

  confirmDelete(){
    this.userService.deleteUser(this.id).subscribe(data =>{
      console.log(data);
      this.goToUsersList();
    })
  }

  goToUsersList() {
    this.router.navigate(['/users']);
  }

  onCancel(){
    this.goToUsersList();
  }
  
}
