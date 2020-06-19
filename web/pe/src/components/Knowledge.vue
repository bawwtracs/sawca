<template>
  <div id="knowledge">
    <van-grid :gutter="16" :center="false" :border="false" :column-num="2">
      <van-grid-item>
        <knowledge-icon color="dark"></knowledge-icon>
        <div class="knowledge-class">Work</div>
        <div class="update-time">Update at 09:30</div>
      </van-grid-item>
      <van-grid-item>
        <knowledge-icon color="blue"></knowledge-icon>
        <div class="knowledge-class">Life</div>
        <div class="update-time">Update at 17:30</div>
      </van-grid-item>
      <van-grid-item>
        <knowledge-icon color="green"></knowledge-icon>
        <div class="knowledge-class">Study</div>
        <div class="update-time">Update at 23:45</div>
      </van-grid-item>
    </van-grid>
  </div>
</template>

<script>
import knowledgeIcon from "@/components/KnowledgeIcon.vue";
export default {
  name: "Knowledge",
  components: {
    knowledgeIcon
  },
  data() {
    return {};
  },
  methods: {},
  beforeCreate() {},
  created() {}
};
</script>

<style lang="less">
#knowledge {
  width: 100%;
  height: 100%;
  background-color: white;
}

.knowledge-class {
  margin-top: 5px;
  font-size: 1.1rem;
  text-align: left;
  font-weight: bold;
}
.update-time {
  font-size: 0.8rem;
  text-align: left;
  color: #bebebe;
}
</style>
