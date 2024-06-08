import { Injectable } from '@angular/core';
import { RxStompService } from '@stomp/ng2-stompjs';
import { Observable, Subject } from 'rxjs';
import { webSocket } from 'rxjs/webSocket';
import { map } from 'rxjs/operators';
import { filter } from 'rxjs/operators';
import { RxStompState } from '@stomp/rx-stomp';


// @Injectable({
//   providedIn: 'root'
// })
// export class WebSocketService {
//   constructor(private rxStompService: RxStompService) {
//
//     this.rxStompService.connectionState$
//       .pipe(filter(state => state === RxStompState.OPEN))
//       .subscribe(() => {
//         console.log('WebSocket connection opened');
//       });
//
//     this.rxStompService.connectionState$
//       .pipe(filter(state => state === RxStompState.CLOSED))
//       .subscribe(() => {
//         console.log('WebSocket connection closed');
//       });
//   }
//
//   public connect(): void {
//     console.log("Connecting");
//     this.rxStompService.activate();
//   }
//
//   public sendMessage(message: string): void {
//     console.log("Sending");
//     this.rxStompService.publish({ destination: '/app/hello', body: message });
//   }
//
//   public getMessages(): Observable<string> {
//     return this.rxStompService.watch('/topic/greetings').pipe(
//       map((message) => message.body)
//     );
//   }
// }
