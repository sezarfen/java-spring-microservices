import { createRouter, createWebHistory } from "vue-router";
import Login from '@/pages/Login.vue';
import Patients from "@/pages/Patients.vue";
import Patient from "@/pages/Patient.vue";
import Dashboard from "@/pages/Dashboard.vue";

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            name: 'default',
            redirect: '/login'
        },
        {
            path: '/login',
            name: 'login',
            component: Login,
            meta: {
                hideNavbar: true,
                requiresAuth: false
            }
        },
        {
            path: '/dashboard',
            name: 'dashboard',
            component: Dashboard,
            meta: {
                requiresAuth: true
            }
        },
        {
            path: '/patients',
            name: 'get-patients',
            component: Patients,
            meta: {
                requiresAuth: true
            }
        },
        {
            path: '/patients/:id',
            name: 'get-patient',
            component: Patient,
            meta: {
                requiresAuth: true
            }
        }
    ]
})

router.beforeEach((to) => {

    const token = localStorage.getItem('patient-management-token');

    if (to.meta.requiresAuth && !token) {
        return '/login'
    }

    return true;
});


export default router;