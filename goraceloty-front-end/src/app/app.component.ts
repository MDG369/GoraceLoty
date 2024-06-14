import {Component, OnDestroy, OnInit} from '@angular/core';
import {ChangesMessage} from "./entity/ChangesMessage";
import {WebsocketService} from "./services/websocket.service";
import {Subscription} from "rxjs";
import {MessageService} from "primeng/api";
import {ChangesService} from "./services/changes.service";
import {AuthService} from "./services/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnDestroy, OnInit {
  title = 'goraceloty-front-end';
  hotelsString: string = 'none';
  private changesSubscription: Subscription;

  constructor(private websocketService: WebsocketService, private messageService: MessageService, private authService: AuthService,
  private changesService: ChangesService) {

  }

  ngOnInit() {
    this.websocketService.tearDownWebsocketEvents();
    this.changesSubscription = this.websocketService.getChangesObservable
      .subscribe((status: ChangesMessage) => this.showChangesDialog(status));
  }

  showChangesDialog(change: ChangesMessage) {
    console.log(`Got a message: ${change}`)
    this.changesService.addChange(change);
  }

  ngOnDestroy() {
    this.websocketService.tearDownWebsocketEvents();
    this.authService.logout();
  }


}
