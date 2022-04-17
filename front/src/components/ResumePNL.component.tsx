import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";

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
              Value
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
              {user.balance.btc}
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
              {user.balance.usd}
            </TableCell>
          </TableRow>
        </TableBody>
      </Table>
    </TableContainer>
  );
};
