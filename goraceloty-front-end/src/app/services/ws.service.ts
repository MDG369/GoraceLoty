import { RxStompService } from "@stomp/ng2-stompjs";
import { RxStompConfig, RxStompState } from "@stomp/rx-stomp";
import { eventsRxStompConfig } from "./rx-stomp-factory.service";
import { Subject } from "rxjs";
import { Injectable } from "@angular/core";
@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private taskProgress: Subject<string> = new Subject<string>();

  get getTaskProgressObservable() {
    return this.taskProgress.asObservable();
  }

  constructor(private rxStompService: RxStompService,
              ) {
    this.rxStompService.watch('/topic/changes')
      .subscribe((message: any): void => {
        console.log('Received message:');
        if (!message) {
          return;
        }
        const progress: string = JSON.parse(message.body) as string;
        this.taskProgress.next(progress);
      });
    this.rxStompService.connectionState$.subscribe(next => {
      console.log('Connection State', RxStompState[next]);
      // if(next === RxStompState.CLOSED) {
      //   this.setupWebsocketEvents()     }
    });
  }

  public setupWebsocketEvents(): void {
    const stompConfig: RxStompConfig = eventsRxStompConfig();
    // @ts-ignore
    this.rxStompService.configure(stompConfig);
    this.rxStompService.activate();
  }

  public tearDownWebsocketEvents(): void {
    this.rxStompService.deactivate()
      .then(
        () => {console.log('Event service disconnected'); this.setupWebsocketEvents()},
        (reason) => console.log(`Something bad happened during disconnect: ${reason}`));
  }
}
