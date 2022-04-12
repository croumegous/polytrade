# Frontend stack

## Setup

To install the frontend stack, run the following commands:  
`npm i`

To run the application in development mode, run the following command:  
`npm run dev`

To run the application in production mode, run the following command:  
`npm run build`

The actual stack is using:

- [React](https://reactjs.org/)
- [RxJS](https://rxjs.dev/)
- [ViteJS](https://vite.dev/)
- [TypeScript](https://www.typescriptlang.org/)
- [MUI](https://mui.com/)

## Websocket

The default websocket server is `ws://localhost:8080`.

For testing purposes, use a simple echo server : https://hub.docker.com/r/jmalloc/echo-server

To test this, run: `docker run -d -p 8080:8080 jmalloc/echo-server`

## Trading chart

We use tradingview charting library to display charts.
Docs here: https://www.tradingview.com/docs/chart-concepts/
