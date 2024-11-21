<template>
  <div class="mContext">
    <div>
      <div class="block">
        <el-avatar shape="square" :size="150" :src="user.avatar"></el-avatar>
        <div class="userName" style="margin-top: 5%">{{ user.name }}</div>
      </div>
      <el-menu class="el-menu-vertical-demo" style="margin-top: 10%">
        <el-menu-item index="1">
          <el-button v-show="!isLogined" @click="returnBlog()" type="primary" icon="el-icon-guide" round>新世界大门
          </el-button>
          <el-button v-show="isLogined" @click="returnBlog()" type="primary" icon="el-icon-trophy" round>看贴子们
          </el-button>
        </el-menu-item>
        <el-menu-item index="2">
          <el-button v-show="!isLogined" @click="toPics()" type="success" icon="el-icon-male" round>福利照片墙</el-button>
          <el-button v-show="isLogined" @click="toPics()" type="success" icon="el-icon-cpu" round>看照片们</el-button>
        </el-menu-item>
        <el-menu-item index="3">
          <el-button v-show="!isLogined" @click="toCheck()" type="info" icon="el-icon-watermelon" round>不发表贴子
          </el-button>
          <el-button v-show="isLogined" @click="addBlogs()" type="warning" icon="el-icon-magic-stick" round>发表贴子
          </el-button>
        </el-menu-item>
        <el-menu-item index="4">
          <el-button v-show="!isLogined" @click="toLogin()" type="warning" icon="el-icon-ice-drink" round>好兄弟登陆
          </el-button>
          <el-button v-show="isLogined" @click="toLogout()" type="danger" icon="el-icon-switch-button" round>退出登陆
          </el-button>
        </el-menu-item>
      </el-menu>
    </div>
  </div>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      user: {
        name: '旅行者，你好',
        avatar: 'https://i.loli.net/2021/01/20/sU2HMY5xtFmdCrq.jpg'
      },
      isLogined: false
    }
  },
  methods: {
    toLogout() {
      this.$axios.get("/logout", {
        headers: {
          "Authorization": localStorage.getItem("token")
        }
      }).then(res => {
        this.$store.commit("REMOVE_INFO");
        this.$router.push("/login");
        this.$notify.error({
          title: '用户已退出',
          message: '退出成功～'
        });
      })
    },
    returnBlog() {
      this.$router.push("/blogs");
      const loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      setTimeout(() => {
        loading.close();
      }, 600);
    },
    addBlogs() {
      this.$router.push("/blog/add");
    },
    toCheck() {
      this.$notify.warning({
        title: '提示',
        message: '抱歉，登陆后才能发表...'
      });
    },
    toLogin() {
      this.$router.push("/login");
    },
    toPics() {
      this.$router.push("/pics");
      const loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      setTimeout(() => {
        loading.close();
      }, 400);
    }
  },
  created() {
    if (this.$store.getters.getUser.username) {
      this.user.name = this.$store.getters.getUser.name;
      this.user.avatar = this.$store.getters.getUser.avatar;

      this.isLogined = true;
    }
  }
}
</script>

<style scoped>
.mContext {
  /*  max-width: 960px;
      margin: 0 auto;
  已在App.vue里全局定义，所以这两句可去掉,别处进行居中时没这两句同理
   */
  text-align: center;
}

.userName {
  color: #e329f6;
  font-weight: 700;
  font-size: medium;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 100%;
  min-height: 360px;
}

</style>