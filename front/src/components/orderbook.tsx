
import { OrderBook } from "@lab49/react-order-book";
import { useState } from "react";

interface Book {
    bids: [string, string][];
    asks: [string, string][];
}

export const OrderBookPanel = () => {
  const [book, setBook] = useState<Book | null>(null);
  var binanceSocket = new WebSocket(
    "wss://stream.binance.com:9443/ws/btcusdt@depth20"
  );

  binanceSocket.onmessage = function (event) {
    var message = JSON.parse(event.data);

    // var candlestick = message;

    console.log(message);

    setBook({ asks: message.asks, bids: message.bids });
  };

  return (
    <>
      <style
        // eslint-disable-next-line react/no-danger
        dangerouslySetInnerHTML={{
          __html: `
              .MakeItNiceAgain {
                color: rgba(255, 255, 255, 0.6);
                display: inline-block;
                font-family: -apple-system, BlinkMacSystemFont, sans-serif;
                font-size: 13px;
                font-variant-numeric: tabular-nums;
                padding: 50px 0;
              }
              .MakeItNiceAgain__side-header {
                font-weight: 700;
                margin: 0 0 5px 0;
                text-align: right;
              }
              .MakeItNiceAgain__list {
                list-style-type: none;
                margin: 0;
                padding: 0;
              }
              .MakeItNiceAgain__list-item {
                border-top: 1px solid rgba(255, 255, 255, 0.1);
                cursor: pointer;
                display: flex;
                justify-content: flex-end;
              }
              .MakeItNiceAgain__list-item:before {
                content: '';
                flex: 1 1;
                padding: 3px 5px;
              }
              .MakeItNiceAgain__side--bids .MakeItNiceAgain__list-item {
                flex-direction: row-reverse;
              }
              .MakeItNiceAgain__side--bids .MakeItNiceAgain__list-item:last-child {
                border-bottom: 1px solid rgba(255, 255, 255, 0.1);
              }
              .MakeItNiceAgain__side--bids .MakeItNiceAgain__size {
                text-align: right;
              }
              .MakeItNiceAgain__list-item:hover {
                background: #262935;
              }
              .MakeItNiceAgain__price {
                border-left: 1px solid rgba(255, 255, 255, 0.1);
                border-right: 1px solid rgba(255, 255, 255, 0.1);
                color: #ebebeb;
                display: inline-block;
                flex: 0 0 50px;
                margin: 0;
                padding: 3px 5px;
                text-align: center;
              }
              .MakeItNiceAgain__size {
                flex: 1 1;
                margin: 0;
                padding: 3px 5px;
                position: relative;
              }
              .MakeItNiceAgain__size:before {
                background-color: var(--row-color);
                content: '';
                height: 100%;
                left: 0;
                opacity: 0.08;
                position: absolute;
                top: 0;
                width: 100%;
              }              
            `,
        }}
      />

      {book !== null && (
        <OrderBook
          book={{ bids: book.bids, asks: book.asks }}
          // applyBackgroundColor
          // fullOpacity
          interpolateColor={(color: any) => color}
          listLength={11}
          stylePrefix="MakeItNiceAgain"
          showSpread={false}
        />
      )}
    </>
  );
};
