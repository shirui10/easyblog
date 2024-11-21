<template>
  <div>
    <div class="background">
      <img :src="imgSrc1" width="100%" height="100%" alt=""/>
    </div>
    <el-page-header class="goBack" @back="goBack" content="注册" style="color: #e329f6">
    </el-page-header>
    <el-container class="mLogin">
      <el-header>
        <img class="mLogo" alt="">
      </el-header>
      <el-main>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="昵称" prop="name">
            <el-input v-model="ruleForm.name"></el-input>
          </el-form-item>
          <el-form-item label="账号" prop="username">
            <el-input v-model="ruleForm.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="ruleForm.password" show-password></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="ruleForm.email"></el-input>
          </el-form-item>
          <el-form-item label="头像地址" prop="avatar">
            <el-input v-model="ruleForm.avatar"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="success" :plain="true" @click="submitForm('ruleForm')">注册</el-button>
            <el-button type="info" :plain="true" @click="resetForm('ruleForm')" icon="el-icon-refresh"
                       circle></el-button>
          </el-form-item>
        </el-form>
      </el-main>
      <el-link href="https://sm.ms/" target="_blank" style="font-weight: inherit">点击进入此处把头像拖入Upload后得到的Image URL即是地址
      </el-link>
    </el-container>
    <div style="margin-top: 39.5%; margin-right: 1%; text-align: right">
      <p style="font-size: small; font-weight: unset; margin-right: 9%; color: #ffffff">
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
  name: "Register",
  data() {
    //正则表达式验证邮箱格式
    var validateEMail = (rule, value, callback) => {
      const reg = /^([a-zA-Z0-9]+[-_.]?)+@[a-zA-Z0-9]+.[a-z]+$/;
      if (value === '' || value === undefined || value == null) {
        callback(new Error('请输入邮箱'));
      } else {
        if (!reg.test(value)) {
          callback(new Error('请输入正确的邮箱'));
        } else {
          callback();
        }
      }
    };
    return {
      imgSrc_icon1: require('../assets/images/Wechat_icon.jpeg'),
      imgSrc_QR1: require('../assets/images/Wechat_QR.jpeg'),
      imgSrc_icon2: require('../assets/images/Twitter_icon.jpeg'),
      imgSrc_QR2: require('../assets/images/Twitter_QR.png'),
      imgSrc_icon3: require('../assets/images/Email_icon.jpg'),
      imgSrc1: require('../assets/images/bg7.jpeg'),
      ruleForm: {
        name: '',
        username: '',
        password: '',
        email: '',
        avatar: ''
      },
      rules: {
        name: [
          {required: true, message: '请输入昵称', trigger: 'blur'},
          {min: 2, max: 15, message: '长度在 2 到 15 个字符', trigger: 'blur'}
        ],
        username: [
          {required: true, message: '请输入账号', trigger: 'blur'},
          {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
        email: [
          {validator: validateEMail, required: true, trigger: 'change'}
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          /* const _this = this
          *本来此处如果直接this的话，this会是单纯该axios请求的this而非最初全局的this，从而需要专门定义一个常量去对应全局的this，但下面用了箭头函数（这能防止this
          *关键字混淆的问题产生），因而不用专门建立 _this 常量去对应也可,但使用也算是一种编码规范吧，_this一般用来定义为最初的this，but无论如何看情况去用就好
           */
          this.$axios.post('/register', this.ruleForm).then(res => {

            this.$message.success('注册成功哦～');

            this.$router.push("/login");

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
    }
  }
}
</script>

<style scoped>
.el-header, .el-footer {
  background-color: #80000000;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  /*background-color: #E9EEF3;*/
  color: #333;
  text-align: center;
  line-height: 160px;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}

.mLogo {
  height: 90%;
  margin: 5px;
}

.demo-ruleForm {
  max-width: 320px;
  margin-left: 0;
}

.background {
  width: 100%;
  height: 100%; /**宽高100%是为了图片铺满屏幕 */
  z-index: -1;
  position: absolute;
}

.mLogin {
  width: 400px;
  height: 500px;
  background-color: rgba(245, 245, 245, 0.7);
  border-radius: 10px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.goBack {
  padding: 1%;
}

</style>