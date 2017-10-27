import jQuery from 'jquery';
import Vue from 'vue';
import VeeValidate from 'vee-validate';
import Axios from 'axios';
import notie from 'notie';
// require('/node_modules/bootstrap/dist/js/bootstrap');

global.$ = global.jQuery = jQuery;
import Popper from 'popper.js'
require('bootstrap');

window.Vue = Vue;
window.Vue.use(VeeValidate, { fieldsBagName: 'formFields'} );

window.axios = Axios;

window.axios.defaults.headers.common = {
    'X-Requested-With': 'XMLHttpRequest',
};


window.notie = notie;
window.success = (message) => {
    window.notie.alert({
        text: message,
        type: 'success'
    });
};
window.error = (message) => {
    window.notie.alert({
        text: message,
        type: 'error'
    });
};
window.info = (message) => {
    window.notie.alert({
        text: message,
        type: 'info'
    });
};