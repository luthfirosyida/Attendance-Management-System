let video = document.getElementById("webCam");
let canvas = document.body.appendChild(document.createElement("canvas"));
let ctx = canvas.getContext("2d");
let model;

// const webCamElement = document.getElementById("webCam");
// const canvasElement = document.getElementById("canvas");


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
              title: 'This is your saved picture',
              imageUrl: e.target.result,
              imageAlt: 'The uploaded picture'
            })
          }
          reader.readAsDataURL(file)
        }
        
        })()
}