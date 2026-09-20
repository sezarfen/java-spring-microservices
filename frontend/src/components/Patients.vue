<script setup>
  import {onMounted, ref} from "vue";
  import axios from "axios";
  import {useRouter} from "vue-router";

  const router = useRouter();

  const canPageLoaded = ref(false);
  const patients = ref([]);

  const getPatients = async () => {
    try{
      const token = localStorage.getItem("patient-management-token");
      const response = await axios.get("/api/patients", {
        headers: {
          Accept: 'application/json',
          Authorization: `Bearer ${token}`,
        }
      });

      patients.value = response.data;
      console.log(response.data);
      canPageLoaded.value = true;
    } catch (err) {
      console.log(err);
      router.push('/login')
    }
  }

  onMounted(() => {
    getPatients();
  })
</script>

<template>
  <main class="patients-page" v-if="canPageLoaded">
    <section class="page-heading">
      <div>
        <p class="eyebrow">Patient directory</p>
        <h1>Patients</h1>
        <p class="page-description">View and manage all registered patients in one place.</p>
      </div>
      <button class="add-button" type="button">
        <span aria-hidden="true">+</span>
        Add patient
      </button>
    </section>

    <section class="summary-grid" aria-label="Patient summary">
      <article class="summary-card">
        <span class="summary-icon summary-icon--blue" aria-hidden="true">
          <svg viewBox="0 0 24 24" fill="none">
            <path d="M16 20v-1.5a4 4 0 0 0-4-4H7a4 4 0 0 0-4 4V20M9.5 10.5a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7ZM17 11a3 3 0 1 0 0-6M17 14.5h1a4 4 0 0 1 4 4V20" />
          </svg>
        </span>
        <div>
          <p>Total patients</p>
          <strong>{{ patients.length }}</strong>
        </div>
      </article>
      <article class="summary-card">
        <span class="summary-icon summary-icon--green" aria-hidden="true">
          <svg viewBox="0 0 24 24" fill="none">
            <path d="M20 6 9 17l-5-5" />
          </svg>
        </span>
        <div>
          <p>Active records</p>
          <strong>{{ patients.length }}</strong>
        </div>
      </article>
      <article class="summary-card">
        <span class="summary-icon summary-icon--purple" aria-hidden="true">
          <svg viewBox="0 0 24 24" fill="none">
            <rect x="4" y="5" width="16" height="16" rx="2" />
            <path d="M8 3v4M16 3v4M4 10h16" />
          </svg>
        </span>
        <div>
          <p>Recently registered</p>
          <strong>{{ patients.length ? patients[0].name : "—" }}</strong>
        </div>
      </article>
    </section>

    <section class="patients-panel" aria-labelledby="patients-title">
      <div class="panel-header">
        <div>
          <h2 id="patients-title">All patients</h2>
          <p>{{ patients.length }} patient{{ patients.length === 1 ? "" : "s" }} found</p>
        </div>
        <div class="panel-filter">
          <svg viewBox="0 0 24 24" fill="none" aria-hidden="true">
            <path d="m21 21-4.35-4.35M10.8 18a7.2 7.2 0 1 1 0-14.4 7.2 7.2 0 0 1 0 14.4Z" />
          </svg>
          <span>Patient records</span>
        </div>
      </div>

      <div v-if="patients.length" class="patients-table-wrapper">
        <table class="patients-table">
          <thead>
            <tr>
              <th>Patient</th>
              <th>Email address</th>
              <th>Date of birth</th>
              <th>Address</th>
              <th><span class="sr-only">Actions</span></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="patient in patients" :key="patient.id">
              <td>
                <div class="patient-cell">
                  <span class="patient-avatar">{{ patient.name ? patient.name.charAt(0).toUpperCase() : "?" }}</span>
                  <div>
                    <strong>{{ patient.name || "Unnamed patient" }}</strong>
                    <small>ID: {{ patient.id ? patient.id.slice(0, 8) : "—" }}</small>
                  </div>
                </div>
              </td>
              <td class="muted-cell">{{ patient.email || "—" }}</td>
              <td class="muted-cell">{{ patient.dateOfBirth || "—" }}</td>
              <td class="muted-cell address-cell">{{ patient.address || "—" }}</td>
              <td class="action-cell">
                <button class="more-button" type="button" aria-label="Patient actions">•••</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <div v-else class="empty-state">
        <span class="empty-icon" aria-hidden="true">+</span>
        <h3>No patients yet</h3>
        <p>Registered patients will appear here.</p>
      </div>
    </section>
  </main>
</template>

<style scoped>
.patients-page {
  min-height: calc(100vh - 5rem);
  padding: 2.5rem clamp(1.25rem, 4vw, 4rem);
  background: #f7f8fc;
  color: #172033;
  font-family: Inter, ui-sans-serif, system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
}

.page-heading,
.panel-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1rem;
}

.page-heading {
  max-width: 81rem;
  margin: 0 auto 2rem;
}

.eyebrow {
  margin: 0 0 0.5rem;
  color: #6366f1;
  font-size: 0.72rem;
  font-weight: 800;
  letter-spacing: 0.14em;
  text-transform: uppercase;
}

h1,
h2,
h3,
p {
  margin-top: 0;
}

h1 {
  margin-bottom: 0.45rem;
  font-size: clamp(1.8rem, 4vw, 2.4rem);
  letter-spacing: -0.045em;
}

.page-description {
  margin-bottom: 0;
  color: #7b8496;
  font-size: 0.94rem;
}

.add-button {
  display: flex;
  align-items: center;
  gap: 0.55rem;
  padding: 0.78rem 1.1rem;
  border: 0;
  border-radius: 0.7rem;
  background: #4f46e5;
  color: white;
  cursor: pointer;
  font: inherit;
  font-size: 0.84rem;
  font-weight: 750;
  box-shadow: 0 0.55rem 1.2rem rgba(79, 70, 229, 0.2);
}

.add-button span {
  font-size: 1.25rem;
  font-weight: 400;
  line-height: 0.7;
}

.summary-grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 1rem;
  max-width: 81rem;
  margin: 0 auto 1.5rem;
}

.summary-card {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 1.2rem;
  border: 1px solid #edf0f5;
  border-radius: 1rem;
  background: white;
  box-shadow: 0 0.5rem 1.5rem rgba(31, 41, 74, 0.04);
}

.summary-icon {
  display: grid;
  flex: 0 0 auto;
  place-items: center;
  width: 2.7rem;
  height: 2.7rem;
  border-radius: 0.8rem;
}

.summary-icon svg {
  width: 1.3rem;
  height: 1.3rem;
  stroke: currentColor;
  stroke-linecap: round;
  stroke-linejoin: round;
  stroke-width: 1.7;
}

.summary-icon--blue { background: #eef2ff; color: #4f46e5; }
.summary-icon--green { background: #ecfdf3; color: #059669; }
.summary-icon--purple { background: #f5f3ff; color: #7c3aed; }

.summary-card p {
  margin-bottom: 0.2rem;
  color: #8992a3;
  font-size: 0.76rem;
}

.summary-card strong {
  color: #1e293b;
  font-size: 1.25rem;
}

.patients-panel {
  max-width: 81rem;
  margin: 0 auto;
  overflow: hidden;
  border: 1px solid #edf0f5;
  border-radius: 1.1rem;
  background: white;
  box-shadow: 0 0.6rem 2rem rgba(31, 41, 74, 0.05);
}

.panel-header {
  padding: 1.4rem 1.5rem;
  border-bottom: 1px solid #f0f2f6;
}

.panel-header h2 {
  margin-bottom: 0.35rem;
  font-size: 1.05rem;
  letter-spacing: -0.02em;
}

.panel-header p {
  margin-bottom: 0;
  color: #929aaa;
  font-size: 0.78rem;
}

.panel-filter {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.55rem 0.75rem;
  border: 1px solid #edf0f5;
  border-radius: 0.6rem;
  color: #98a2b3;
  font-size: 0.77rem;
}

.panel-filter svg {
  width: 1rem;
  height: 1rem;
  stroke: currentColor;
  stroke-linecap: round;
  stroke-width: 1.7;
}

.patients-table-wrapper {
  overflow-x: auto;
}

.patients-table {
  width: 100%;
  min-width: 45rem;
  border-collapse: collapse;
  text-align: left;
}

th {
  padding: 0.85rem 1.5rem;
  background: #fbfcfe;
  color: #8b95a7;
  font-size: 0.68rem;
  font-weight: 800;
  letter-spacing: 0.06em;
  text-transform: uppercase;
}

td {
  padding: 1rem 1.5rem;
  border-top: 1px solid #f1f3f7;
  font-size: 0.82rem;
  vertical-align: middle;
}

.patient-cell {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.patient-avatar {
  display: grid;
  flex: 0 0 auto;
  place-items: center;
  width: 2.35rem;
  height: 2.35rem;
  border-radius: 0.7rem;
  background: linear-gradient(135deg, #e0e7ff, #ccfbf1);
  color: #4f46e5;
  font-weight: 800;
}

.patient-cell strong,
.patient-cell small {
  display: block;
}

.patient-cell strong {
  margin-bottom: 0.25rem;
  color: #263246;
  font-size: 0.83rem;
}

.patient-cell small {
  color: #a2aab8;
  font-size: 0.68rem;
}

.muted-cell {
  color: #687386;
}

.address-cell {
  max-width: 13rem;
}

.action-cell {
  width: 2rem;
  text-align: right;
}

.more-button {
  border: 0;
  background: transparent;
  color: #98a2b3;
  cursor: pointer;
  letter-spacing: 0.12em;
}

.empty-state {
  padding: 4rem 1.5rem;
  text-align: center;
}

.empty-icon {
  display: inline-grid;
  place-items: center;
  width: 3rem;
  height: 3rem;
  margin-bottom: 1rem;
  border-radius: 50%;
  background: #eef2ff;
  color: #6366f1;
  font-size: 1.7rem;
}

.empty-state h3 {
  margin-bottom: 0.4rem;
  font-size: 1rem;
}

.empty-state p {
  margin-bottom: 0;
  color: #98a2b3;
  font-size: 0.82rem;
}

.sr-only {
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  white-space: nowrap;
  border: 0;
}

@media (max-width: 700px) {
  .patients-page {
    padding-top: 1.5rem;
  }

  .page-heading {
    align-items: flex-start;
    flex-direction: column;
  }

  .summary-grid {
    grid-template-columns: 1fr;
  }

  .panel-header {
    align-items: flex-start;
    flex-direction: column;
  }

  .panel-filter {
    width: 100%;
    box-sizing: border-box;
  }
}
</style>
