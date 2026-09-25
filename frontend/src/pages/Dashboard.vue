<script setup>
  import api from '@/services/api.js';
  import {onMounted, reactive} from "vue";

  const currentUser = reactive({
    id: '',
    email: '',
    role: ''
  })

  const getDashboard = async () => {
    const response = await api.get("/auth/me");

    console.log(response.data);

    currentUser.id = response.data.id;
    currentUser.email = response.data.email;
    currentUser.role = response.data.role;
  }

  onMounted(() => {
    getDashboard();
  })

</script>

<template>
  <main class="min-h-[calc(100vh-5rem)] bg-slate-50 px-6 py-10">
    <div class="mx-auto max-w-6xl">
      <section class="mb-8">
        <p class="text-sm font-semibold uppercase tracking-[0.18em] text-indigo-600">
          Patient Management
        </p>
        <h1 class="mt-2 text-3xl font-bold tracking-tight text-slate-900">
          Welcome to your dashboard
        </h1>
        <p class="mt-2 text-slate-500">
          Review your account information and manage your patient records.
        </p>
      </section>

      <section class="grid gap-6 lg:grid-cols-[1fr_1.4fr]" aria-label="Dashboard overview">
        <article class="rounded-2xl bg-gradient-to-br from-indigo-600 to-indigo-800 p-6 text-white shadow-lg">
          <div class="flex items-start justify-between gap-4">
            <div>
              <p class="text-sm font-medium text-indigo-100">Signed-in account</p>
              <h2 class="mt-3 text-2xl font-bold">Test User</h2>
              <p class="mt-1 text-indigo-100">{{ currentUser.email }}</p>
            </div>
            <span class="grid h-14 w-14 shrink-0 place-items-center rounded-full bg-white/15 text-xl font-bold">
              TU
            </span>
          </div>

          <div class="mt-8 border-t border-white/20 pt-5">
            <p class="text-xs font-semibold uppercase tracking-wider text-indigo-200">Role</p>
            <span class="mt-2 inline-flex rounded-full bg-emerald-400/20 px-3 py-1 text-sm font-semibold text-emerald-100">
              {{currentUser.role}}
            </span>
          </div>
        </article>

        <article class="rounded-2xl border border-slate-200 bg-white p-6 shadow-sm">
          <div class="flex items-center justify-between gap-4">
            <div>
              <p class="text-sm font-semibold uppercase tracking-wider text-indigo-600">Auth response</p>
              <h2 class="mt-2 text-xl font-semibold text-slate-900">Current user</h2>
            </div>
            <span class="rounded-lg bg-emerald-50 px-3 py-1 text-xs font-semibold text-emerald-700">
              Authenticated
            </span>
          </div>

          <dl class="mt-6 divide-y divide-slate-100">
            <div class="grid gap-1 py-3 sm:grid-cols-[7rem_1fr] sm:gap-4">
              <dt class="text-sm font-medium text-slate-500">ID</dt>
              <dd class="break-all font-mono text-sm text-slate-800">
                223e4567-e89b-12d3-a456-426614174006
              </dd>
            </div>
            <div class="grid gap-1 py-3 sm:grid-cols-[7rem_1fr] sm:gap-4">
              <dt class="text-sm font-medium text-slate-500">Email</dt>
              <dd class="text-sm text-slate-800">{{ currentUser.email }}</dd>
            </div>
            <div class="grid gap-1 py-3 sm:grid-cols-[7rem_1fr] sm:gap-4">
              <dt class="text-sm font-medium text-slate-500">Role</dt>
              <dd class="text-sm font-semibold text-indigo-700">{{ currentUser.role }}</dd>
            </div>
          </dl>
        </article>
      </section>

      <section class="mt-6 grid gap-6 md:grid-cols-3" aria-label="Quick actions">
        <RouterLink
          to="/patients"
          class="rounded-2xl border border-slate-200 bg-white p-5 shadow-sm transition hover:-translate-y-0.5 hover:border-indigo-200 hover:shadow-md"
        >
          <p class="text-sm font-semibold text-indigo-600">Patients</p>
          <h2 class="mt-2 text-lg font-semibold text-slate-900">View patient records</h2>
          <p class="mt-1 text-sm text-slate-500">Browse and manage registered patients.</p>
        </RouterLink>

        <div class="rounded-2xl border border-slate-200 bg-white p-5 shadow-sm">
          <p class="text-sm font-semibold text-indigo-600">Access level</p>
          <h2 class="mt-2 text-lg font-semibold text-slate-900">Administrator access</h2>
          <p class="mt-1 text-sm text-slate-500">Your account can access administrative features.</p>
        </div>

        <div class="rounded-2xl border border-slate-200 bg-white p-5 shadow-sm">
          <p class="text-sm font-semibold text-indigo-600">Session</p>
          <h2 class="mt-2 text-lg font-semibold text-slate-900">Securely signed in</h2>
          <p class="mt-1 text-sm text-slate-500">Your authenticated session is active.</p>
        </div>
      </section>
    </div>
  </main>
</template>