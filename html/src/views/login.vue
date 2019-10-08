<template lang="html">
  <div class="container">
    <header></header>
    <div class="main">
      <transition name="el-fade-in">
        <aside v-show="show" class="transition-box">
          <p class="title title-top">TEST SYSTEM</p>
          <article class="title title-bottom">
            这是一个测试的平台, 你可以做你想做的事情
          </article>
        </aside>
      </transition>
      <transition name="el-fade-in">
        <div v-show="show" class="transition-box container-login">
          <form ref="form">
            <p class="log-title">用户登录</p>
            <input type="text" class="log-input" placeholder="请输入你的账户" v-model="username" @keydown.enter="submit">
            <input type="text" class="log-input" placeholder="请输入你的密码" v-model="password" @keydown.enter="submit">
            <div class="err-line" >
              <transition name="el-fade-in">
                <span class="transition-box" v-show="error_show">
                  {{error_info}}
                </span>
              </transition>
            </div>
            <div class="line line-info">
              <el-checkbox v-model="passchecked">记住密码</el-checkbox>
              <span class="fr">忘记密码?</span>
            </div>
            <el-button type="primary submit" @click="submit">登录</el-button>
          </form>
        </div>
      </transition>
    </div>
    <footer></footer>
  </div>
</template>

<script>
import {
  login
} from '@/service/api'
// console.log(login)
export default {
  data() {
    return {
      show: false,
      passchecked: false,
      error_show: false,
      error_info: '',
      username: '',
      password: '',
      redirect: ''
    }
  },
  methods: {
    submit() {
      if (!this.username) {
        this.error_show = true
        this.error_info = '请填写用户名'
        return
      }
      if (!this.password) {
        this.error_show = true
        this.error_info = '请填写密码'
        return
      }
      login({
        userName: this.username,
        passWord: this.password
      }).then(data => {
        if (data.code == 0) {
          const login2page = () => {
            this.$router.push(this.redirect || '/home')
          }
          this.$store.commit({
            type: 'SAVE_TOKEN',
            loginuser: data.data,
            login2page
          })
        } else {
          this.error_show = true
          this.error_info = data.message
        }
      }).catch(err => {
        // new Error(err)
        console.error(err)
      })
    }
  },
  created() {
    setTimeout(() => {
      this.show = true
    }, 500)
  },
  watch: {
    $route: {
      handler(route) {
        this.redirect = route.query && route.query.redirect
        console.log(this.redirect)
      },
      immediate: true
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
    width: 100%;
    height: 100%;
    footer,
    header {
        width: 100%;
        height: 15%;
    }
    .main {
        width: 100%;
        height: 70%;
        background: linear-gradient(270deg,rgba(196,232,255,1) 0%,rgba(0,159,230,1) 100%);
        position: relative;
        .container-login {
            width: 400px;
            height: 420px;
            background-color: #fff;
            border-radius: 12px;
            position: absolute;
            top: 50%;
            left: 68%;
            margin-top: -225px;
            border: 1px solid #fff;
            padding: 15px 25px;
            .log-title {
                font-family: SourceHanSansCN-Medium;
                font-weight: 500;
                color: rgba(74,74,74,1);
                font-size: 30px;
            }
            .log-input {
                width: 380px;
                height: 40px;
                background: rgba(255,255,255,1);
                border-radius: 4px;
                border: 2px solid rgba(205,216,231,1);
                margin-top: 10px;
                padding: 0 10px;
            }
            .log-input + .log-input {
                margin-top: 30px;
            }
            .line-info {
                .fr {
                    color: #606266;
                    font-weight: 500;
                    font-size: 14px;
                    cursor: pointer;
                }
                .fr:active {
                    color: #409EFF;
                }
            }
            .submit {
                margin-top: 10px;
                width: 100%;
            }
        }
        aside {
            width: 50%;
            height: 100%;
            font-family: SourceHanSansCN-Medium;
        }
        .title {
            font-size: 45px;
            font-weight: 800;
            color: #fff;
            position: absolute;
            width: 50%;
            text-align: center;
        }
        .title-top {
            top: 25%;
        }
        .title-bottom {
            top: 55%;
            font-size: 30px;
            font-weight: 500;
        }
    }
}
</style>
