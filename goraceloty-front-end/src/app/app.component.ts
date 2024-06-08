import {Component, OnDestroy, OnInit} from '@angular/core';
import {HotelService} from "./services/hotel.service";
import {ChangesMessage} from "./entity/ChangesMessage";
import {WebsocketService} from "./services/websocket.service";
import {Subscription} from "rxjs";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnDestroy, OnInit {
  title = 'goraceloty-front-end';
  hotelsString: string = 'none';
  private changesSubscription: Subscription;

  constructor(private websocketService: WebsocketService, private messageService: MessageService) {

  }

  ngOnInit() {
    this.websocketService.tearDownWebsocketEvents();
    this.changesSubscription = this.websocketService.getChangesObservable
      .subscribe((status: ChangesMessage) => this.showChangesDialog(status));
  }

  showChangesDialog(status: ChangesMessage) {
    this.messageService.add({
      severity: 'success', summary: 'Success', detail: `Oferta została zarezerwowana: ${status}`
    })
  }

  ngOnDestroy() {
    this.websocketService.tearDownWebsocketEvents();
  }
}
