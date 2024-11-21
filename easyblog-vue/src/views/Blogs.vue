<template>
  <div class="myBackground" :style="myBackground">
    <TopBar></TopBar>
    <a-divider dashed/>
    <el-container>
      <el-main>
        <el-tooltip placement="top" content="回到顶部">
          <back-to-top :custom-style="myBackToTopStyle" :visibility-height="300" :back-position="0"
                       transition-name="fade"/>
        </el-tooltip>
        <el-carousel class="mShow" :interval="4000" arrow="always">
          <el-carousel-item v-for="item in items" :key="item">
            <el-image style="width: 100%; height: 100%" :src="item" fit="cover" :preview-src-list="items"></el-image>
          </el-carousel-item>
        </el-carousel>
        <div class="block">
          <el-tabs v-model="activeName" type="card" @tab-click="handleClick" tab-position="right">
            <el-tab-pane label="贴子" name="first" lazy style="margin-top: 5%">
              <h1 class="mTip">铁(tiē)子(zi)们</h1>
              <el-timeline>
                <el-timeline-item style="color: blueviolet; margin-right: 5%" :timestamp="blog.created"
                                  placement="top"
                                  v-for="blog in blogs_top">
                  <el-card class="card">
                    <h3>
                      <el-tag type="danger" size="mini" effect="light">置顶</el-tag>
                      <router-link :to="{name:'BlogDetail', params: {blogId: blog.id}}">
                        {{ blog.title }}
                      </router-link>
                    </h3>
                    <h4>{{ blog.description }}</h4>
                    <h6 style="text-align: right; font-weight: lighter">by {{ blog.author }}</h6>
                  </el-card>
                </el-timeline-item>
              </el-timeline>


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
                    <h6 style="text-align: right; font-weight: lighter">by {{ blog.author }}</h6>
                  </el-card>
                </el-timeline-item>
              </el-timeline>
              <el-pagination class="mPages"
                             background
                             style="margin-bottom: 20px"
                             layout="prev, pager, next"
                             :current-page="currentPage"
                             :page-size="pageSize"
                             :total="total"
                             @current-change="page">
              </el-pagination>

            </el-tab-pane>
            <el-tab-pane label="公告" name="second" lazy>
            </el-tab-pane>
          </el-tabs>
        </div>


        <el-divider></el-divider>

        <div style="margin-right: 7%; text-align: right">
          <p style="font-size: small; font-weight: unset; margin-right: 9%">
            开发者：
          </p>
          <el-tooltip placement="top" effect="light">
            <div slot="content" style="text-align: center; font-weight: bold"><img style="width: 150px; height: 150px"
                                                                                   :src="imgSrc_QR1" alt=""><br/>开发者微信
            </div>
            <el-avatar :src="imgSrc_icon1" :size="40"></el-avatar>
          </el-tooltip>
          <el-tooltip placement="top" effect="light" style="margin-left: 20px">
            <div slot="content" style="text-align: center; font-weight: bold"><img style="width: 150px; height: 150px"
                                                                                   :src="imgSrc_QR2" alt=""><br/>开发者推特
            </div>
            <el-avatar :src="imgSrc_icon2" :size="40"></el-avatar>
          </el-tooltip>
          <el-tooltip placement="top" effect="light" style="margin-left: 20px">
            <div slot="content" style="text-align: center; font-weight: bold"><h2>123@qq.com</h2><br/>开发者邮箱</div>
            <el-avatar :src="imgSrc_icon3" :size="40"></el-avatar>
          </el-tooltip>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import Header from "@/components/Header";
import UserInfo from "@/components/UserInfo";
import BackToTop from "@/components/BackToTop/index";
import TopBar from "@/components/TopBar";

export default {
  name: "Blogs",
  components: {BackToTop, UserInfo, Header, TopBar},
  data() {
    return {
      imgSrc_icon1: require('../assets/images/Wechat_icon.jpeg'),
      imgSrc_QR1: require('../assets/images/Wechat_QR.jpeg'),
      imgSrc_icon2: require('../assets/images/Twitter_icon.jpeg'),
      imgSrc_QR2: require('../assets/images/Twitter_QR.png'),
      imgSrc_icon3: require('../assets/images/Email_icon.jpg'),
      myBackToTopStyle: {
        right: '50px',
        bottom: '50px',
        width: '40px',
        height: '40px',
        borderRadius: '4px',
        lineHeight: '45px', // 请保持与高度一致以垂直居中
        background: '#e7eaf1'// 按钮的背景颜色
      },
      activeIndex: '1',
      activeIndex2: '1',
      activeName: 'first',
      myBackground: {
        //backgroundImage: "url(" + require("../assets/images/bg2.jpeg") + ") ",
        backgroundPosition: "absolute",
        backgroundRepeat: "no-repeat",
        backgroundSize: "100% 100%"
      },
      blogs_manager: {},
      currentPage_manager: 1,
      total_manager: 0,
      pageSize_manager: 5,
      blogs_manager_top: {},
      currentPage_manager_top: 1,
      total_manager_top: 0,
      pageSize_manager_top: 5,
      blogs: {},
      currentPage: 1,
      total: 0,
      pageSize: 5,
      blogs_top: {},
      currentPage_top: 1,
      total_top: 0,
      pageSize_top: 5,
      imgSrc: require('../assets/images/bg2.jpeg'),
      count: 0,
      drawer: false,
      innerDrawer: false,
      items: [
        'https://i.im5i.com/2021/01/21/QfLpl.jpg',
        'https://i.loli.net/2021/04/08/J6x5p1WescAg2Vw.jpg',
        'https://i.im5i.com/2021/01/21/QfzK7.jpg',
        'https://i.im5i.com/2021/01/21/QfARn.jpg',
        'https://i.loli.net/2021/04/08/EiZz6pfy7sFVTnj.jpg',
        'https://i.im5i.com/2021/01/21/Qfkyt.gif',
        'https://i.im5i.com/2021/01/21/Ql1Yq.gif',
        'https://i.loli.net/2021/04/08/xIv8OrUKoYQR1VL.jpg',
        'https://i.loli.net/2021/04/08/oj7ZfS5QT3nrtki.png',
        'https://i.loli.net/2021/04/08/hABg6iLZKkV1JQ9.png',
        'https://i.im5i.com/2021/01/21/Qf0j2.jpg'
      ],
      names:[
          "阿夸","阿夸","阿夸","阿夸","阿夸","阿夸","阿夸","阿夸","阿夸","阿夸"
      ]
    }
  },
  methods: {
    handleSelect(key, keyPath) {
      //console.log(key, keyPath);
    },

    page(currentPage) {
      this.$axios.get("/blogs?currentPage=" + currentPage).then(res => {

        //console.log(res);
        this.blogs = res.data.data.records;
        this.currentPage = res.data.data.current;
        this.total = res.data.data.total;
        this.pageSize = res.data.data.size;

      });
    },
    page_top(currentPage) {
      this.$axios.get("/blogs-top?currentPage=" + currentPage).then(res => {

        //console.log(res);
        this.blogs_top = res.data.data.records;
        this.currentPage_top = res.data.data.current;
        this.total_top = res.data.data.total;
        this.pageSize_top = res.data.data.size;

      });
    },
    page_manager(currentPage) {
      this.$axios.get("/blogs-manager?currentPage=" + currentPage).then(res => {

        //console.log(res);
        this.blogs_manager = res.data.data.records;
        this.currentPage_manager = res.data.data.current;
        this.total_manager = res.data.data.total;
        this.pageSize_manager = res.data.data.size;

      });
    },
    page_manager_top(currentPage) {
      this.$axios.get("/blogs-manager-top?currentPage=" + currentPage).then(res => {

        //console.log(res);
        this.blogs_manager_top = res.data.data.records;
        this.currentPage_manager_top = res.data.data.current;
        this.total_manager_top = res.data.data.total;
        this.pageSize_manager_top = res.data.data.size;

      });
    },
    load() {
      this.count += 2
    },
    handleClick(tab, event) {
      this.$router.push("/blogs-manager");
      //console.log(tab, event);
    }
  },
  created() {
    //console.log();
    this.page(1);
    this.page_top(1);
    this.page_manager(1);
    this.page_manager_top(1);
  },
}
</script>

<style scoped>
.block {
  max-width: 75%;
  margin: 0 auto;
  background-color: rgba(255, 255, 255, 0.5);
}

.card {
  background-color: #3f5c6d2c;
}

.mPages {
  max-width: 55%;
  margin: 0 auto;
  text-align: center;
}

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

.mShow {
  margin-left: 15%;
  margin-bottom: 3%;
  margin-right: 15%;
}

.mTip {
  margin: 0 auto;
  text-align: center;
  font-weight: initial;
  color: rgb(114, 5, 124);
}

.myBackground {
  background-color: rgba(0, 86, 105, 0.8);
}

</style>