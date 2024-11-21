<template>
  <div>
    <el-page-header class="goBack" @back="goBack" style="margin: 20px" content="关于">
    </el-page-header>
    <p style="font-size: xxx-large; text-align: center; margin: 13%; font-weight: bolder">
      ❗️此网站仅作学习交流使用，禁止传播非法信息，如有必要会通过提供后台信息采取法律措施。
    </p>
    <p style="font-size: large; text-align: center;font-weight: lighter">
      此网站仍在随缘开发中，如有疑问，可通过以下方式联系：
    </p>
    <div style="margin: 0 auto; text-align: center">
      <el-tooltip placement="top" effect="light">
        <div slot="content" style="text-align: center; font-weight: bold"><img style="width: 200px; height: 200px"
                                                                               :src="imgSrc_QR1" alt=""><br/>开发者微信
        </div>
        <el-avatar :src="imgSrc_icon1" :size="60"></el-avatar>
      </el-tooltip>
      <el-tooltip placement="top" effect="light" style="margin-left: 50px">
        <div slot="content" style="text-align: center; font-weight: bold"><img style="width: 200px; height: 200px"
                                                                               :src="imgSrc_QR2" alt=""><br/>开发者推特
        </div>
        <el-avatar :src="imgSrc_icon2" :size="60"></el-avatar>
      </el-tooltip>
      <el-tooltip placement="top" effect="light" style="margin-left: 50px">
        <div slot="content" style="text-align: center; font-weight: bold"><h2>123@qq.com</h2><br/>开发者邮箱</div>
        <el-avatar :src="imgSrc_icon3" :size="60"></el-avatar>
      </el-tooltip>
    </div>
  </div>
</template>

<script>
export default {
  name: "Info",
  data() {
    return {
      imgSrc_icon1: require('../assets/images/Wechat_icon.jpeg'),
      imgSrc_QR1: require('../assets/images/Wechat_QR.jpeg'),
      imgSrc_icon2: require('../assets/images/Twitter_icon.jpeg'),
      imgSrc_QR2: require('../assets/images/Twitter_QR.png'),
      imgSrc_icon3: require('../assets/images/Email_icon.jpg'),
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
        loading.close();
      }, 500);
    }
  }
}
</script>

<style scoped>

</style>