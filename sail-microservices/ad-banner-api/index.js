// express for api
const express = require('express');
const app = express();

// port
const PORT = process.env.PORT || 3000;

// import sharp for image processing
const sharp = require('sharp');

// add firebase
const { admin, serviceAccount } = require('./firebase.js');
const store = 'firebase-storage';

// access firebase storage
const storage = admin.storage();

// bucket link: gs://sail-app-c1585.appspot.com
const bucket = storage.bucket('gs://sail-app-c1585.appspot.com');

// default route
app.get('/banners', (req, res) => {

    bucket.getFiles()
        .then((results) => {

            // if the file is within "banners" folder, then print it's public link
            const files = results[0].filter(file => file.name.includes('banners'));

            // filter out the files that are not in the folder "banners"
            const filteredFiles = files.filter(file => file.name.includes('banners'));

            // iterate over filteredFiles, generate their signedURL and then send them to the client
            let urls = [];
            filteredFiles.forEach(file => {

                urls.push(

                    file.getSignedUrl({
                        action: 'read',
                        expires: '03-09-2491'
                    })
                );

            });

            Promise.all(urls).then(allURLs => {
                let banners = allURLs.map(url => (
                    {
                        imageUrl: url[0]
                    }
                ))

                res.send(banners);
            });

        });

});


// listen
app.listen(PORT, () => console.log("Server started on port " + PORT));