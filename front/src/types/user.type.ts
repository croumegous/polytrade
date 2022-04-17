export type User = {
  balance?: {
    btc: number;
    usd: number;
  };
  email: string;
  token: string;
};
