<script setup>
  import {useRoute, useRouter} from "vue-router";
  import axios from 'axios';
  import {onMounted, ref} from "vue";
  import api from "@/services/api.js";

  const route = useRoute();
  const router = useRouter();
  const patientId = route.params.id;

  const patient = ref({});

  const getPatient = async () => {
    console.log("patientId");
    console.log(patientId);

    try{
        const response = await api.get(`/api/patients/${patientId}`);

        patient.value.id = response.data.id;
        patient.value.name = response.data.name;
        patient.value.email = response.data.email;
        patient.value.dateOfBirth = response.data.dateOfBirth;
        patient.value.address = response.data.address;

    }catch (err) {
        console.log(err);
        router.push('/patients');
    }
  }

  onMounted(() => {
    getPatient();
  })
</script>

<template>
  <main v-if="patient.name" class="mx-auto max-w-4xl px-6 py-10">
    <div class="mb-8">
      <RouterLink to="/patients" class="text-sm text-indigo-600 hover:text-indigo-800">
        ← Back to patients
      </RouterLink>
      <p class="mt-6 text-sm font-semibold uppercase tracking-wider text-indigo-600">
        Patient details
      </p>
      <h1 class="mt-2 text-3xl font-bold text-slate-900">
        {{ patient.name }}
      </h1>
    </div>

    <section class="rounded-xl border border-slate-200 bg-white p-6 shadow-sm" aria-labelledby="patient-information">
      <h2 id="patient-information" class="mb-6 text-lg font-semibold text-slate-900">
        Personal information
      </h2>

      <dl class="grid gap-6 sm:grid-cols-2">
        <div>
          <dt class="text-sm font-medium text-slate-500">Name</dt>
          <dd class="mt-1 text-base text-slate-900">{{ patient.name }}</dd>
        </div>
        <div>
          <dt class="text-sm font-medium text-slate-500">Email address</dt>
          <dd class="mt-1 text-base text-slate-900">{{ patient.email }}</dd>
        </div>
        <div>
          <dt class="text-sm font-medium text-slate-500">Date of birth</dt>
          <dd class="mt-1 text-base text-slate-900">{{ patient.dateOfBirth }}</dd>
        </div>
        <div>
          <dt class="text-sm font-medium text-slate-500">Address</dt>
          <dd class="mt-1 text-base text-slate-900">{{ patient.address }}</dd>
        </div>
        <div class="sm:col-span-2">
          <dt class="text-sm font-medium text-slate-500">Patient ID</dt>
          <dd class="mt-1 break-all font-mono text-sm text-slate-700">{{ patient.id }}</dd>
        </div>
      </dl>
    </section>
  </main>
</template>
