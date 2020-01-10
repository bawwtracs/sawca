<template>
  <div>
    <van-nav-bar
      title="Words"
      left-text="Back"
      left-arrow
      @click-left="onClickLeft"
      @click-right="onClickRight"
    >
      <van-icon name="plus" slot="right" />
    </van-nav-bar>
    <van-index-bar :index-list="indexAdapter">
      <template v-for="(indexItem, key) in wordAdapter">
        <van-index-anchor :key="key" :index="key" />
        <van-swipe-cell v-for="wordItem in wordAdapter[key]" :key="wordItem.id">
          <van-cell :border="false" :title="wordItem.spelling" @click="show(wordItem)" />
          <template slot="right">
            <van-button hairline size="small" type="danger" text="del" @click="del(wordItem)" />
          </template>
        </van-swipe-cell>
      </template>
    </van-index-bar>
    <van-popup v-model="showWord" round position="bottom" :style="{ height: '60%' }">
      <van-divider />
      {{spelling}} .{{lang}}
      <van-divider />
      {{representations}}
      <van-divider />
    </van-popup>
    <van-popup v-model="showEditor" position="bottom" :style="{ height: '80%' }">
      <van-cell-group>
        <div class="block-title">Lang</div>
        <van-field v-model="lang" input-align="center" placeholder="lang" />
        <div class="block-title">Spelling</div>
        <van-field v-model="spelling" input-align="center" placeholder="spelling" />
        <div class="block-title">Representations</div>
        <van-field
          v-model="representations"
          rows="2"
          autosize
          type="textarea"
          placeholder="representations"
        />
      </van-cell-group>
      <br />
      <van-button type="warning" plain @click="reset" hairline>Reset</van-button>
      <van-button
        type="primary"
        plain
        @click="submit"
        :disabled="submiting"
        :loading="submiting"
        hairline
      >Submit</van-button>
    </van-popup>
  </div>
</template>

<script>
import { Dialog } from "vant";
export default {
  name: "Word",
  props: {
    originId: {
      type: String,
      default: ""
    }
  },
  methods: {
    show(word) {
      this.showWord = true;
      this.spelling = word.spelling;
      this.representations = word.representations;
      this.lang = word.lang;
    },
    getWords() {
      this.axios
        .get(`${this.api.words()}?originId=${this.originId}`)
        .then(response => {
          this.words = response.data;
        });
    },
    del(word) {
      Dialog.confirm({
        title: "Sure?",
        message: `${word.spelling}`
      })
        .then(() => {
          this.axios.delete(this.api.origin(word.id)).then(response => {
            this.words = this.words.filter(obj => {
              return obj.id != response.data;
            });
          });
        })
        .catch(() => {
          // on cancel
        });
    },
    reset() {
      this.lang = "en";
      this.spelling = "";
      this.representations = "";
    },
    submit() {
      let lang = this.lang;
      let spelling = this.spelling;
      let representations = this.representations;
      let originId = this.originId;
      if (spelling && representations) {
        this.submiting = true;
        this.axios
          .post(this.api.word(), {
            spelling,
            representations,
            lang,
            originId
          })
          .then(response => {
            this.submiting = false;
            this.words.push(response.data);
            this.showEditor = false;
          });
      }
    },
    onClickLeft() {
      this.$router.go(-1);
    },
    onClickRight() {
      this.reset();
      this.showEditor = true;
    }
  },
  computed: {
    wordAdapter() {
      let letterSet = {};
      for (let i = 0; i < this.words.length; i++) {
        let firstLetter = this.words[i].spelling
          .charAt(0)
          .toString()
          .toUpperCase();
        if (!letterSet[firstLetter]) {
          letterSet[firstLetter] = [];
        }
        letterSet[firstLetter].push(this.words[i]);
      }
      return letterSet;
    },
    indexAdapter() {
      let letterSet = new Set();
      for (let i = 0; i < this.words.length; i++) {
        let firstLetter = this.words[i].spelling
          .charAt(0)
          .toString()
          .toUpperCase();
        letterSet.add(firstLetter);
      }
      return Array.from(letterSet);
    }
  },
  created() {
    this.getWords();
  },
  data() {
    return {
      lang: "en",
      spelling: "",
      representations: "",
      showEditor: false,
      showWord: false,
      words: [],
      submiting: false
    };
  }
};
</script>

<style>
.block-title {
  margin: 0;
  padding: 32px 16px 16px;
  background: #f7f8fa;
  color: rgba(69, 90, 100, 0.6);
  font-weight: normal;
  font-size: 14px;
  line-height: 16px;
}
</style>