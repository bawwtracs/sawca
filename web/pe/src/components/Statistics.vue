<template>
  <div id="statistics">
    <v-chart :options="sexualOptions" />
  </div>
</template>

<script>
export default {
  name: "Statistics",
  data() {
    return {
      sexualOptions: {
        tooltip: {},
        backgroundColor: "#fff",
        visualMap: {
          show: false,
          dimension: 2,
          min: 0,
          max: 30,
          inRange: {
            color: [
              "#313695",
              "#4575b4",
              "#74add1",
              "#abd9e9",
              "#e0f3f8",
              "#ffffbf",
              "#fee090",
              "#fdae61",
              "#f46d43",
              "#d73027",
              "#a50026",
            ],
          },
        },
        xAxis3D: {
          type: "value",
        },
        yAxis3D: {
          type: "value",
        },
        zAxis3D: {
          type: "value",
        },
        grid3D: {
          viewControl: {
            projection: "orthographic",
          },
        },
        series: [
          {
            type: "line3D",
            data: this.sexual(),
            lineStyle: {
              width: 4,
            },
          },
        ],
      },
    };
  },
  methods: {
    sexual() {
      let data = [];
      // Parametric curve
      for (var t = 0; t < 25; t += 0.001) {
        var x = 30 * Math.cos(t);
        var y = 30 * Math.sin(t);
        var z = t;
        data.push([x, y, z]);
      }
      return data;
    },
  },
  beforeCreate() {},
  created() {},
};
</script>

<style lang="less">
#statistics {
  height: 100vh;
  padding-bottom: 50px;
}

.echarts {
  width: 100%;
  height: 100%;
}
</style>
