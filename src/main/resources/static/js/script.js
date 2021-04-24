let mainImageInput = $('#image-pick-input');
let logoImageInput = $('#logo-pick-input');
let multipleImageInput = $('#image-pick-input-multiple');
let logoImage = $('#logo-image');
let mainImage = $('#main-image');
let deleteMainImage = $('#delete-main-image');
let deleteMainImageH = $('#delete-main-image-h');
let imageHolder = $('#image-holder');
let deleteLogoBtn = $('#delete-logo-btn');

logoImageInput.change(() => {
    let url = window.URL.createObjectURL(logoImageInput[0].files[0])
    logoImage.attr('src', url)
});

mainImageInput.change(() => {
    let url = window.URL.createObjectURL(mainImageInput[0].files[0])
    mainImage.attr('src', url)
});

deleteLogoBtn.click(() => {
    logoImageInput.val('')
    logoImage.attr('src', 'http://localhost:8080/default/default_image_h.png')
});

deleteMainImageH.click(() => {
    mainImageInput.val('')
    mainImage.attr('src', 'http://localhost:8080/default/default_image_h.png')
});

multipleImageInput.change(() => {
    $('#empty-div').remove();
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

$().ready(() => {
    if (multipleImageInput[0].files.length === 0){
        $('#empty-div').text('Список изображений пуст');
        $('#empty-div').addClass('text-xl text-center')
    }
})
