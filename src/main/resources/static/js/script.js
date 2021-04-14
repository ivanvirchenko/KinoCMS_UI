let mainImageInput = $('#image-pick-input');
let multipleImageInput = $('#image-pick-input-multiple');
let mainImage = $('#main-image');
let deleteMainImage = $('#delete-main-image');
let deleteMainImageH = $('#delete-main-image-h');
let imageHolder = $('#image-holder');

mainImageInput.change(() => {
    let url = window.URL.createObjectURL(mainImageInput[0].files[0])
    mainImage.attr('src', url)
});

deleteMainImage.click(() => {
    mainImageInput.val("")
    mainImage.attr('src', 'http://localhost:8080/default/default_image.png')
});

deleteMainImageH.click(() => {
    mainImageInput.val("")
    mainImage.attr('src', 'http://localhost:8080/default/default_image_h.png')
});


multipleImageInput.change(() => {
    let filesList = multipleImageInput[0].files;

    for (let i = 0; i < filesList.length; i++) {
        let img = document.createElement("img");
        let div = document.createElement("div");
        img.src = window.URL.createObjectURL(filesList[i]);
        img.classList.add("w-100")
        img.onload = function () {
            window.URL.revokeObjectURL(this.src);
        }
        div.append(img);

        imageHolder.prepend(div);
    }
})

$('#submit_btn').submit(() => {
    if (mainImageInput[0].files === undefined) {
        alert("Картинка нада");
    }
})
