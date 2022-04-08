import { Typography, Card, CardContent } from "@mui/material";

const card_styles = {
    card: {
        minHeight: 200,
    }
}

const StatsCard = ({icon, title, value})=> {
    return (
        <Card sx={card_styles.card} variant="outlined">
            <CardContent>
                {icon}
                <Typography color="#666" mt={3}>
                    {title}
                </Typography>
                <Typography variant="h2" mt={1} component="div">
                    {value}
                </Typography>
            </CardContent>
        </Card>
    )
}

export default StatsCard;