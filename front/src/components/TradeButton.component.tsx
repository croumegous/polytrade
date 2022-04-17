import { Button } from "@mui/material";

interface TradeButtonProps {
  type: "sell" | "buy";
  label: string;
}

export const TradeButton: React.FunctionComponent<TradeButtonProps> = (
  props: TradeButtonProps
) => {
  return (
    <Button
      variant="contained"
      color={props.type === "sell" ? "error" : "success"}
      sx={{ padding: "0.7rem 2rem" }}
      type="submit"
    >
      {props.label}
    </Button>
  );
};
