let video = document.getElementById("webCam");
let canvas = document.body.appendChild(document.createElement("canvas"));
let ctx = canvas.getContext("2d");
let model;

// const webCamElement = document.getElementById("webCam");
// const canvasElement = document.getElementById("canvas");


let width = 720;
let height = 1280;

ctx.canvas.width = width;
ctx.canvas.height = height;    

const startStream = () => {
    navigator.mediaDevices.getUserMedia ({
        video : {width: width , height: height},
        audio : false
    }).then((stream) => {
        video.srcObject = stream;
    });
}

const detect = async () => {
    //const model = await blazeface.load();

    const returnTensors = false; // Pass in `true` to get tensors back, rather than values.
    const predictions = await model.estimateFaces(video, returnTensors);

    console.log(predictions);

    ctx.drawImage(video, 0, 0, width, height);

    predictions.forEach(element => {
        ctx.beginPath();
        ctx.lineWidth = 4;
        ctx.strokeStyle = "yellow";
        ctx.rect (
            element.topLeft[0], element.topLeft[1],
            element.bottomRight[0] - element.topLeft[0],
            element.bottomRight[1] - element.topLeft[1]
        );
        ctx.stroke();

        ctx.fillStyle = "yellow";
        element.landmarks.forEach((landmark) => {
            ctx.fillRect(landmark[0], landmark[1], 10,10);
        })
    });
}

startStream();
video.addEventListener('loadeddata', async () => {
    model = await blazeface.load();

    setInterval(detect, 20);
});

const webcam = new Webcam (video, "user", canvas)
webcam.start();

function takeAPicture() {
    let picture = webcam.snap();
    document.querySelector("a").href = picture;

    
        Swal.fire({
            icon: 'success',
            title: 'Picture have been saved!',
            width: 600,
            padding: '3em',
            color: '#716add',
            background: '#fff',
            showConfirmButton: false,
            timer: 1500,
            backdrop: `
            rgba(0,0,123,0.4)
            url("https://sweetalert2.github.io/images/nyan-cat.gif")
            left top
            no-repeat
            `
        })
     
}

function showPicture(){
    (async () => {

        const { value: file } = await Swal.fire({
          title: 'Select image',
          input: 'file',
          inputAttributes: {
            'accept': 'image/*',
            'aria-label': 'Upload your profile picture'
          }
        })
        
        if (file) {
          const reader = new FileReader()
          reader.onload = (e) => {
            Swal.fire({
              title: 'This is your uploaded picture',
              imageUrl: e.target.result,
              imageAlt: 'The uploaded picture'
            })
          }
          reader.readAsDataURL(file)
        }
        
        })()
}