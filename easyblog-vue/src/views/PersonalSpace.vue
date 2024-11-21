<template>
  <div>
    <TopBar></TopBar>
    <el-page-header class="goBack" @back="goBack" style="margin-left: 15px" content="个人空间">
    </el-page-header>
    <el-container style="margin-left: 50px; margin-right: 50px">
      <el-container>
        <el-aside width="400px" style="margin-top: 50px">
          <el-image
              style="width: 400px; height: 400px;"
              :src="user.avatar"
              :preview-src-list="srcList"
              fit="fill"></el-image>
          <h2 style="margin-top: 10px">昵称： {{ user.name }}</h2>
          <h2 style="margin-top: 10px">账号： {{ user.username }}</h2>
          <h2 style="margin-top: 10px">邮箱： {{ user.email }}</h2>
          <h2 style="margin-top: 10px">ID： {{ user.userId }}</h2>
        </el-aside>
        <el-container style="margin-left: 100px">
          <el-main>

            <h3>发表的贴子</h3>
            <el-timeline>
              <el-timeline-item style="color: blueviolet; margin-right: 5%" :timestamp="blog.created"
                                placement="top"
                                v-for="blog in blogs">
                <el-card class="card">
                  <h3>
                    <router-link :to="{name:'BlogDetail', params: {blogId: blog.id}}">
                      {{ blog.title }}
                    </router-link>
                  </h3>
                  <h4>{{ blog.description }}</h4>
                </el-card>
              </el-timeline-item>
            </el-timeline>
            <el-pagination class="mPages"
                           background
                           layout="prev, pager, next"
                           :current-page="currentPage"
                           :page-size="pageSize"
                           :total="total"
                           @current-change="page">
            </el-pagination>

          </el-main>
          <el-footer>

          </el-footer>
        </el-container>
      </el-container>
    </el-container>

    <el-tooltip placement="top" content="回到顶部">
      <back-to-top :custom-style="myBackToTopStyle" :visibility-height="300" :back-position="0"
                   transition-name="fade"/>
    </el-tooltip>
  </div>
</template>

<script>
import TopBar from "@/components/TopBar";
import BackToTop from "@/components/BackToTop/index";
import UserInfo from "@/components/UserInfo";
import Header from "@/components/Header";

export default {
  name: "PersonalSpace",
  components: {UserInfo, Header, BackToTop, TopBar},
  inject: ["reload"],  //用来刷新

  data() {
    return {
      user: {
        name: '旅行者',
        username: 'null',
        avatar: 'https://i.loli.net/2021/01/20/sU2HMY5xtFmdCrq.jpg',
        email: 'traveller@xx.com',
        userId: 'traveller'
      },
      srcList: [
        'https://i.loli.net/2021/01/20/sU2HMY5xtFmdCrq.jpg',
      ],
      myBackToTopStyle: {
        right: '50px',
        bottom: '50px',
        width: '40px',
        height: '40px',
        borderRadius: '4px',
        lineHeight: '45px', // 请保持与高度一致以垂直居中
        background: '#e7eaf1'// 按钮的背景颜色
      },
      blogs: {},
      currentPage: 1,
      total: 0,
      pageSize: 5
    }
  },
  methods: {
    goBack() {
      this.$router.go(-1);
      const loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      setTimeout(() => {
        this.reload();
        loading.close();
      }, 300);
    },

    page(currentPage) {
      if (this.$route.params.userId === this.$store.getters.getUser.id) {
        this.$axios.get("/blogs-person", {
              params: {
                currentPage: currentPage,
                userId: this.$store.getters.getUser.id
              }
            }
        ).then(res => {
          //console.log(res);
          this.blogs = res.data.data.records;
          this.currentPage = res.data.data.current;
          this.total = res.data.data.total;
          this.pageSize = res.data.data.size;
        });
      } else {
        this.$axios.get("/blogs-person", {
              params: {
                currentPage: currentPage,
                userId: this.$route.params.userId
              }
            }
        ).then(res => {
          //console.log(res);
          this.blogs = res.data.data.records;
          this.currentPage = res.data.data.current;
          this.total = res.data.data.total;
          this.pageSize = res.data.data.size;
        });
      }
    }
  },
  created() {
    const userId = this.$route.params.userId; //获取参数
    if (userId === this.$store.getters.getUser.id) {
      this.user.name = this.$store.getters.getUser.name;
      this.user.username = this.$store.getters.getUser.username;
      this.user.avatar = this.$store.getters.getUser.avatar;
      this.user.userId = this.$store.getters.getUser.id;
      this.user.email = this.$store.getters.getUser.email;

      this.srcList[0] = this.user.avatar;
    } else {
      try {
        this.$axios.get("/get-user", {
              params: {
                id: userId
              }
            }
        ).then(res => {
          this.user.name = res.data.data.name;
          this.user.username = res.data.data.username;
          this.user.avatar = res.data.data.avatar;
          this.user.userId = res.data.data.id;
          this.user.email = res.data.data.email;

          this.srcList[0] = res.data.data.avatar;
        });
      } catch (e) {
      }
    }
    this.page(1);
  }
}
</script>

<style scoped>

</style>