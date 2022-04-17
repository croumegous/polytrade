import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import NumberFormat from "react-number-format";

import { User } from "../types/user.type";

export const ResumePNL = ({ user }: { user: User }) => {
  return (
    <TableContainer component={"div"} sx={{ width: "90%", margin: "auto" }}>
      <Table sx={{ color: "#fff !important" }}>
        <TableHead>
          <TableRow>
            <TableCell sx={{ color: "#fff !important", fontWeight: "bold" }}>
              Assets
            </TableCell>
            <TableCell
              sx={{ color: "#fff !important", fontWeight: "bold" }}
              align="right"
            >
              Amount
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody
          sx={{
            "&:last-child td, &:last-child th": { border: 0 },
          }}
        >
          <TableRow key={"btc"}>
            <TableCell
              sx={{ color: "#fff !important" }}
              component="th"
              scope="row"
            >
              Bitcoin
            </TableCell>

            <TableCell sx={{ color: "#fff !important" }} align="right">
              <NumberFormat
                value={user.balance.btc}
                displayType={"text"}
                thousandSeparator={true}
                decimalScale={8}
              />
            </TableCell>
          </TableRow>
          <TableRow key={"usd"}>
            <TableCell
              sx={{ color: "#fff !important" }}
              component="th"
              scope="row"
            >
              Dollars
            </TableCell>

            <TableCell sx={{ color: "#fff !important" }} align="right">
              <NumberFormat
                value={user.balance.usd}
                displayType={"text"}
                thousandSeparator={true}
                prefix={"$"}
                decimalScale={2}
              />
            </TableCell>
          </TableRow>
        </TableBody>
      </Table>
    </TableContainer>
  );
};
