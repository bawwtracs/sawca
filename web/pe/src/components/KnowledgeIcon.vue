<template>
  <div class="knowledge-icon" :class="color"></div>
</template>

<script>
export default {
  name: "KnowledgeIcon",
  props: ["color"],
  data() {
    return {};
  },
  methods: {},
  beforeCreate() {},
  created() {
    console.log(this.color);
  }
};
</script>

<style lang="less">
.knowledge-icon {
  width: 100%;
  padding-bottom: 125%;
  border-radius: 0 7px 7px 0;
  filter: blur(1px);
  &::after {
    content: "";
    position: absolute;
    right: 16%;
    width: 10px;
    height: 103%;
    transform: translateY(-1.5%);
  }
}

.dark {
  box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.1);
  &.knowledge-icon {
    background: linear-gradient(
      to right bottom,
      rgba(0, 0, 0, 0.7),
      rgba(0, 0, 0, 0.8)
    );
  }
  &.knowledge-icon::after {
    background: rgba(0, 0, 0, 0.9);
  }
}

.blue {
  box-shadow: 5px 5px 5px rgba(0, 128, 128, 0.1);
  &.knowledge-icon {
    background: linear-gradient(
      to right bottom,
      rgba(0, 128, 128, 0.7),
      rgba(0, 128, 128, 0.8)
    );
  }
  &.knowledge-icon::after {
    background: rgba(0, 128, 128, 0.9);
  }
}

.green {
  box-shadow: 5px 5px 5px rgba(46, 139, 87, 0.1);
  &.knowledge-icon {
    background: linear-gradient(
      to right bottom,
      rgba(46, 139, 87, 0.7),
      rgba(46, 139, 87, 0.8)
    );
  }
  &.knowledge-icon::after {
    background: rgba(46, 139, 87, 0.9);
  }
}
</style>
