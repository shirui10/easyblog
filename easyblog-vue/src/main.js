import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Element from 'element-ui'
import axios from 'axios'
import mavonEditor from 'mavon-editor'
import mouse from './assets/mouseClick'
import Antd from 'ant-design-vue';
import CommentEditor from 'comment-message-editor'
import BaiduMap from 'vue-baidu-map'


import 'ant-design-vue/dist/antd.css';
import "element-ui/lib/theme-chalk/index.css"
import 'mavon-editor/dist/css/index.css'

import "./axios"
import './permission.js'

Vue.component(CommentEditor.name, CommentEditor)

Vue.use(Element)
Vue.use(mavonEditor)
Vue.use(mouse)
Vue.use(Antd)
Vue.use(BaiduMap, {
    ak: 'eiX7vRkf63TiNSPQOruDYG5MtG8zXBGq'
})

Vue.config.productionTip = false
Vue.prototype.$axios = axios

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
