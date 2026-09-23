<script setup>
  import api from "@/services/api.js";
  import {onMounted, reactive, ref} from "vue";
  import {useRouter} from "vue-router";

  const router = useRouter();
  const finalMessage = ref('');
  const form = reactive({
    email: '',
    password: '',
  });

  const login = async () => {
    try {

      const response = await api.post("/auth/login",
          {
            'email': form.email,
            'password': form.password
          });

      const token = response.data.token;
      localStorage.setItem("patient-management-token", token);
      finalMessage.value = 'Logged in successfully! Redirecting..';
      router.push('/patients');
    } catch (err){
      console.log("Error login", err);
      finalMessage.value = 'Error logging in';
    }
  }

  /*
  onMounted(() => {
    login();
  })
 */
</script>

<template>
  <main class="login-page">
    <section class="login-card" aria-labelledby="login-title">
      <div class="login-intro">
        <div class="brand-mark" aria-hidden="true">
          <span></span>
          <span></span>
          <span></span>
        </div>
        <p class="eyebrow">Patient Management</p>
        <h1 id="login-title">Welcome back</h1>
        <p class="subtitle">Sign in to continue managing your patients.</p>
      </div>

      <form class="login-form" @submit.prevent="login">
        <div class="form-field">
          <label for="email">Email address</label>
          <div class="input-wrapper">
            <svg viewBox="0 0 24 24" aria-hidden="true">
              <path d="M4 5.5h16v13H4zM4 6l8 6 8-6" />
            </svg>
            <input
              id="email"
              v-model="form.email"
              type="email"
              name="email"
              placeholder="you@example.com"
              autocomplete="email"
              required
            />
          </div>
        </div>

        <div class="form-field">
          <label for="password">Password</label>
          <div class="input-wrapper">
            <svg viewBox="0 0 24 24" aria-hidden="true">
              <rect x="5" y="10" width="14" height="10" rx="2" />
              <path d="M8 10V7a4 4 0 0 1 8 0v3" />
            </svg>
            <input
              id="password"
              v-model="form.password"
              type="password"
              name="password"
              placeholder="Enter your password"
              autocomplete="current-password"
              required
            />
          </div>
        </div>

        <button type="submit" class="submit-button">
          Sign in
          <span aria-hidden="true">→</span>
        </button>
      </form>

      <p v-if="finalMessage" class="status-message" :class="{ success: finalMessage.includes('Successfully') }">
        {{ finalMessage }}
      </p>
    </section>
  </main>
</template>

<style scoped>
.login-page {
  min-height: 100vh;
  display: grid;
  place-items: center;
  padding: 2rem 1.25rem;
  background:
    radial-gradient(circle at 10% 10%, rgba(129, 140, 248, 0.3), transparent 30%),
    radial-gradient(circle at 90% 90%, rgba(45, 212, 191, 0.2), transparent 28%),
    #f5f7fb;
  font-family: Inter, ui-sans-serif, system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
}

.login-card {
  width: min(100%, 27rem);
  padding: 2.75rem;
  border: 1px solid rgba(255, 255, 255, 0.8);
  border-radius: 1.5rem;
  background: rgba(255, 255, 255, 0.88);
  box-shadow: 0 1.5rem 4rem rgba(31, 41, 74, 0.12);
  backdrop-filter: blur(16px);
}

.login-intro {
  margin-bottom: 2rem;
}

.brand-mark {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.2rem;
  width: 3.25rem;
  height: 3.25rem;
  margin-bottom: 1.5rem;
  border-radius: 1rem;
  background: linear-gradient(135deg, #6366f1, #14b8a6);
  box-shadow: 0 0.75rem 1.5rem rgba(99, 102, 241, 0.25);
}

.brand-mark span {
  width: 0.22rem;
  border-radius: 999px;
  background: white;
}

.brand-mark span:nth-child(1) { height: 0.9rem; }
.brand-mark span:nth-child(2) { height: 1.45rem; }
.brand-mark span:nth-child(3) { height: 1.1rem; }

.eyebrow {
  margin: 0 0 0.5rem;
  color: #6366f1;
  font-size: 0.72rem;
  font-weight: 800;
  letter-spacing: 0.13em;
  text-transform: uppercase;
}

h1 {
  margin: 0;
  color: #172033;
  font-size: clamp(1.8rem, 5vw, 2.25rem);
  letter-spacing: -0.04em;
}

.subtitle {
  margin: 0.75rem 0 0;
  color: #718096;
  font-size: 0.95rem;
  line-height: 1.5;
}

.login-form {
  display: grid;
  gap: 1.25rem;
}

.form-field {
  display: grid;
  gap: 0.55rem;
}

label {
  color: #344054;
  font-size: 0.82rem;
  font-weight: 700;
}

.input-wrapper {
  position: relative;
}

.input-wrapper svg {
  position: absolute;
  top: 50%;
  left: 1rem;
  width: 1.15rem;
  height: 1.15rem;
  fill: none;
  stroke: #98a2b3;
  stroke-linecap: round;
  stroke-linejoin: round;
  stroke-width: 1.6;
  transform: translateY(-50%);
  pointer-events: none;
}

input {
  width: 100%;
  box-sizing: border-box;
  padding: 0.85rem 1rem 0.85rem 2.85rem;
  border: 1px solid #e4e7ec;
  border-radius: 0.8rem;
  outline: none;
  background: #fafbfc;
  color: #172033;
  font: inherit;
  font-size: 0.9rem;
  transition: border-color 160ms ease, box-shadow 160ms ease, background 160ms ease;
}

input::placeholder {
  color: #b0b8c5;
}

input:focus {
  border-color: #818cf8;
  background: white;
  box-shadow: 0 0 0 4px rgba(99, 102, 241, 0.12);
}

.submit-button {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.65rem;
  margin-top: 0.5rem;
  padding: 0.9rem 1.25rem;
  border: 0;
  border-radius: 0.8rem;
  background: linear-gradient(135deg, #6366f1, #4f46e5);
  color: white;
  cursor: pointer;
  font: inherit;
  font-weight: 750;
  box-shadow: 0 0.7rem 1.4rem rgba(79, 70, 229, 0.23);
  transition: transform 160ms ease, box-shadow 160ms ease;
}

.submit-button span {
  font-size: 1.2rem;
  line-height: 0;
  transition: transform 160ms ease;
}

.submit-button:hover {
  box-shadow: 0 0.9rem 1.7rem rgba(79, 70, 229, 0.3);
  transform: translateY(-1px);
}

.submit-button:hover span {
  transform: translateX(3px);
}

.submit-button:focus-visible {
  outline: 3px solid rgba(99, 102, 241, 0.3);
  outline-offset: 3px;
}

.status-message {
  margin: 1.25rem 0 0;
  padding: 0.75rem 1rem;
  border-radius: 0.7rem;
  background: #fff1f2;
  color: #be123c;
  font-size: 0.85rem;
  text-align: center;
}

.status-message.success {
  background: #ecfdf3;
  color: #047857;
}

@media (max-width: 480px) {
  .login-card {
    padding: 2rem 1.35rem;
    border-radius: 1.25rem;
  }
}
</style>
