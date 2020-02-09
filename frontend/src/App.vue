<template>
  <div id="app">
    <h1>Received WS messages</h1>
    <ul>
      <li v-for="message in messages" v-bind:key="message">{{ message }}</li>
    </ul>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { WebsocketService } from './services/websocket-service';

@Component({})
export default class App extends Vue {
  private webSocketService?: WebsocketService;
  private messages: string[] = [];

  private readonly webSocketGreetingsSubscribeEndpoint = '/user/topic/greetings';
  private readonly webSocketGreetingsSendEndpoint = '/app/hello';

  mounted(): void {
    this.webSocketService = WebsocketService.getInstance();
    this.webSocketService.connect(
      () => {
        this.webSocketService &&
          this.webSocketService.subscribe(
            this.webSocketGreetingsSubscribeEndpoint,
            message => {
              if (message.body) {
                console.log('Received WS message: ', message.body);
                this.messages.push(message.body);
              } else {
                console.warn('Received empty message', message);
              }
            }
          );

        // send initial message to get jobs
        this.sendMessage();
      },
      () => {},
      () => {}
    );
  }

  private sendMessage(): void {
    this.webSocketService &&
      this.webSocketService.sendMessage(
        this.webSocketGreetingsSendEndpoint,
        JSON.stringify({name: 'Any Name'})
      );
  }
}
</script>

<style lang="less">
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
