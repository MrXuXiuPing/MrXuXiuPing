<template>
  <section class="app-main">
    <transition name="fade" mode="out-in">
      <!-- <router-view :key="key"></router-view> -->
      <!-- <router-view></router-view> -->
	  <keep-alive :include="cachedViews">
	    <router-view :key="key" />
	  </keep-alive>
    </transition>
  </section>
</template>

<script>
export default {
  name: 'AppMain',
  computed: {
	  cachedViews() {
	    return this.$store.state.tagsView.cachedViews
	  },
	  key() {
	    return this.$route.path
	  }
    // key() {
    //   return this.$route.name !== undefined ? this.$route.name + +new Date() : this.$route + +new Date()
    // }
  }
}
</script>
