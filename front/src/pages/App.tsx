import { Button, Grid, Stack } from "@mui/material";
import Paper from "@mui/material/Paper";
import { styled } from "@mui/material/styles";
import { useState } from "react";

import { AuthForm } from "../components/auth/AuthForm.component";
import { Chart } from "../components/Chart.component";
import { OrderBookPanel } from "../components/OrderBook.component";
import { TradePanel } from "../components/Tradepanel.component";

const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: "#151825",
  ...theme.typography.body2,
  padding: theme.spacing(12),
  textAlign: "center",
  color: theme.palette.text.secondary,
}));

export const App = () => {
  const [price, setPrice] = useState(0);
  const [user, setUser] = useState(null);

  return (
    <Grid
      container
      direction="row"
      gap={2}
      sx={{ height: "100vh", width: "100vw", paddingY: 2, paddingX: 1 }}
    >
      <Grid container sx={{ height: "70vh" }}>
        <Grid item sx={{ width: "100%", height: "100%" }} xs={9}>
          <Chart setPrice={setPrice} />
        </Grid>
        <Grid item sx={{ width: "100%", height: "100%" }} xs={3}>
          <OrderBookPanel />
        </Grid>
      </Grid>
      {user != null ? (
        <Grid container sx={{ height: "20vh" }}>
          <Grid item sx={{ width: "100%", height: "100%" }} xs={9}>
            <TradePanel price={price} />
          </Grid>
          <Grid item sx={{ width: "100%", height: "100%" }} xs={3}>
            PNL
          </Grid>
        </Grid>
      ) : (
        <Grid
          container
          sx={{ height: "20vh", justifyContent: "center", color: "white" }}
        >
          <Stack direction="column">
            <h2>Pour commencer à trader, connectez-vous</h2>
            <AuthForm setUser={setUser}></AuthForm>
          </Stack>
        </Grid>
      )}
    </Grid>
  );
};
