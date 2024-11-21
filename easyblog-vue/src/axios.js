import axios from 'axios'
import Element from 'element-ui'
import router from './router'
import store from './store'

//  此为后端服务器端口地址，本地和线上
//axios.defaults.baseURL = "http://localhost:8099"
axios.defaults.baseURL = "http://8.129.165.36:8099"

//  前置拦截
axios.interceptors.request.use(config => {
    return config;
})


axios.interceptors.response.use(response => {
        let res = response.data;

        //console.log("===================");
        //console.log(res);
        //console.log("===================");

        if (res.code === 200) {
            return response;
        } else {

            //  弹出后端返回数据中的错误信息，即例如"账号或密码不正常"等
            Element.Message.error(response.data.msg, {duration: 1000});

            //告诉请求此处已结束，避免错误后仍进入到正常流程获取到token
            return Promise.reject(response.data.msg);

        }

    },
    error => {
        console.log(error);

        //  如果后端返回的数据里有信息，则把错误信息赋值给message
        if (error.response.data.msg) {
            error.message = error.response.data.msg;
        }

        //401异常即shiro异常时，删除全局参数即token和userInfo，然后返回登陆页面
        if (error.response.status === 401) {
            store.commit("REMOVE_INFO");
            router.push("/login");
        }

        Element.Message.error(error.message, {duration: 1000});
        return Promise.reject(error);

    }
)