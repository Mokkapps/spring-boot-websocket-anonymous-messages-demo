import { Client, messageCallbackType } from '@stomp/stompjs';

interface IWebSocketService {
  connect(
    onConnectCb: Function,
    onDisconnectCb: Function,
    onErrorCb: Function
  ): void;
  disconnect(): void;
  subscribe(destination: string, cb: messageCallbackType): void;
  sendMessage(destination: string, body: string): void;
}

export class WebsocketService implements IWebSocketService {
  private readonly webSocketUrl =
    process.env.NODE_ENV === 'development'
      ? 'ws://localhost:8080/ws'
      : `wss://${window.location.hostname}/ws`;
  private client: Client;
  private onConnectCb?: Function;
  private onDisconnectCb?: Function;
  private onErrorCb?: Function;
  private _isConnected = false;

  private static instance: WebsocketService;

  private constructor() {
    console.log(`${process.env.NODE_ENV === 'development' ? 'DEV' : 'PROD'} mode`);
    this.client = new Client({
      brokerURL: this.webSocketUrl,
      debug: function(str: string) {
        console.log('WS debug: ', str);
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000
    });

    this.client.onConnect = () => {
      this._isConnected = true;
      this.onConnectCb && this.onConnectCb();
    };

    this.client.onDisconnect = () => {
      this._isConnected = false;
      this.onDisconnectCb && this.onDisconnectCb();
    };

    this.client.onStompError = (frame: any) => {
      console.error('WS: Broker reported error: ' + frame.headers['message']);
      console.error('WS: Additional details: ' + frame.body);
      this.onErrorCb && this.onErrorCb();
    };
  }

  static getInstance(): WebsocketService {
    if (!WebsocketService.instance) {
      return new WebsocketService();
    }
    return WebsocketService.instance;
  }

  get isConnected(): boolean {
    return this._isConnected;
  }

  connect(
    onConnectCb: Function,
    onDisconnectCb: Function,
    onErrorCb: Function
  ): void {
    this.onConnectCb = onConnectCb;
    this.onDisconnectCb = onDisconnectCb;
    this.onErrorCb = onErrorCb;

    this.client.activate();
  }

  disconnect(): void {
    this.client.deactivate();
  }

  subscribe(destination: string, cb: messageCallbackType): void {
    this.client.subscribe(destination, cb);
  }

  sendMessage(destination: string, body: string): void {
    this.client.publish({ destination, body });
  }
}
