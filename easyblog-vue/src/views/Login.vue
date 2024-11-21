<template>
  <div>
    <div class="background">
      <img :src="imgSrc1" width="100%" height="100%" alt=""/>
    </div>
    <el-page-header class="goBack" @back="goBack" content="登陆">
    </el-page-header>
    <el-container class="mRegister">
      <el-header>
        <img class="mLogo" alt="">
      </el-header>
      <el-main>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="ruleForm.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="ruleForm.password" show-password></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="success" :plain="true" @click="submitForm('ruleForm')">登陆</el-button>
            <el-button type="primary" :plain="true" @click="goRegister()">注册</el-button>
            <el-button type="warning" :plain="true" @click="traveller()">游客</el-button>
            <el-button type="info" :plain="true" @click="resetForm('ruleForm')" icon="el-icon-refresh"
                       circle></el-button>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      ruleForm: {
        username: '',
        password: ''
      },
      imgSrc1: require('../assets/images/bg.jpeg'),
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
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
          this.$axios.post('/login', this.ruleForm).then(res => {

            const jwt = res.headers['authorization'];
            const userInfo = res.data.data;

            //把数据共享出去
            this.$store.commit("SET_TOKEN", jwt);
            this.$store.commit("SET_USERINFO", userInfo);

            //获取
            //console.log(this.$store.getters.getUser);

            this.$message.success('登陆成功哦～');

            this.$router.go(-1);

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
    traveller() {
      const loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      setTimeout(() => {
        loading.close();
      }, 600);

      this.$message.success('欢迎光临～');
      this.$router.push("/blogs");
    },
    goBack() {
      this.$router.go(-1);
    },
    goRegister() {
      this.$router.push("/register");
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
  max-width: 600px;
  margin: 0 auto;
}

.background {
  width: 100%;
  height: 100%; /**宽高100%是为了图片铺满屏幕 */
  z-index: -1;
  position: absolute;
}

.mRegister {
  width: 450px;
  height: 286px;
  background-color: #80000000;
  border-radius: 3px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.goBack {
  padding: 1%;
}

</style>