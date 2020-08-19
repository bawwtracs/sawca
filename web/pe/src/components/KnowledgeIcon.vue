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
  created() {},
};
</script>

<style lang="less">
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
    height: 102.5%;
    transform: translateY(-1.25%);
  }
}

.dark {
  box-shadow: 5px 5px 5px rgba(35, 45, 52, 0.1);
  &.knowledge-icon {
    background: linear-gradient(
      to bottom,
      rgba(35, 45, 52, 0.75),
      rgba(35, 45, 52, 0.95)
    );
  }
  &.knowledge-icon::after {
    background: rgba(0, 0, 0, 0.9);
  }
}

.blue {
  box-shadow: 5px 5px 5px rgba(17, 104, 131, 0.1);
  &.knowledge-icon {
    background: linear-gradient(
      to bottom,
      rgba(17, 104, 131, 0.75),
      rgba(17, 104, 131, 0.95)
    );
  }
  &.knowledge-icon::after {
    background: rgba(25, 73, 99, 0.9);
  }
}

.green {
  box-shadow: 5px 5px 5px rgba(23, 139, 105, 0.1);
  &.knowledge-icon {
    background: linear-gradient(
      to bottom,
      rgba(23, 139, 105, 0.75),
      rgba(23, 139, 105, 0.95)
    );
  }
  &.knowledge-icon::after {
    background: rgba(18, 99, 76, 0.9);
  }
}

.purple {
  box-shadow: 5px 5px 5px rgba(67, 109, 108, 0.1);
  &.knowledge-icon {
    background: linear-gradient(
      to bottom,
      rgba(67, 109, 108, 0.75),
      rgba(67, 109, 108, 0.95)
    );
  }
  &.knowledge-icon::after {
    background: rgba(67, 109, 108, 0.9);
  }
}
</style>
