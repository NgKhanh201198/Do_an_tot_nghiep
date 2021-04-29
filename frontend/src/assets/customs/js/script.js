$(document).ready(function() {
    $('.navbar-nav li').click(function(e) {
        $('.navbar-nav li').removeClass('active');
        $('.navbar-nav li').removeClass('border-bottom');

        $(this).addClass('active')
        $(this).addClass('border-bottom')
    });

    $('.nav-profile ul li a').click(function(e) {
        $('.nav-profile ul li a').removeClass('active-now');

        $(this).addClass('active-now')
    });
});