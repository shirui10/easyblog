<template>
  <div class="mContext">
    <el-avatar shape="square" :size="150" :src="user.avatar"></el-avatar>
    <el-container>
      <el-aside width="60px" style="margin-top: 6.9%; margin-left: 30%; text-align: left">
        <div class="name">{{ "昵称:" }}</div>
        <div class="userName">{{ "账号:" }}</div>
        <div class="email">{{ "Email:" }}</div>
        <div class="Id">{{ "ID:" }}</div>
      </el-aside>
      <el-main style="text-align: left; margin-top: 1%">
        <div class="name">{{ user.name }}</div>
        <div class="userName">{{ user.username }}</div>
        <div class="email">{{ user.email }}</div>
        <div class="Id">{{ user.userId }}</div>
      </el-main>
    </el-container>
    <div>
      <h3 class="tip">好好想想，别忘了做什么事哟～</h3>
      <div class="date">{{ date }}</div>
      <div class="time">{{ time }}</div>
    </div>
  </div>
</template>

<script>
export default {

  name: "UserInfo",
  data() {
    return {
      user: {
        name: '旅行者',
        username: 'null',
        avatar: 'https://i.loli.net/2021/01/20/sU2HMY5xtFmdCrq.jpg',
        email: 'traveller@xx.com',
        userId: '游客'
      },
      date: new Date(),
      time: new Date(),
      isLogined: false
    }
  },
  mounted() {
    let _this = this;
    this.timer = setInterval(function () {
      _this.time = new Date().toLocaleTimeString();
      _this.date = new Date().toLocaleDateString();
    });
  },
  beforeDestroy: function () {
    if (this.timer) {
      clearInterval(this.timer);
    }
  },
  methods: {},
  created() {
    if (this.$store.getters.getUser.username) {
      this.user.name = this.$store.getters.getUser.name;
      this.user.username = this.$store.getters.getUser.username;
      this.user.avatar = this.$store.getters.getUser.avatar;
      this.user.email = this.$store.getters.getUser.email;
      this.user.userId = this.$store.getters.getUser.id;

      this.isLogined = true;
    }
  }
}
</script>

<style scoped>
.mContext {
  max-width: 960px;
  margin: 0 auto;
  text-align: center;
}

.userName {
  color: #f75efc;
  font-weight: 700;
  font-size: medium;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 100%;
  min-height: 400px;
}

.email {
  color: #05bdf5;
  font-weight: 700;
  font-size: medium;
}

.Id {
  color: #23e500;
  font-weight: 700;
  font-size: medium;
}

.time {
  font-weight: bold;
  font-size: xxx-large;
  color: #2af5d7;
}

.tip {
  margin-top: 22%;
  font-weight: bold;
  color: #ff8809;
}

.date {
  padding-top: 2%;
  font-weight: bold;
  font-size: xxx-large;
  color: #b910ff;
}

.name {
  color: #f6105a;
  font-weight: 700;
  font-size: medium;
}
</style>