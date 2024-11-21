<template>
  <div class="mBlog">

    <TopBar></TopBar>

    <el-page-header class="goBack" @back="goBack" content="公告详情">
    </el-page-header>
    <el-tooltip placement="top" content="回到顶部">
      <back-to-top :custom-style="myBackToTopStyle" :visibility-height="300" :back-position="0" transition-name="fade"/>
    </el-tooltip>

    <div class="mDetail">
      <h2>{{ blog.title }}</h2>
      <el-link icon="el-icon-edit" v-if="isOwner">
        <router-link :to="{name: 'BlogEdit', params:{blogId: blog.id}}">
          编辑
        </router-link>
      </el-link>
      <el-divider></el-divider>
      <div class="markdown-body" v-html="blog.content"></div>
    </div>
    <el-divider></el-divider>
  </div>
</template>

<script>
import 'github-markdown-css/github-markdown.css'
import Header from "@/components/Header";
import UserInfo from "@/components/UserInfo";
import BackToTop from "@/components/BackToTop/index";
import TopBar from "@/components/TopBar";

export default {
  name: "BlogDetail",
  components: {UserInfo, Header, BackToTop, TopBar},
  data() {
    return {
      myBackToTopStyle: {
        right: '50px',
        bottom: '50px',
        width: '40px',
        height: '40px',
        borderRadius: '4px',
        lineHeight: '45px', // 请保持与高度一致以垂直居中
        background: '#e7eaf1'// 按钮的背景颜色
      },
      blog: {
        id: "",
        title: "",
        author: "",
        content: ""
      },
      imgSrc: require('../assets/images/bg4.jpeg'),
      isOwner: false,
      drawer: false,
      innerDrawer: false
    }
  },
  methods: {
    goBack() {
      this.$router.push("/blogs-manager");
      const loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      setTimeout(() => {
        loading.close();
      }, 300);
    }
  },
  created() {
    const blogId = this.$route.params.blogId;
    this.$axios.get('/blog-manager/' + blogId).then(res => {

      const blog = res.data.data;
      this.blog.id = blog.id;
      this.blog.title = blog.title;
      this.blog.author = blog.author;
      this.blog.userId = blog.userId;

      let MarkdownIt = require("markdown-it");
      let md = new MarkdownIt();

      this.blog.content = md.render(blog.content);

      this.isOwner = (blog.userId === this.$store.getters.getUser.id)
    })
  }
}
</script>

<style scoped>

.mDetail {
  box-shadow: 0 2px 12px 0 #454444;
  background-color: rgba(172, 246, 246, 0.35);
  width: 100%;
  min-height: 700px;
  padding: 20px 15px;
  max-width: 75%;
  margin: 0 auto;
}

.mBlog {
  background-color: rgba(190, 147, 245, 0.5);
}

.goBack {
  padding: 1%;
}

</style>