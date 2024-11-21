<template>
  <div>
    <TopBar></TopBar>
    <el-page-header class="goBack" @back="goBack" style="margin-bottom: 40px; margin-left: 15px" content="搜索结果">
    </el-page-header>
    <el-tooltip placement="top" content="回到顶部">
      <back-to-top :custom-style="myBackToTopStyle" :visibility-height="300" :back-position="0"
                   transition-name="fade"/>
    </el-tooltip>
    <el-tabs v-model="activeName" @tab-click="handleClick" style="width: 90%; margin: 0 auto">
      <el-tab-pane label="贴子" name="first">
        <h1 v-show="empty">抱歉，没有相关数据...</h1>
        <el-timeline v-show="!empty">
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
              <h6 style="text-align: right; font-weight: lighter">by {{ blog.author }}</h6>
            </el-card>
          </el-timeline-item>
        </el-timeline>
        <el-pagination class="mPages"
                       background
                       layout="prev, pager, next"
                       :current-page="currentPage"
                       :page-size="pageSize"
                       :total="total"
                       @current-change="pageBlog">
        </el-pagination>
      </el-tab-pane>

      <el-tab-pane label="用户" name="second" lazy>
        <h1 v-show="emptyUsers">抱歉，没有相关用户...</h1>
        <el-row v-show="!emptyUsers">
          <el-col :span="8" v-for="user in users">
            <el-card :body-style="{ padding: '0px' }" style="max-width: 300px; margin: 80px auto 0;">
              <el-image :src="user.avatar"
                        fit="fill"
                        style="width: 300px; height: 300px"
                        class="image"
                        alt="">
              </el-image>
              <div style="padding: 14px;">
                <h2 style="font-weight: bolder">{{ user.name }}</h2>
                <div class="bottom clearfix">
                  <time class="time"></time>
                  <router-link style="float: right; font-weight: initial"
                               :to="{name:'PersonalSpace', params: {userId: user.id}}">
                    {{ user.name }}的空间
                  </router-link>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
        <el-pagination class="mPages"
                       background
                       layout="prev, pager, next"
                       :current-page="currentPage"
                       :page-size="pageSizeUser"
                       :total="totalUsers"
                       style="margin-top: 25px"
                       @current-change="pageUser">
        </el-pagination>

      </el-tab-pane>
    </el-tabs>
    <el-divider></el-divider>
  </div>
</template>

<script>
import Header from "@/components/Header";
import UserInfo from "@/components/UserInfo";
import BackToTop from "@/components/BackToTop/index";
import TopBar from "@/components/TopBar";

export default {
  name: "Search",
  components: {BackToTop, UserInfo, Header, TopBar},

  data() {
    return {
      empty: false,
      emptyUsers: false,
      activeName: 'first',
      users: {},
      blogs: {},
      currentPage: 1,
      total: 0,
      totalUsers: 0,
      pageSize: 6,
      pageSizeUser: 6,
      keyword: '',
      myBackToTopStyle: {
        right: '50px',
        bottom: '50px',
        width: '40px',
        height: '40px',
        borderRadius: '4px',
        lineHeight: '45px', // 请保持与高度一致以垂直居中
        background: '#e7eaf1'// 按钮的背景颜色
      }
    }
  },
  methods: {
    pageBlog(currentPage) {
      this.$axios.get("/blogs-search", {
        params: {
          currentPage: currentPage,
          msg: this.keyword
        }
      }).then(res => {

       // console.log(res);
        this.blogs = res.data.data.records;
        this.currentPage = res.data.data.current;
        this.total = res.data.data.total;
        this.pageSize = res.data.data.size;

        if (this.total === 0) {
          this.empty = true;
        }
      });
    },

    pageUser(currentPage) {
      this.$axios.get("/user-search", {
        params: {
          currentPage: currentPage,
          msg: this.keyword
        }
      }).then(res => {

        //console.log(res);
        this.users = res.data.data.records;
        this.currentPage = res.data.data.current;
        this.totalUsers = res.data.data.total;
        this.pageSizeUser = res.data.data.size;

        if (this.totalUsers === 0) {
          this.emptyUsers = true;
        }
      });
    },

    goBack() {
      this.$router.go(-1);
      const loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      setTimeout(() => {
        loading.close();
      }, 300);
    },
    handleClick(tab, event) {
      //console.log(tab, event);
    }
  },
  created() {
    this.keyword = this.$route.query.keyword;   //接收参数
    //console.log("KEYWORD: ", this.keyword);
    this.pageBlog(1);
    this.pageUser(1);
  }
}
</script>

<style scoped>


.mPages {
  max-width: 55%;
  margin: 0 auto;
  text-align: center;
}


.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}
</style>