const express = require('express');
const app = express();
const PORT = process.env.PORT || 3000;

// api middleware
app.use('/api', require('./api/'));

// send all home traffic to api
app.get('/', (req, res)=> res.redirect('/api'));

// start server
app.listen(PORT, ()=> {
    console.log('Server is running on port 3000');
});