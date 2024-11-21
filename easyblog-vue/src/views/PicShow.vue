<template>
  <div class="myBackground" :style="myBackground">

    <TopBar></TopBar>

    <el-page-header class="goBack" @back="goBack" content="照片墙">
    </el-page-header>
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

        <div class="PicsWall">
          <el-card class="box-card">
            <h1 class="mTip">照片墙</h1>
            <div v-for="o in 2" :key="o" class="text item">
              <el-row>
                <el-col :span="8" v-for="(o, index) in 2" :key="o" :offset="index > 0 ? 6 : 0">
                  <el-card :body-style="{ padding: '0px' }" class="card">
                    <img src="https://i.loli.net/2021/01/21/UNaymMVoxSG15q9.jpg"
                         class="image">
                    <div style="padding: 14px;">
                      <span>我是小丑吗</span>
                      <div class="bottom clearfix">
                        <el-button type="text" class="button" @click="no()">否</el-button>
                        <el-button type="text" class="button" @click="yes()">是</el-button>
                      </div>
                    </div>
                  </el-card>
                </el-col>
              </el-row>
            </div>
          </el-card>
        </div>
        <el-divider></el-divider>
        <el-container scroll-container class="mPics" style="height: 400px; border: 1px solid #eee">
          <el-main>
            <div class="demo-image__lazy">
              <el-image v-for="url in urls" :key="url" :src="url" :preview-src-list="urls" lazy></el-image>
            </div>
          </el-main>
        </el-container>
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
  name: "PicShow",
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
      myBackground: {
        //backgroundImage: "url(" + require("../assets/images/bg6.jpeg") + ") ",
        backgroundPosition: "absolute",
        backgroundRepeat: "no-repeat",
        backgroundSize: "100% 100%"
      },
      blogs: {},
      currentPage: 1,
      total: 0,
      pageSize: 5,
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
      urls: [
        'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg',
        'https://fuss10.elemecdn.com/1/34/19aa98b1fcb2781c4fba33d850549jpeg.jpeg',
        'https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg',
        'https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg',
        'https://fuss10.elemecdn.com/d/e6/c4d93a3805b3ce3f323f7974e6f78jpeg.jpeg',
        'https://fuss10.elemecdn.com/3/28/bbf893f792f03a54408b3b7a7ebf0jpeg.jpeg',
        'https://fuss10.elemecdn.com/2/11/6535bcfb26e4c79b48ddde44f4b6fjpeg.jpeg',
      ]
    }
  },
  methods: {
    page(currentPage) {
      this.$axios.get("/blogs?currentPage=" + currentPage).then(res => {

        console.log(res);
        this.blogs = res.data.data.records;
        this.currentPage = res.data.data.current;
        this.total = res.data.data.total;
        this.pageSize = res.data.data.size;

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
      }, 500);
    },
    load() {
      this.count += 2
    },
    yes() {
      window.open('https://tieba.baidu.com/f?ie=utf-8&kw=%E6%8A%97%E5%8E%8B%E8%83%8C%E9%94%85', '_blank');
    },
    no() {
      window.open('https://space.bilibili.com/', '_blank');
    }
  },
  created() {
    this.page(1);
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

.mShow {
  margin-left: 15%;
  margin-bottom: 3%;
  margin-right: 15%;
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

.PicsWall {
  max-width: 75%;
  margin: 0 auto;
}

.mTip {
  margin: 0 auto;
  text-align: center;
  color: rgb(174, 6, 231);
  font-weight: normal;
}

.goBack {
  padding: 1%;
}

.box-card {
  background-color: rgba(8, 39, 141, 0.3);
}

.item {
  padding: 3%;
}

.myBackground {
  background-color: rgba(239, 26, 111, 0.5);
}

.mPics {
  width: 60%;
  height: 50%;
  margin: 0 auto;
}

</style>