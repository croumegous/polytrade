import {
  Button,
  Checkbox,
  FormControlLabel,
  Grid,
  TextField,
} from "@mui/material";
import { styled } from "@mui/material/styles";
import { useForm } from "react-hook-form";
import { useFetch } from "use-http";

import { environment } from "../../env/environment";

const CssTextField = styled(TextField)({
  "& .MuiOutlinedInput-root": {
    "& fieldset": {
      borderColor: "#fafafa",
    },
    margin: "5px 0",
    color: "white",
    "&:hover fieldset": {
      borderColor: "#fff",
    },
  },
  "& .MuiFormHelperText-root": {
    color: "red",
  },
});

export const AuthForm: React.FunctionComponent<{ setUser: any }> = ({
  setUser,
}) => {
  const {
    register,
    watch,
    handleSubmit,
    formState: { errors },
  } = useForm({ shouldFocusError: true, reValidateMode: "onChange" });

  const { response, post, loading, error } = useFetch(environment.api.auth);
  const onSubmit = async (data: any) => {
    await post(data.createAccount ? "/register" : "/login", data);
    if (response.ok) {
      response.json().then((data: any) => {
        setUser(data);
      });
    }
  };

  return (
    <Grid container columns={1}>
      <form onSubmit={handleSubmit(onSubmit)}>
        <Grid item xs={12}>
          {error && (
            <div className="alert alert-danger" role="alert">
              {error.message}
            </div>
          )}
        </Grid>
        <Grid item xs={12}>
          <CssTextField
            label="Votre email"
            color={errors.email ? "error" : "primary"}
            helperText={errors.email ? errors.email.message : ""}
            size="small"
            {...register("email", {
              required: "L'email est obligatoire",
              pattern: {
                value: /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/,
                message: "L'email n'est pas valide",
              },
            })}
          />
        </Grid>
        <Grid item xs={12}>
          <CssTextField
            type="password"
            label="Votre mot de passe"
            helperText={errors.password ? errors.password.message : ""}
            color={errors.password ? "error" : "primary"}
            size="small"
            {...register("password", {
              required: "Vous devez entrer votre mot de passe",
              minLength: {
                value: 6,
                message:
                  "Votre mot de passe doit contenir au moins 6 caractères",
              },
              maxLength: {
                value: 40,
                message:
                  "Votre mot de passe ne doit pas dépasser 40 caractères",
              },
            })}
          />
        </Grid>
        {watch("createAccount") && (
          <Grid item xs={12}>
            <CssTextField
              type="password"
              label="Confirmez le mot de passe"
              helperText={
                errors.confirmPassword ? errors.confirmPassword.message : ""
              }
              color={errors.confirmPassword ? "error" : "primary"}
              size="small"
              {...register("confirmPassword", {
                required: "Vous devez confirmer votre mot de passe",
                validate: (val: string) => {
                  if (watch("password") != val) {
                    return "Les mot de passe doivent être identiques";
                  }
                },
              })}
            />
          </Grid>
        )}{" "}
        <Grid item xs={12}>
          <FormControlLabel
            control={
              <Checkbox
                sx={{
                  color: "white",
                }}
                {...register("createAccount")}
              />
            }
            label="Je souhaite créer un compte"
          />{" "}
        </Grid>
        <Grid item xs={12}>
          <Button type="submit" variant="outlined" sx={{ margin: "auto" }}>
            {loading
              ? "En cours.."
              : watch("createAccount")
              ? "S'enregistrer"
              : "Se connecter"}
          </Button>
        </Grid>
      </form>
    </Grid>
  );
};
