import { Button } from "@mui/material";

interface TradeButtonProps {
  type: "sell" | "buy";
  action: string;
}

export const TradeButton: React.FunctionComponent<TradeButtonProps> = (
  props: TradeButtonProps
) => {
  return (
    <Button
      variant="contained"
      color={props.type === "sell" ? "error" : "success"}
      sx={{ padding: "0.7rem 2rem" }}
    >
      {props.type === "sell" ? "Sell" : "Buy"}
    </Button>
  );
};
