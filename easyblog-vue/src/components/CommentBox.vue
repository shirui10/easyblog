<template>
  <div>
    <a-comment>
      <a-avatar
          slot="avatar"
          :src="userInfo.avatar"
          :alt="userInfo.name"
      />
      <div slot="content" v-show="!isLogined">
        <a-form-item>
          <a-textarea :rows="4" :value="innerText" @change="handleChange" placeholder="登陆后才可评论噢～"/>
        </a-form-item>
        <a-form-item>
          <el-button html-type="submit" :loading="submitting" type="success" @click="goLogin">
            登陆
          </el-button>
        </a-form-item>
      </div>
      <div slot="content" v-show="isLogined">
        <a-form-item>
          <a-textarea :rows="4" :value="innerText" @change="handleChange" :placeholder="tip"/>
        </a-form-item>
        <a-form-item>
          <a-button html-type="submit" :loading="submitting" type="primary" @click="handleSubmit"
                    v-show="!mode&&!mode2">
            回复
          </a-button>
          <a-button html-type="submit" :loading="submitting" type="primary" @click="handleSubmitReply"
                    v-show="mode&&!mode2">
            回复
          </a-button>
          <a-button html-type="submit" :loading="submitting" type="primary" @click="handleSubmitReplySon"
                    v-show="mode&&mode2">
            回复
          </a-button>
        </a-form-item>
      </div>
    </a-comment>
  </div>
</template>
<script>
import moment from 'moment';

export default {
  inject: ["reload"],

  props: {
    value1: {
      type: Object
    },
    value2: {
      type: Object
    },
    value3: {
      type: Object
    },
    mode: {
      type: Boolean
    },
    mode2: {
      type: Boolean
    },
    tip: {
      type: String
    }
  },

  data() {
    return {
      userInfo: {
        username: 'traveller',
        name: '旅行者，你好',
        avatar: 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=4087125932,4288183110&fm=26&gp=0.jpg'
      },
      submitting: false,
      moment,
      innerText: '',
      isLogined: false
    };
  },
  methods: {
    handleSubmit() {
      if (!this.innerText) {
        return;
      }
      this.value1.content = this.innerText;
      this.value1.fromAvatar = this.userInfo.avatar;
      this.value1.fromId = this.userInfo.username;
      this.value1.fromName = this.userInfo.name;
      console.log("TEXT: " + this.innerText)
      this.$axios.post('/addDadComments', this.value1).then(res => {

        this.reload();
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
    },

    handleSubmitReply() {
      if (!this.innerText) {
        return;
      }
      this.value2.content = this.innerText;
      this.value2.fromAvatar = this.userInfo.avatar;
      this.value2.fromId = this.userInfo.username;
      this.value2.fromName = this.userInfo.name;
      console.log("TEXT: " + this.innerText)
      this.$axios.post('/addSonComments', this.value2).then(res => {

        this.reload();
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
    },

    handleSubmitReplySon() {
      if (!this.innerText) {
        return;
      }
      this.value3.content = '回复 @' + this.value3.fromName + ':  ' + this.innerText;
      this.value3.fromAvatar = this.userInfo.avatar;
      this.value3.fromId = this.userInfo.username;
      this.value3.fromName = this.userInfo.name;
      console.log("TEXT: " + this.value3.content)
      this.$axios.post('/addSonComments', this.value3).then(res => {

        this.reload();
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
    },

    handleChange(e) {
      this.innerText = e.target.value
    },

    goLogin() {
      this.$router.push('/login');
    }
  },
  created() {
    try {
      if (this.$store.getters.getUser.username) {
        this.userInfo.name = this.$store.getters.getUser.name;
        this.userInfo.username = this.$store.getters.getUser.username;
        this.userInfo.avatar = this.$store.getters.getUser.avatar;

        this.isLogined = true;
      }
    } catch (e) {

    }
  }
};
</script>