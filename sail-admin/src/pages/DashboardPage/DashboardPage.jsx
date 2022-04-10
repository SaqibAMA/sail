import { Typography, Grid } from "@mui/material";
import StatsCard from "../../components/StatsCard/StatsCard";

import LocalShippingIcon from "@mui/icons-material/LocalShipping";
import PendingIcon from "@mui/icons-material/Pending";
import CategoryIcon from "@mui/icons-material/Category";

import {
  Navigate,
} from "react-router-dom";

const page_styles = {
  title: {
    fontSize: "2rem",
    fontWeight: "bold",
  },
};

const DashboardPage = ({auth}) => {
  return (
    <section>

      {auth || <Navigate to="/login" />}

      <Typography sx={page_styles.title}>Dashboard</Typography>

      <Grid container spacing={3} mt={0}>
        <Grid item xs={4}>
          <StatsCard
            icon={<LocalShippingIcon />}
            title="Total Orders"
            value="1024"
          />
        </Grid>
        <Grid item xs={4}>
          <StatsCard
            icon={<PendingIcon />}
            title="Unfulfilled Orders"
            value="1024"
          />
        </Grid>
        <Grid item xs={4}>
          <StatsCard icon={<CategoryIcon />} title="Products" value="1024" />
        </Grid>
      </Grid>
    </section>
  );
};

export default DashboardPage;
