import { Grid } from "@mui/material";
import { ChartComponent } from "../components/chart";
import { OrderBookPanel } from "../components/orderbook";
import { styled } from "@mui/material/styles";
import Box from "@mui/material/Box";
import Paper from "@mui/material/Paper";

const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: "#151825",
  ...theme.typography.body2,
  padding: theme.spacing(12),
  textAlign: "center",
  color: theme.palette.text.secondary,
}));

export const App = () => {
  return (
    <Box sx={{ flexGrow: 1 }}>
      <Grid container spacing={2}>
        <Grid xs={9}>
          <ChartComponent></ChartComponent>
        </Grid>
        <Grid xs={3}>
          <OrderBookPanel></OrderBookPanel>
        </Grid>
        <Grid xs={12}>
          <Item>Fake Trading</Item>
        </Grid>
      </Grid>
    </Box>
  );
};
