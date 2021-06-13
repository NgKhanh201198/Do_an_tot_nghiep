export enum Path {

    //Pages
    HOME = 'home',
    ADMIN_PAGE = 'admin-page',
    LOGIN = 'log-in',
    REGISTER = 'register',
    PROFILE = 'user-profile',
    CHANGEPASSWORD = 'user-change-password',
    RESETPASSWORD = 'user-reset-password',
    FORGOTPASSWORD = 'forgot-password',
    VERIFYEMAIL = 'verify-email',
    BOOKINGROOM = 'user-bookingroom',
    HOTEL = 'hotel',
    ROOM = 'room',
    POST = 'post',


    //  admin page
    CREATE_ACCOUNT = 'create-account',
    LIST_ACCOUNT = 'list-account',
    UPDATED_ACCOUNT = 'updated-account/:id',
    DELETED_ACCOUNT = 'deleted-account',

    CREATE_CUSTOMER = 'create-customer',
    LIST_CUSTOMER = 'list-customer',
    UPDATED_CUSTOMER = 'updated-customer/:id',
    DELETED_CUSTOMER = 'deleted-customer',

    CREATE_CITY = 'create-city',
    LIST_CITY = 'list-city',
    UPDATED_CITY = 'updated-city/:id',
    DELETED_CITY = 'deleted-city',

    CREATE_HOTEL = 'create-hotel',
    LIST_HOTEL = 'list-hotel',
    UPDATED_HOTEL = 'updated-hotel/:id',
    DELETED_HOTEL = 'deleted-hotel',

    CREATE_ROOM = 'create-room',
    LIST_ROOM = 'list-room',
    UPDATED_ROOM = 'updated-room/:id',
    DELETED_ROOM = 'deleted-room',

    CREATE_ROOMTYPE = 'create-roomtype',
    LIST_ROOMTYPE = 'list-roomtype',
    UPDATED_ROOMTYPE = 'updated-roomtype/:id',
    DELETED_ROOMTYPE = 'deleted-roomtype',

    CREATE_BOOKINGROOM = 'create-bookingroom',
    LIST_BOOKINGROOM = 'list-bookingroom',
    DETAILS_BOOKINGROOM = 'details-bookingroom/:id',
    UPDATED_BOOKINGROOM = 'updated-bookingroom/:id',
    DELETED_BOOKINGROOM = 'deleted-bookingroom'
}
