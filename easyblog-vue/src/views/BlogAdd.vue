<template>
  <div class="mEdit">

    <TopBar></TopBar>

    <el-page-header class="goBack" @back="goBack" content="贴子发表">
    </el-page-header>

    <div class="background">
      <img :src="imgSrc" width="100%" height="110%" alt=""/>
    </div>

    <el-tooltip placement="top" content="回到顶部">
      <back-to-top :custom-style="myBackToTopStyle" :visibility-height="300" :back-position="0" transition-name="fade"/>
    </el-tooltip>

    <div class="mContent">

      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <!--
        <el-form-item label="是否置顶" prop="is_stick">
          <div style="text-align: left">
            <el-radio-group v-model="radio" @change="change">
              <el-radio label="1" border size="medium">是</el-radio>
              <el-radio label="0" border size="medium">否</el-radio>
            </el-radio-group>
          </div>
        </el-form-item>
        <el-form-item label="权重" prop="weight_num" v-if="isTop">
          <div style="text-align: left">
            <el-select v-model="value" placeholder="请选择" @change="weight_change">
              <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </div>
        </el-form-item>
        -->
        <el-form-item label="标题" prop="title">
          <el-input v-model="ruleForm.title"></el-input>
        </el-form-item>
        <el-form-item label="摘要" prop="description">
          <el-input type="textarea" v-model="ruleForm.description"></el-input>
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <mavon-editor v-model="ruleForm.content"></mavon-editor>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" plain @click="submitForm('ruleForm')">确认操作</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>

    </div>
  </div>
</template>

<script>
import Header from "@/components/Header";
import UserInfo from "@/components/UserInfo";
import BackToTop from "@/components/BackToTop/index";
import TopBar from "@/components/TopBar";

export default {
  name: "BlogEdit",
  components: {UserInfo, Header, BackToTop, TopBar},
  data() {
    return {
      userInfo: {
        username: 'traveller',
        name: '旅行者，你好',
        avatar: 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=4087125932,4288183110&fm=26&gp=0.jpg'
      },
      myBackToTopStyle: {
        right: '50px',
        bottom: '50px',
        width: '40px',
        height: '40px',
        borderRadius: '4px',
        lineHeight: '45px', // 请保持与高度一致以垂直居中
        background: '#e7eaf1'// 按钮的背景颜色
      },
      options: [{
        value: '0',
        label: '默认（最低权重）'
      }, {
        value: '10',
        label: '1'
      }, {
        value: '9',
        label: '2'
      }, {
        value: '8',
        label: '3'
      }, {
        value: '7',
        label: '4'
      }, {
        value: '6',
        label: '5'
      }],
      value: '0',
      radio: '',
      ruleForm: {
        id: '',
        title: '',
        author: '',
        description: '',
        content: '',
        is_stick: 0,
        weight_num: ''
      },
      isTop: true,
      drawer: false,
      innerDrawer: false,
      imgSrc: require('../assets/images/bg5.jpeg'),
      rules: {
        title: [
          {required: true, message: '请输入标题', trigger: 'blur'},
          {min: 1, max: 25, message: '长度在 1 到 25 个字符', trigger: 'blur'}
        ],
        description: [
          {required: true, message: '请输入摘要', trigger: 'blur'}
        ],
        content: [
          {required: true, message: '请输入内容', trigger: 'blur'}
        ],
        is_stick: [
          {required: true, message: '请选择', trigger: 'blur'}
        ],
        weight_num: [
          {required: true, message: '请选择', trigger: 'blur'}
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {

          this.$axios.post('/blog/edit', this.ruleForm, {
            headers: {
              "Authorization": localStorage.getItem("token")
            }
          }).then(res => {
            //console.log(res);
            this.$alert('操作成功', '提示', {
              confirmButtonText: '确定',
              callback: action => {

                console.log("TEST: " + this.ruleForm.is_stick);

                this.$router.push("/blogs");
                this.$notify.success({
                  title: '添加成功',
                  message: '新的贴子已发表'
                });
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
            });
          })

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
      this.$router.push("/blogs");
    },
    change(val) {
      this.ruleForm.is_stick = val;
    },
    weight_change(val) {
      this.ruleForm.weight_num = val;
    }
  },
  created() {
    const blogId = this.$route.params.blogId;
    //console.log(blogId);

    try {
      if (this.$store.getters.getUser.username) {
        this.userInfo.name = this.$store.getters.getUser.name;
        this.userInfo.username = this.$store.getters.getUser.username;
        this.userInfo.avatar = this.$store.getters.getUser.avatar;

        this.ruleForm.author = this.userInfo.name;
      }
    } catch (e) {

    }

    if (blogId) {
      this.$axios.get('/blog/' + blogId).then(res => {

        //const blog = res.data.data;
        this.ruleForm.id = blog.id;
        this.ruleForm.title = blog.title;
        this.ruleForm.description = blog.description;
        this.ruleForm.content = blog.content;
        this.ruleForm.is_stick = blog.is_stick;
        this.ruleForm.weight_num = blog.weight_num;

      });
    }
  }
}
</script>

<style scoped>
.mContent {
  max-width: 960px;
  margin: 0 auto;
  text-align: center;
}

.background {
  width: 100%;
  height: 100%; /**宽高100%是为了图片铺满屏幕 */
  z-index: -1;
  position: absolute;
  background-color: rgba(190, 147, 245, 0.5);
}

.goBack {
  padding: 1%;
}
</style>