<template>
  <div class="mBlog">
    <TopBar></TopBar>
    <el-page-header class="goBack" @back="goBack" content="贴子详情">
    </el-page-header>
    <el-tooltip placement="top" content="回到顶部">
      <back-to-top :custom-style="myBackToTopStyle" :visibility-height="300" :back-position="0" transition-name="fade"/>
    </el-tooltip>
    <el-drawer
        class="mMenu"
        direction="ltr"
        title="_(:зゝ∠)_"
        :visible.sync="drawer"
        size="230px">
      <div>
        <Header></Header>
        <el-button @click="innerDrawer = true" icon="el-icon-s-custom" style="margin-left: 10%" circle></el-button>
        <el-drawer
            class="mMenu_in"
            title="(｡◝ᴗ◜｡)"
            size="335px"
            :append-to-body="true"
            :visible.sync="innerDrawer">
          <p>
            <UserInfo></UserInfo>
          </p>
        </el-drawer>
      </div>
    </el-drawer>

    <div class="mDetail">
      <h2>{{ blog.title }}</h2>
      <el-link icon="el-icon-edit" v-if="isOwner">
        <router-link :to="{name: 'BlogEdit', params:{blogId: blog.id}}">
          编辑
        </router-link>
      </el-link>
      <h5 style="text-align: right; font-weight: lighter">by {{ blog.author }}</h5>
      <el-divider></el-divider>
      <div class="markdown-body" v-html="blog.content"></div>

      <el-divider></el-divider>
      <!--回复输入-->
      <CommentBox :value1="value" :mode="false"></CommentBox>

      <!--回复列表-->
      <a-comment v-for="item in comments" :key="comments.commentId">
        <span slot="actions" @click="toReply(item.id, item.fromName)">回复</span>
        <CommentBox :value2="valueReply" :mode="true" :tip="tips" v-show="boxShow===item.id"></CommentBox>
        <p slot="author">{{ item.fromName }}</p>
        <a-avatar
            slot="avatar"
            :src="item.fromAvatar"
            :alt="item.fromName"
        />
        <p slot="content">
          {{ item.content }}
        </p>
        <a-tooltip slot="datetime" style="color: #333333">
          <span>{{ item.createTime }}</span>
        </a-tooltip>
        <a-comment v-for="item in comments[item.index].listCommentsReply" :key="comments.commentId">
          <span slot="actions" @click="toReplySon(item.fromName, item.id, item.commentId)">回复</span>
          <CommentBox :value3="valueReplySon" :mode="true" :mode2="true" :tip="tips"
                      v-show="boxShow===item.commentId"></CommentBox>
          <p slot="author">{{ item.fromName }}</p>
          <a-avatar
              slot="avatar"
              :src="item.fromAvatar"
              :alt="item.fromName"
          />
          <p slot="content">
            {{ item.content }}
          </p>
          <a-tooltip slot="datetime" style="color: #333333">
            <span>{{ item.createTime }}</span>
          </a-tooltip>
        </a-comment>
      </a-comment>
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
import CommentBox from "@/components/CommentBox";
import moment from "moment";

export default {
  name: "BlogDetail",
  components: {TopBar, UserInfo, Header, BackToTop, CommentBox},
  inject: ["reload"],
  data() {
    return {
      replaceUrl: "", // 记录你一开始进入的页面
      comments: [
        {
          index: 0,
          id: '',
          commentId: '',
          fromName: '',
          fromAvatar: '',
          content: '',
          createTime: '',
          listCommentsReply: [
            {
              id: '',
              commentId: '',
              fromName: '',
              fromAvatar: '',
              content: '',
              createTime: '',
            }
          ]
        },
      ],
      moment,
      userInfo: {
        username: 'traveller',
        name: '旅行者，你好',
        avatar: 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=4087125932,4288183110&fm=26&gp=0.jpg'
      },
      value: {
        ownerId: '',
        fromId: '',
        fromName: '',
        fromAvatar: '',
        content: ''
      },
      valueReply: {
        id: '',
        fromId: '',
        fromName: '',
        fromAvatar: '',
        toName: '',
        toId: '',
        toAvatar: '',
        content: ''
      },
      valueReplySon: {
        id: '',
        fromId: '',
        fromName: '',
        fromAvatar: '',
        toName: '',
        toId: '',
        toAvatar: '',
        content: ''
      },
      num: -1,
      boxShow: '',
      tips: '',

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
        author: "",
        title: "",
        content: ""
      },
      imgSrc: require('../assets/images/bg4.jpeg'),
      isOwner: false,
      drawer: false,
      innerDrawer: false
    }
  },

  // this还不能直接取到
  beforeRouteEnter(to, from, next) {
    next(vm => {
      //因为当钩子执行前，组件实例还没被创建
      // vm 就是当前组件的实例相当于上面的 this，所以在 next 方法里你就可以把 vm 当 this 来用了。
      //console.log(vm);//当前组件的实例
      vm.replaceUrl = from.path;
    });
  },
  mounted() {
    // 挂载完成后，判断浏览器是否支持popstate
    if (window.history && window.history.pushState) {
      history.pushState(null, null, document.URL);
      /*popstate触发时机：当前历史条目为pushState创建时，事件被触发*/
      window.addEventListener('popstate', this.goBack, false);
    }
  },

  methods: {

    toReply(ID, name) {
      this.boxShow = ID;
      this.tips = "回复 @" + name + "：";
      this.valueReply.id = ID;
      //console.log("ID: " + ID);
    },

    toReplySon(name, ID, commentID) {
      this.boxShow = commentID;
      this.tips = "回复 @" + name + "：";
      this.valueReplySon.id = ID;
      this.valueReplySon.fromName = name;
      //console.log("ID: " + ID);
    },

    goBack() {
      let routerArray = [
        "/blog/" + this.blog.id + "/edit",
      ];
      if (routerArray.includes(this.replaceUrl)) {
        //判断，当获取上个页面进来的路由是D的时候，返回到A页面
        this.$router.replace({ path: "/blogs" });
      }
      else {
        this.$router.go(-1);
        //判断，当有其他返回值的时候，返回到默认页面
      }
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
  // 页面销毁时，取消监听。否则其他vue路由页面也会被监听 这个必须得写啊！不然来回跳转报错
  destroyed() {
    window.removeEventListener('popstate', this.goBack, false);
  },
  created() {

    try {
      if (this.$store.getters.getUser.username) {
        this.userInfo.name = this.$store.getters.getUser.name;
        this.userInfo.username = this.$store.getters.getUser.username;
        this.userInfo.avatar = this.$store.getters.getUser.avatar;
      }
    } catch (e) {

    }

    const blogId = this.$route.params.blogId;
    this.$axios.get('/blog/' + blogId).then(res => {

      const blog = res.data.data;
      this.blog.id = blog.id;
      this.blog.author = blog.author;
      this.blog.title = blog.title;
      this.blog.userId = blog.userId;

      this.value.ownerId = blog.id;

      let MarkdownIt = require("markdown-it");
      let md = new MarkdownIt();

      this.blog.content = md.render(blog.content);

      this.isOwner = (blog.userId === this.$store.getters.getUser.id)
    })

    //console.log("ID: " + blogId);
    this.$axios.get('/getListByOwnerId', {
      headers: {
        "ownerId": blogId
      }
    }).then(res => {
      let commentList = res.data.data;

      this.comments = commentList

      //专门给comments建立一个index属性用来循环子评论，获取到后端数据后才进行赋值，仅在前端使用，避免直接用原有的id来进行循环而出现id与comments数组中的数据的下标不匹配而获取不到数据而出错
      for (let i = 0; i < commentList.length; i++) {
        this.num = this.num + 1;
        this.comments[i].index = this.num;
      }


      //console.log("TIME: " + commentList[0].createTime);
      //console.log("msg: " + commentList[0].listCommentsReply[0])
      //console.log("commentNum: " + this.comments[0].index)
    });

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