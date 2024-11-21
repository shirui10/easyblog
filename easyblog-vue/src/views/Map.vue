<template>
  <div>
    <TopBar></TopBar>
    <el-page-header class="goBack" @back="goBack" style="margin-left: 15px" content="地图">
    </el-page-header>
    <div class="mapbox">
      <baidu-map :center="center" :zoom="zoom" :scroll-wheel-zoom="true"
                 style="height: 600px; width: 1000px; margin: 0 auto;"
                 @ready="handler" @click="getClickInfo">
        <!-- 必须给容器指高度，不然地图将显示在一个高度为0的容器中，看不到 -->
        <bm-navigation anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-navigation>
        <bm-geolocation anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :showAddressBar="true" :autoLocation="true"></bm-geolocation>
        <bm-city-list anchor="BMAP_ANCHOR_TOP_LEFT"></bm-city-list>
      </baidu-map>
      <!-- 因为我采用的是全局注册，所以不用再在该页面上注册components -->
      <el-divider></el-divider>
    </div>
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
  name: "Map",
  components: {UserInfo, Header, BackToTop, TopBar},

  data() {
    return {
      center: {lng: 0, lat: 0},
      zoom: 13,
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
    handler({BMap, map}) {
      console.log(BMap, map);
      this.center.lng = 113.26472222222222;
      this.center.lat = 23.108888888888888;
    },
    getClickInfo(e) {
      console.log(e.point.lng);
      console.log(e.point.lat);
      this.center.lng = e.point.lng;
      this.center.lat = e.point.lat;
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
    }
  }
}
</script>

<style scoped>

</style>