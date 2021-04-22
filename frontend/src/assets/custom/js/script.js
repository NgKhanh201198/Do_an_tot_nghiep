$(document).ready(function() {
    $('.navbar-nav li').click(function(e) {
        $('.navbar li').removeClass('active');
        $('.navbar li').removeClass('border-bottom');

        $(this).addClass('active')
        $(this).addClass('border-bottom')
    });

    $('.owl-carousel').owlCarousel({
        loop: true,
        margin: 10,
        nav: false,
        dots: true,
        responsive: {
            0: {
                items: 3
            },
            600: {
                items: 4
            },
            1000: {
                items: 5
            }
        }
    })
});