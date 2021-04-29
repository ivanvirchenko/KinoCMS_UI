let logoInput = $('#logo-input');
let logoImage = $('#logo-image');
let logoInputVertical = $('#logo-vertical-input');
let logoImageVertical = $('#logo-vertical-image');

let deleteLogo = $('#delete-logo');
let deleteLogoVertical = $('#delete-vertical-logo');
let bannerInput = $('#banner-input');
let bannerImage = $('#banner-image');
let deleteBanner = $('#delete-banner');


let galleryInput = $('#gallery-input');
let imageHolder = $('#image-holder');

logoInput.change(() => {
    let url = window.URL.createObjectURL(logoInput[0].files[0])
    logoImage.attr('src', url)
});
deleteLogo.click(() => {
    logoInput.val('')
    logoImage.attr('src', 'http://localhost:8080/default/default_image_h.png')
});

logoInputVertical.change(() => {
    let url = window.URL.createObjectURL(logoInputVertical[0].files[0])
    logoImageVertical.attr('src', url)
})
deleteLogoVertical.click(() => {
    logoInputVertical.val('')
    logoImageVertical.attr('src', 'http://localhost:8080/default/default_image.png')
})

bannerInput.change(() => {
    let url = window.URL.createObjectURL(bannerInput[0].files[0])
    bannerImage.attr('src', url)
});
deleteBanner.click(() => {
    bannerInput.val('')
    bannerImage.attr('src', 'http://localhost:8080/default/default_image_h.png')
})

galleryInput.change(() => {
    let filesList = galleryInput[0].files;

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
    if (multipleImageInput[0].files.length === 0) {
        $('#empty-div').text('Список изображений пуст');
        $('#empty-div').addClass('text-xl text-center')
    }
})

$(function () {
    //Date picker
    $('#reservationdate').datetimepicker({
        format: 'DD/MM/YYYY'
    });

    //Date picker
    $('#reservationdate-end').datetimepicker({
        format: 'DD/MM/YYYY'
    });
})
