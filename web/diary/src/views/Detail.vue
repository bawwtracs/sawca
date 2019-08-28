<template>
  <div class="detail">
    <h1>This is an detail {{id}} page</h1>
  </div>
</template>

<script>
export default {
  props: {
    id: {
      type: String,
      default: "id not found"
    }
  }
};
</script>