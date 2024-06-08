import { InjectableRxStompConfig, RxStompService } from '@stomp/ng2-stompjs';
import { RxStompConfig } from '@stomp/rx-stomp';
import SockJS from 'sockjs-client';

// export const myRxStompConfig: InjectableRxStompConfig = {
//   // WebSocket endpoint
//
//   brokerURL: `ws://${window.location.host}/api/ws`,
//
//   // Heartbeat
//   heartbeatIncoming: 0,
//   heartbeatOutgoing: 20000,
//
//   // Debug
//   debug: (msg: string): void => {
//     console.log(new Date(), msg);
//   }
// };



// export function rxStompServiceFactory() {
//   const rxStomp = new RxStompService();
//   rxStomp.configure(myRxStompConfig);
//   rxStomp.activate();
//   return rxStomp;
// }

export function eventsRxStompConfig(): RxStompConfig {
  return {
    webSocketFactory(): any {
      return new SockJS(`http://${window.location.host}/api/ws`);
    },
    discardWebsocketOnCommFailure: true,
    heartbeatIncoming: 0, // Typical value 0 - disabled
    heartbeatOutgoing: 20000, // Typical value 20000 - every 20 seconds
    reconnectDelay: 200,

    debug: (msg: string): void => {
      console.log(new Date(), msg);
    }
  };
}
