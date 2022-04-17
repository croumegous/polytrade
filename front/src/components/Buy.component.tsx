import { Alert, Slider, Snackbar } from "@mui/material";
import { useState } from "react";
import { useForm } from "react-hook-form";
import useFetch from "use-http";

import { User } from "@/types/user.type";

import { environment } from "../env/environment";
import { CustomInput, InputAdornment } from "./input.component";
import { TradeButton } from "./TradeButton.component";
import { TradeOrder } from "./Tradepanel.component";

export const BuyInterface = ({
  user,
  price,
  setUser,
}: {
  user: User;
  price: number;
  setUser: any;
}) => {
  const { register, watch, setValue, handleSubmit } = useForm({
    mode: "onSubmit",
    reValidateMode: "onChange",
    defaultValues: {
      amountUSD: 0,
      amountBTC: (user.balance?.btc * 0.2) | 0.1,
    },
  });

  const [snackError, setSnackError] = useState(false);
  const [snackSuccess, setSnackSuccess] = useState(false);

  const { response, post, error } = useFetch(environment.api.trade, {
    headers: { authorization: `${user.token}` },
  });
  const onSubmit = async (data: { amountBTC: number }) => {
    const { amountBTC } = data;
    await post("/market", {
      buying: true,
      asset: "btc",
      amount: amountBTC,
    } as TradeOrder);
    if (response.ok) {
      setSnackSuccess(true);
      setUser({
        token: user.token,
        ...response.data,
      });
    }
    if (error) {
      setSnackError(true);
    }
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <CustomInput
        {...register("amountBTC", {
          min: 0.001,
          setValueAs: (v) => parseFloat(v.toString().replace(",", ".")),
        })}
        startAdornment={<InputAdornment>BTC</InputAdornment>}
      />
      <CustomInput
        disabled
        {...register("amountUSD", {
          min: 0,
        })}
        startAdornment={<InputAdornment>USD</InputAdornment>}
      />

      <Slider
        marks
        aria-label="Temperature"
        defaultValue={watch("amountBTC")}
        valueLabelDisplay="auto"
        step={5}
        min={5}
        max={100}
        onChange={(e: unknown, v: number) => {
          setValue(
            "amountBTC",
            parseFloat(((v * user.balance.usd) / 100 / price).toFixed(5))
          );
          setValue("amountUSD", (v * user.balance.usd) / 100);
        }}
      />
      <TradeButton type="buy" label="Buy market" />
      <Snackbar
        key={"bottom-right-success"}
        anchorOrigin={{ vertical: "bottom", horizontal: "right" }}
        open={snackSuccess}
        onClose={() => setSnackSuccess(false)}
      >
        <Alert severity="success" sx={{ width: "100%" }}>
          Your order as been done !
        </Alert>
      </Snackbar>
      <Snackbar
        key={"bottom-right-error"}
        anchorOrigin={{ vertical: "bottom", horizontal: "right" }}
        open={snackError}
        onClose={() => setSnackError(false)}
      >
        <Alert severity="error" sx={{ width: "100%" }}>
          Your order as been done !
        </Alert>
      </Snackbar>
    </form>
  );
};
