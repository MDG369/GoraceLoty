import { RxStompService } from "@stomp/ng2-stompjs";
import { RxStompConfig, RxStompState } from "@stomp/rx-stomp";
import { eventsRxStompConfig } from "./rx-stomp-factory.service";
import { Subject } from "rxjs";
import { Injectable } from "@angular/core";
import {ChangesMessage} from "../entity/ChangesMessage";
import {AuthService} from "./auth.service";
@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private changesSubject: Subject<ChangesMessage> = new Subject<ChangesMessage>();

  get getChangesObservable() {
    return this.changesSubject.asObservable();
  }

  constructor(private rxStompService: RxStompService, private authService: AuthService
              ) {
    this.rxStompService.watch('/topic/changes')
      .subscribe((message: any): void => {
        console.log('Received message:');
        if (!message) {
          return;
        }
        const changesMessage: ChangesMessage = JSON.parse(message.body) as ChangesMessage;
        this.changesSubject.next(changesMessage);
      });
    this.rxStompService.connectionState$.subscribe(next => {
      console.log('Connection State', RxStompState[next]);
    });
  }

  public setupWebsocketEvents(): void {
    if (this.authService.isLoggedIn()) {
      const stompConfig: RxStompConfig = eventsRxStompConfig();
      // @ts-ignore
      this.rxStompService.configure(stompConfig);
      this.rxStompService.activate();
    }
  }

  public tearDownWebsocketEvents(): void {
    this.rxStompService.deactivate()
      .then(
        () => {console.log('Event service disconnected');
          this.setupWebsocketEvents()
        },
        (reason) => console.log(`Something bad happened during disconnect: ${reason}`));
  }
}
