# Binance stream

[link](https://docs.google.com/document/d/1anV0G7cVZiEljeEf_qi-5mF_ErKitjxJkAWrq0JjRXY/edit#heading=h.lx0rfpxnskj5)

## Overview

This project aims to use binance websocket streams to create a financial tool that would show financial metrics, aggregated information about current offers on the market and a trader simuator.

## Goals

Show different financial indicators (MACD)
Recreate and show the order book
Simulate fake trades based on the order book

## Specifications

You will create a web application that will provide the features described below. The front-end will only show information and should not compute any complex aggregation, most of the business logic of your application will be implemented in a back-end service. Your back-end and your front-end will both needs to be reactive.

Back-end framework: Akka
Front-end framework: You can choose your framework (Vue or React recommended)

You will use the binance order websocket to get the information: https://docs.binance.org/api-reference/dex-api/ws-streams.html#1-orders
Show different financial indicators (MACD)

Financial applications provide graphs with metrics about the maket across time.
Here is an example on Yahoo finance for the ZEN symbol.
You will have to implement a smiliar view which will at least be able to show a smoothed line of the current price across time, a candle bar view and an MACD line (the purple line on the screenshot below).
Recreate and show the order book

An order book contains a list of all pending orders on the market. Let say there is 2 sellers that sells 1 BTC at $50.000 and 1 seller that sells 1 BTC at $49.000, there will be only two informations in the order book: 2 BTC available at $50.000 and one BTC at $49.000. It allows you how much a cryptocurrency is available at a given price.

You will need to provide a reactive view of the order book.
Simulate fake trades based on the order book

Based on all the information you'll have stored, you'll create an API and a front-end tool that allows users to simulate trades. This trade should be accepted as soon as an offer in the order book matches the quantity and the target price. The order book needs to be updated accordingly. You will be able to track the total amount of money gained during your fake trade session.
