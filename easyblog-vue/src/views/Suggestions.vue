<template>
  <div>
    <div class="background">
      <img :src="imgSrc1" width="100%" height="100%" alt=""/>
    </div>
    <el-page-header class="goBack" @back="goBack" style="padding: 20px" content="意见反馈">
    </el-page-header>
    <div style="width: 80%; margin: 150px auto 0;">
      <h1 style="text-align: center; font-weight: lighter">💗你提意见的样子真帅💗</h1>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm"
               style="margin-top: 50px">
        <el-form-item label="💡意见" prop="content">
          <el-input v-model="ruleForm.content" type="textarea"></el-input>
        </el-form-item>
        <el-form-item style="text-align: center">
          <el-button type="success" :plain="true" @click="submitForm('ruleForm')" round>提交</el-button>
          <el-button type="primary" :plain="true" @click="resetForm('ruleForm')" round>重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div style="margin-right: 5%; margin-top: 13%;text-align: right">
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
  </div>
</template>

<script>
export default {
  name: "Suggestions",
  data() {
    return {
      imgSrc_icon1: require('../assets/images/Wechat_icon.jpeg'),
      imgSrc_QR1: require('../assets/images/Wechat_QR.jpeg'),
      imgSrc_icon2: require('../assets/images/Twitter_icon.jpeg'),
      imgSrc_QR2: require('../assets/images/Twitter_QR.png'),
      imgSrc_icon3: require('../assets/images/Email_icon.jpg'),
      imgSrc1: require('../assets/images/bg8.jpeg'),
      userInfo: {
        username: 'traveller',
        name: '旅行者，你好',
        avatar: 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=4087125932,4288183110&fm=26&gp=0.jpg'
      },

      ruleForm: {
        content: '',
        name: ''
      },
      rules: {
        content: [
          {required: true, message: '请输入内容', trigger: 'blur'},
          {min: 2, message: '长度在 2 个字符及以上', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          /* const _this = this
          *本来此处如果直接this的话，this会是单纯该axios请求的this而非最初全局的this，从而需要专门定义一个常量去对应全局的this，但下面用了箭头函数（这能防止this
          *关键字混淆的问题产生），因而不用专门建立 _this 常量去对应也可,但使用也算是一种编码规范吧，_this一般用来定义为最初的this，but无论如何看情况去用就好
           */
          this.$axios.post('/suggestions', this.ruleForm).then(res => {

            this.$message.success('谢谢宝贵的意见～');


            const loading = this.$loading({
              lock: true,
              text: 'Loading',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.7)'
            });
            setTimeout(() => {
              loading.close();
            }, 600);
          });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
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
  },
  created() {
    try {
      if (this.$store.getters.getUser.username) {
        this.userInfo.name = this.$store.getters.getUser.name;
        this.userInfo.username = this.$store.getters.getUser.username;
        this.userInfo.avatar = this.$store.getters.getUser.avatar;

        this.ruleForm.name = this.userInfo.name;
      }
    } catch (e) {

    }
  }
}
</script>

<style scoped>
.background {
  width: 100%;
  height: 100%; /**宽高100%是为了图片铺满屏幕 */
  z-index: -1;
  position: absolute;
}
</style>