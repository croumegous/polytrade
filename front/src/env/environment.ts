export const environment = {
  api: {
    price: import.meta.env.VITE_API_PRICE as string || "wss://stream.binance.com:9443/ws/btcusdt@kline_1m",
    orderBook: import.meta.env.VITE_API_ORDERBOOK as string || "wss://stream.binance.com:9443/ws/btcusdt@depth20",
    auth: import.meta.env.VITE_API_AUTH as string || "http://localhost:8080/user",   
    trade: import.meta.env.VITE_API_TRADE as string || "http://localhost:8080/trade",
  },
  debugMode: false,
};
