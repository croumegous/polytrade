import TabContext from "@mui/lab/TabContext";
import TabList from "@mui/lab/TabList";
import TabPanel from "@mui/lab/TabPanel";
import { Tab } from "@mui/material";
import Grid from "@mui/material/Grid";
import { Box } from "@mui/system";
import { useState } from "react";
import NumberFormat from "react-number-format";

import { User } from "@/types/user.type";

import { BuyInterface } from "./Buy.component";
import { SellInterface } from "./Sell.component";

export interface TradeOrder {
  buying: boolean;
  asset: string;
  amount: number;
}

export const TradePanel: React.FunctionComponent<{
  price: number;
  user: User;
  setUser: unknown;
}> = ({ price, user, setUser }) => {
  // tab selector, default -> buy
  const [tab, setTab] = useState("1");
  const handleChange = (event: React.SyntheticEvent, newValue: string) => {
    setTab(newValue);
  };

  return (
    <Grid container sx={{ height: "100%", width: "100%" }}>
      <Grid
        item
        container
        sx={{ height: "100%", gap: 2, borderRight: "1px solid grey" }}
        xs={4}
        justifyContent="center"
      >
        <Grid item xs={12} sx={{ textAlign: "center", color: "#fafafa" }}>
          Current price: <br />
          <span>
            <NumberFormat
              value={price}
              displayType={"text"}
              thousandSeparator={true}
              prefix={"$"}
              decimalScale={2}
            />
          </span>
        </Grid>
        <Grid item>
          <TabContext value={tab}>
            <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
              <TabList
                centered
                variant="fullWidth"
                sx={{
                  ".css-1aquho2-MuiTabs-indicator": {
                    backgroundColor: "white !important",
                  },
                }}
                aria-label="lab API tabs example"
                onChange={handleChange}
              >
                <Tab
                  label="Buy"
                  value="1"
                  sx={{
                    color: "white !important",
                    backgroundColor: "green",
                    borderColor: "white !important",
                  }}
                />
                <Tab
                  label="Sell"
                  value="2"
                  sx={{
                    color: "white !important",
                    backgroundColor: "red",
                    borderColor: "white !important",
                  }}
                />
              </TabList>
            </Box>
            <TabPanel value="1">
              <BuyInterface price={price} setUser={setUser} user={user} />
            </TabPanel>
            <TabPanel value="2">
              <SellInterface price={price} setUser={setUser} user={user} />
            </TabPanel>
          </TabContext>
        </Grid>
      </Grid>
      <Grid item sx={{ height: "100%" }} xs={8}></Grid>
    </Grid>
  );
};
