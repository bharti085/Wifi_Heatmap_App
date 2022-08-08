const express = require('express');
const fileUpload = require('express-fileupload');
const bodyParser = require('body-parser');
const morgan = require('morgan');
const { spawn } = require('child_process');
const fs = require('fs');
const sizeOf = require('image-size');
const sharp = require('sharp');
 

const app = express();


// enable files upload
app.use(fileUpload({
    createParentPath: true,
    limits: { 
        fileSize: 2 * 1024 * 1024 * 1024 //2MB max file(s) size
    },
}));


//add other middleware
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));
app.use(morgan('dev'));

//make uploads directory static
app.use(express.static('uploads'));
//make images directory static
app.use(express.static('images'));


// Function to let the server wait before executing ahead
function delay(time) {
    return new Promise(resolve => setTimeout(resolve, time));
  } 


// To send input through form data an html page will be displayed
app.get('/', (req, res) => {
    res.sendFile(__dirname+"/index.html");
  })




// API to handle the input sent by user and send the heatmap generated
app.post('/generate-heatmap', async(req, res) => {
    try {
        if(!req.files.base64image && !req.files.txtFile && !req.body.Height && !req.body.Width){
            console.log('No i/p');
           res.status(404).send( 'No input found');
        }
            else if(!req.files.txtFile) {
            console.log('No txt file');
            res.status(400).end( 'No txt file uploaded');
        }
        else if(!req.files.base64image){
            console.log('No image');
            res.status(400).end( 'No image uploaded!');
        }
        else if(!req.body.Height || !req.body.Width)
        {
            console.log("Dimensions invalid");
            res.status(400).end("Correct dimensions not provided");
        }
         else {
            //Using the name of the input field (i.e. "txtFile") to retrieve the uploaded files
            let txtFile = req.files.txtFile;

            //Using the name of the input field (i.e. "base64image") to retrieve the uploaded files
            let img = req.files.base64image;

            // To store the image sent in images directory
            try{
                 await img.mv('./images/' + img.name);
            }catch(err){
                console.log(err);
            }
            

            
            let path="";
            temp = req.files.txtFile.data.toString('utf-8');

            // Storing the received textfile in uploads directory
            try {
                await fs.writeFileSync(__dirname+'/uploads/'+txtFile.name, temp);
                path = __dirname+"/uploads/"+txtFile.name;
              } catch (err) {
                console.log(err);
              }
            
            let x = parseInt(req.body.Width);
            let y = parseInt(req.body.Height);

            var dimensions = sizeOf('./images/'+img.name);

            // Resizing the image received to correct dimensions and store it in ResizedImages directory
            sharp('./images/'+img.name).resize({ height: y, width: x , fit : 'fill' }).toFile('./ResizedImages/'+img.name)
            .then(function(newFileInfo) {
            console.log("Success");
            })
            .catch(function(err) {
            console.log("Error occured");
            console.log(err);
            });


            // spawning the conv.py script to convert textfile into json file 
            const childPython = spawn('python', ['./conv.py',path]);
            childPython.stdout.on('data', (data)=>{
                console.log('stdout ::'+data);
            });
            
            childPython.stderr.on('err', (data)=>{
                console.log('stderr Chpython : '+data);
            });
            childPython.stdout.on('close', (code)=>{
                console.log(`ChildPython process exited with code : ${code}`);
            });

            await delay(3000);
            
            
            let TEST_CONFIG_JSON = "config.json";


            // spawning the main.py script to generate the heatmap from config.json file
            const childPythen = spawn('python', ['./main.py',img.name,TEST_CONFIG_JSON]);
            
            childPythen.stdout.on('data', (data)=>{
                console.log('stdout :: '+data);
            });
            childPythen.stderr.on('error', (data)=>{
                console.log('stderr chPythen:: '+data);
            });
            childPythen.stdout.on('close', (code)=>{
                console.log('ChildPythen process exited with code : '+code);
            });
            
            await delay(5000);

            // storing the generated heatmap in Heatmaps directory
            fs.readFile(__dirname+"/signal_strength.png", function (err, data) {
                if (err) throw err;
                fs.writeFile(__dirname+'/Heatmaps/image'+Date.now()+'.jpeg', data, function (err) {
                    if (err) throw err;
                    console.log('It\'s saved!');
                });
            });
            
            // Sending the generated heatmap back as response
            res.sendFile(__dirname+"/signal_strength.png")
        }
    } catch (err) {
        res.status(500).send(err);
    }
});


//start app 
const port = process.env.PORT || 3000;
app.listen(port, () => 
  console.log(`App is listening on port ${port}.`)
);