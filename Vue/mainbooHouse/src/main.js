

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap';

import temp from './components/modals/temp.vue'


//npm i --save @fortawesome/fontawesome-svg-core
//npm i --save @fortawesome/vue-fontawesome
//npm i --save @fortawesome/free-solid-svg-icons
//npm i --save @fortawesome/free-regular-svg-icons
//npm i --save @fortawesome/free-brands-svg-icons
import {library} from '@fortawesome/fontawesome-svg-core'
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome'
import {fas} from '@fortawesome/free-solid-svg-icons'
import {far} from '@fortawesome/free-regular-svg-icons'
import {fab} from '@fortawesome/free-brands-svg-icons'
library.add(fas, far, fab);
// Vue.component('font-awesome-icon', FontAwesomeIcon);

const app = createApp(App)
.component('font-awesome-icon', FontAwesomeIcon)

app.component('temp', temp)

app.use(createPinia())
app.use(router)

app.mount('#app')
