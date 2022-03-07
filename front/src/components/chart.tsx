import useWindowDimensions from "@/helpers/window";
import { ColorType, createChart, Time } from "lightweight-charts";
import { useEffect, useRef, useState } from "react";

interface IChartComponent {}
export const ChartComponent: React.FunctionComponent<IChartComponent> = (
  props
) => {
  const ref = useRef() as React.MutableRefObject<HTMLDivElement>;
  const { height, width } = useWindowDimensions();
  useEffect(() => {
    const chart = createChart(ref.current, {
      width: 0,
      height: height *0.75,
      layout: {
        background: { type: ColorType.Solid, color: "#1c202e" },
        textColor: "rgba(255, 255, 255, 0.9)",
      },
    });
    const candleSeries = chart.addCandlestickSeries();
    var binanceSocket = new WebSocket(
      "wss://stream.binance.com:9443/ws/btcusdt@kline_1m"
    );

    binanceSocket.onmessage = function (event) {
      var message = JSON.parse(event.data);

      var candlestick = message.k;

      console.log(candlestick);

      candleSeries.update({
        time: candlestick.t / 1000 as Time,
        open: candlestick.o,
        high: candlestick.h,
        low: candlestick.l,
        close: candlestick.c,
      });
    };
    return () => {
      chart.remove();
    };
  }, []);

  return (
    <>
      <div ref={ref} />
    </>
  );
};
