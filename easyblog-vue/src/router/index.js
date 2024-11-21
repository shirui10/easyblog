import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Blogs from '../views/Blogs.vue'
import BlogDetail from '../views/BlogDetail.vue'
import BlogEdit from '../views/BlogEdit.vue'
import PicShow from "@/views/PicShow";
import BlogAdd from "@/views/BlogAdd";
import ManagerBlogDetail from "@/views/ManagerBlogDetail";
import ManagerBlogs from "@/views/ManagerBlogs";
import Register from "@/views/Register";
import Suggestions from "@/views/Suggestions";
import PersonalSpace from "@/views/PersonalSpace";
import Search from "@/views/Search";
import Map from "@/views/Map";
import Info from "@/views/Info";

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Index',
        redirect: {name: "Blogs"} //重定向
    },
    {
        path: '/blogs',
        name: 'Blogs',
        component: Blogs
    },
    {
        path: '/blogs-manager',
        name: 'ManagerBlogs',
        component: ManagerBlogs
    },
    {
        path: '/pics',
        name: 'PicShow',
        component: PicShow
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/register',
        name: 'Register',
        component: Register
    },
    {
        path: '/blog/add',
        name: 'BlogAdd',
        component: BlogAdd,
        meta: {
            requireAuth: true
        }
    },
    {
        path: '/blog/:blogId',
        name: 'BlogDetail',
        component: BlogDetail
    },
    {
        path: '/blog-manager/:blogId',
        name: 'ManagerBlogDetail',
        component: ManagerBlogDetail
    },
    {
        path: '/blog/:blogId/edit',
        name: 'BlogEdit',
        component: BlogEdit,
        meta: {
            requireAuth: true
        }
    },
    {
        path: '/space/:userId',
        name: 'PersonalSpace',
        component: PersonalSpace,
        meta: {
            requireAuth: true
        }
    },
    {
        path: '/suggestions',
        name: 'Suggestions',
        component: Suggestions
    },
    {
        path: '/search',
        component: () => import("../views/Search") //懒加载组件
    },
    {
        path: '/map',
        name: 'Map',
        component: Map
    },
    {
        path: '/info',
        name: 'Info',
        component: Info
    },
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
