<template>
  <div>
    <el-menu
        style="margin-bottom: 1%"
        :router="true"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
        background-color="#B0E0E6"
        text-color="#4682B4"
        active-text-color="#ffd04b">
      <el-menu-item>
        <el-button @click="drawer = true" type="danger" size="mini" circle>
          <el-avatar :src="user.avatar" :size="40"></el-avatar>
        </el-button>
      </el-menu-item>
      <el-menu-item>
        <router-link :to="{name:'PersonalSpace', params: {userId: user.userId}}" @click.native="refresh">
          个人空间
        </router-link>
      </el-menu-item>
      <el-submenu index="2">
        <template slot="title">内容分类</template>
        <el-menu-item index="/blogs-manager">公告</el-menu-item>
        <el-menu-item index="/blogs">贴子</el-menu-item>
        <el-menu-item index="/pics">照片墙</el-menu-item>
        <el-submenu index="2-4">
          <template slot="title">开发ing</template>
          <el-menu-item index="/map">地图</el-menu-item>
          <el-menu-item>选项2</el-menu-item>
          <el-menu-item>选项3</el-menu-item>
        </el-submenu>
      </el-submenu>
      <el-menu-item disabled>管理员</el-menu-item>
      <el-menu-item index="/suggestions">意见反馈</el-menu-item>
      <el-menu-item index="/info">关于</el-menu-item>

      <!--搜索栏-->
      <div style="margin-bottom: 10px; margin-top: 10px; margin-left: 60%; max-width: 500px;">
        <el-input placeholder="请输入搜索内容" v-model="input" class="input-with-select">
          <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
        </el-input>
      </div>

    </el-menu>

    <el-drawer
        class="mMenu"
        direction="ltr"
        title="_(:зゝ∠)_"
        :visible.sync="drawer"
        size="230px">
      <div>
        <Header></Header>
        <el-button @click="innerDrawer = true" type="primary" icon="el-icon-s-custom" style="margin-left: 10%"
                   circle></el-button>
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
  </div>
</template>

<script>
import Header from "@/components/Header";
import UserInfo from "@/components/UserInfo";

export default {
  name: "TopBar",
  components: {UserInfo, Header},
  inject: ["reload"],  //用来刷新

  data() {
    return {
      input: '',
      select: '',
      drawer: false,
      innerDrawer: false,
      user: {
        name: '旅行者',
        username: 'null',
        avatar: 'https://i.loli.net/2021/01/20/sU2HMY5xtFmdCrq.jpg',
        email: 'traveller@xx.com',
        userId: '游客'
      },
      indexUrl: ''
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      //console.log(key, keyPath);
    },
    search() {
      const msg = this.input;

      //通过query传参
      this.$router.push({
        path: "/search",
        query: {
          keyword: msg
        }
      })
      this.reload(); //刷新
    },
    refresh() {
      this.reload();
      const loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      setTimeout(() => {
        loading.close();
      }, 200);
    }
  },
  created() {
    try {
      if (this.$store.getters.getUser.username) {
        this.user.name = this.$store.getters.getUser.name;
        this.user.username = this.$store.getters.getUser.username;
        this.user.avatar = this.$store.getters.getUser.avatar;
        this.user.userId = this.$store.getters.getUser.id;
        this.user.email = this.$store.getters.getUser.email;
        this.indexUrl = '/space/' + this.user.userId;
      }
    } catch (e) {
    }
    this.input = this.$route.query.keyword;
  }
}
</script>

<style scoped>

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}


.mMenu_in {
  background-color: rgba(7, 31, 102, 0.3);
}

.mMenu {
  background-color: rgba(93, 7, 102, 0.2);
}


</style>