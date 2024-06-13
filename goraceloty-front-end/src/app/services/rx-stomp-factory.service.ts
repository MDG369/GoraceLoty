import { RxStompConfig } from '@stomp/rx-stomp';
import SockJS from 'sockjs-client';

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
