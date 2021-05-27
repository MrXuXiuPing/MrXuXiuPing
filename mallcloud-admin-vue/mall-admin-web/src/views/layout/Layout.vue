<template>
  <div class="app-wrapper" :class="classObj">
    <sidebar class="sidebar-container"></sidebar>
    <div class="main-container">
      <navbar></navbar>
      <app-main></app-main>
    </div>
  </div>
</template>

<script>
import { Navbar, Sidebar, AppMain,TagsView } from './components'
import ResizeMixin from './mixin/ResizeHandler'
import { mapState } from 'vuex';
export default {
  name: 'layout',
  components: {
    Navbar,
    Sidebar,
    AppMain,
	TagsView
  },
  mixins: [ResizeMixin],
  computed: {
	...mapState({
	  sidebar: state => state.app.sidebar,
	  device: state => state.app.device,
	  showSettings: state => state.settings.showSettings,
	  needTagsView: state => state.settings.tagsView,
	  fixedHeader: state => state.settings.fixedHeader
	}),  
    sidebar() {
      return this.$store.state.app.sidebar
    },
    device() {
      return this.$store.state.app.device
    },
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
		openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === 'mobile'
      }
    }
  },
  methods: {
    handleClickOutside() {
      this.$store.dispatch('app/closeSideBar', { withoutAnimation: false })
    }
  }
}
</script>

<style lang="scss" scoped>
  @import "src/styles/mixin.scss";
  @import "src/styles/variables.scss";
  .app-wrapper {
    @include clearfix;
    position: relative;
    height: 100%;
    width: 100%;
  }

</style>
