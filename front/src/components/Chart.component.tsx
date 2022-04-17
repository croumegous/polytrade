import { Box } from "@mui/system";
import { ColorType, createChart, Time } from "lightweight-charts";
import { useEffect, useRef } from "react";

import { environment } from "../env/environment";

export const Chart: React.FunctionComponent<{ setPrice: any }> = ({
  setPrice,
}) => {
  const ref = useRef() as React.MutableRefObject<HTMLDivElement>;

  useEffect(() => {
    const chart = createChart(ref.current, {
      width: 0,
      height: 0,
      timeScale: {
        rightOffset: 15,
        lockVisibleTimeRangeOnResize: true,
        rightBarStaysOnScroll: true,
        timeVisible: true,
        secondsVisible: true,
        shiftVisibleRangeOnNewBar: false,
      },
      layout: {
        background: { type: ColorType.Solid, color: "#1c202e" },
        textColor: "rgba(255, 255, 255, 0.7)",
      },
    });
    const candleSeries = chart.addCandlestickSeries();

    // Websocket flux to listen
    const binanceSocket = new WebSocket(environment.api.price);

    // Add each flux message to the chart
    binanceSocket.onmessage = function (event) {
      const message = JSON.parse(event.data);
      const candlestick = message.k;
      setPrice(candlestick.c);

      candleSeries.update({
        time: (candlestick.t / 1000) as Time,
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

  return <Box ref={ref} sx={{ width: "100%", height: "100%" }}></Box>;
};
