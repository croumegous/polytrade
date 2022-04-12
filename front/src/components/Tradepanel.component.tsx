import Grid from "@mui/material/Grid";

import { TradeButton } from "./TradeButton.component";

export const TradePanel: React.FunctionComponent<{ price: number }> = ({
  price,
}) => {
  return (
    <Grid container sx={{ height: "100%", width: "100%" }}>
      <Grid
        container
        sx={{ height: "100%", gap: 2, borderRight: "1px solid grey" }}
        xs={4}
        justifyContent="center"
      >
        <Grid item xs={12} sx={{ textAlign: "center", color: "#fafafa" }}>
          Current price: <br />
          <span>{price}</span>
        </Grid>
        <Grid item>
          <TradeButton action={""} type={"sell"} />
        </Grid>
        <Grid item>
          <TradeButton action={""} type={"buy"} />
        </Grid>
        <Grid item></Grid>
      </Grid>
      <Grid item sx={{ height: "100%" }} xs={8}></Grid>
    </Grid>
  );
};
