import { createRouter, createWebHistory } from "vue-router";
import Login from '@/components/Login.vue';
import Patients from "@/components/Patients.vue";

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
                hideNavbar: true
            }
        },
        {
            path: '/patients',
            name: 'get-patients',
            component: Patients
        }
    ]
})

export default router;