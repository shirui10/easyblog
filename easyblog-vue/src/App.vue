<template>
  <div id="app">
    <router-view v-if="active"></router-view>
  </div>
</template>

<script>
export default {
  name: 'App',
  provide() {
    return {
      reload: this.reload
    }
  },
  data() {
    return {
      active: true
    }
  },
  methods: {
    reload() {
      this.active = false
      this.$nextTick(function () {
        this.active = true
      })
    }
  },
}
</script>