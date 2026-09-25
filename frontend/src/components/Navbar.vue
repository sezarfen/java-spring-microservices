<script setup>

import {HomeIcon, UsersIcon, CalendarDaysIcon, EyeDropperIcon, FolderMinusIcon} from "@heroicons/vue/24/solid";
import {onMounted, ref} from "vue";
import {useRouter, useRoute} from "vue-router";

defineProps({
    title: String,
    smallTitle: String
  });

  const route = useRoute();
  const router = useRouter();
  const isProfileMenuOpen = ref(false);
  const currentPath = route.path;

  const logout = () => {
    localStorage.removeItem("patient-management-token");
    isProfileMenuOpen.value = false;
    router.push("/login");
  };

  const navbarItems = [
    {
      name: 'Dashboard',
      icon: HomeIcon,
      path: '/dashboard'
    },
    {
      name: 'Hastalar',
      icon: UsersIcon,
      path: '/patients'
    },
    {
      name: 'Randevular',
      icon: CalendarDaysIcon,
      path: '/appointments'
    },
    {
      name: "Doktorlar",
      icon: EyeDropperIcon,
      path: '/doctors'
    },
    {
      name: "Raporlar",
      icon: FolderMinusIcon,
      path: '/raports'
    }

  ]

</script>

<template>
  <header class="navbar">
    <div class="navbar__inner">
      <a class="brand" href="#" aria-label="Patient Management ana sayfa">
        <span class="brand__mark" aria-hidden="true">
          <svg viewBox="0 0 24 24" fill="none">
            <path d="M12 21s-7-4.35-9.2-8.55C.87 8.94 2.78 5 6.44 5c2.07 0 3.63 1.18 4.56 2.65C11.93 6.18 13.49 5 15.56 5c3.66 0 5.57 3.94 3.64 7.45C19 16.65 12 21 12 21Z" fill="currentColor"/>
            <path d="M12 8v6M9 11h6" stroke="white" stroke-width="1.7" stroke-linecap="round"/>
          </svg>
        </span>
        <span class="brand__text">
          <strong>{{ title }}</strong>
          <small>{{ smallTitle }}</small>
        </span>
      </a>
      <nav class="nav-links" aria-label="Ana Navigasyon">
        <RouterLink
            v-bind:class='["nav-link", item.path === currentPath ? "nav-link--active" : ""]'
            :to="item.path"
            v-for="item in navbarItems">

          <component :is="item.icon" />
          <span> {{item.name}}</span>

        </RouterLink>
      </nav>

      <div class="navbar__actions">
        <button class="icon-button" type="button" aria-label="Bildirimler">
          <svg viewBox="0 0 24 24" fill="none" aria-hidden="true">
            <path d="M18 9a6 6 0 0 0-12 0c0 7-3 7-3 9h18c0-2-3-2-3-9ZM10 21h4" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
          <span class="notification-dot"></span>
        </button>
        <span class="action-divider" aria-hidden="true"></span>
        <div class="profile-menu">
        <button
          class="profile"
          type="button"
          :aria-expanded="isProfileMenuOpen"
          aria-haspopup="menu"
          @click="isProfileMenuOpen = !isProfileMenuOpen"
        >
          <span class="profile__avatar">AD</span>
          <span class="profile__info">
            <strong>Admin User</strong>
            <small>Yönetici</small>
          </span>
          <svg class="profile__chevron" viewBox="0 0 24 24" fill="none" aria-hidden="true">
            <path d="m7 10 5 5 5-5" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </button>
        <div v-if="isProfileMenuOpen" class="profile-dropdown" role="menu">
          <div class="dropdown-heading">
            <span class="profile__avatar profile__avatar--small">AD</span>
            <div>
              <strong>Admin User</strong>
              <small>Yönetici hesabı</small>
            </div>
          </div>
          <span class="dropdown-divider" aria-hidden="true"></span>
          <button class="logout-button" type="button" role="menuitem" @click="logout">
            <svg viewBox="0 0 24 24" fill="none" aria-hidden="true">
              <path d="M10 5H5v14h5M15 8l4 4-4 4M19 12H9" />
            </svg>
            <span>Logout</span>
          </button>
        </div>
        </div>
      </div>
    </div>
  </header>
</template>

<style scoped>
.navbar {
  width: 100%;
  background: #ffffff;
  border-bottom: 1px solid #e8edf3;
  color: #25324a;
}

.navbar__inner {
  display: flex;
  align-items: center;
  gap: 42px;
  max-width: 1440px;
  min-height: 76px;
  margin: 0 auto;
  padding: 0 32px;
}

.brand,
.nav-link,
.profile {
  text-decoration: none;
}

.brand {
  display: inline-flex;
  align-items: center;
  flex-shrink: 0;
  gap: 10px;
  color: #24324a;
}

.brand__mark {
  display: grid;
  width: 38px;
  height: 38px;
  place-items: center;
  border-radius: 11px;
  background: #2f80ed;
  color: #ffffff;
  box-shadow: 0 7px 16px rgba(47, 128, 237, 0.2);
}

.brand__mark svg {
  width: 25px;
  height: 25px;
}

.brand__text {
  display: flex;
  flex-direction: column;
  line-height: 1.05;
}

.brand__text strong {
  font-size: 17px;
  letter-spacing: -0.3px;
}

.brand__text small {
  margin-top: 4px;
  color: #8390a5;
  font-size: 10px;
  font-weight: 600;
  letter-spacing: 1.6px;
  text-transform: uppercase;
}

.nav-links {
  display: flex;
  align-items: stretch;
  align-self: stretch;
  gap: 8px;
}

.nav-link {
  position: relative;
  display: inline-flex;
  align-items: center;
  gap: 9px;
  padding: 3px 13px 0;
  color: #7b879b;
  font-size: 13px;
  font-weight: 600;
  transition: color 180ms ease;
}

.nav-link::after {
  position: absolute;
  right: 13px;
  bottom: 0;
  left: 13px;
  height: 3px;
  border-radius: 3px 3px 0 0;
  background: transparent;
  content: '';
}

.nav-link:hover,
.nav-link--active {
  color: #2f80ed;
}

.nav-link--active::after {
  background: #2f80ed;
}

.nav-link svg {
  width: 19px;
  height: 19px;
}

.navbar__actions {
  display: flex;
  align-items: center;
  gap: 20px;
  margin-left: auto;
}

.icon-button {
  position: relative;
  display: grid;
  width: 34px;
  height: 34px;
  padding: 0;
  place-items: center;
  border: 0;
  border-radius: 9px;
  background: transparent;
  color: #738198;
  cursor: pointer;
}

.icon-button:hover {
  background: #f2f6fb;
  color: #2f80ed;
}

.icon-button svg {
  width: 20px;
  height: 20px;
}

.notification-dot {
  position: absolute;
  top: 6px;
  right: 5px;
  width: 6px;
  height: 6px;
  border: 1.5px solid #ffffff;
  border-radius: 50%;
  background: #f26b6b;
}

.action-divider {
  width: 1px;
  height: 30px;
  background: #e8edf3;
}

.profile {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 0;
  border: 0;
  background: transparent;
  color: #25324a;
  cursor: pointer;
  font: inherit;
  text-align: left;
}

.profile__avatar {
  display: grid;
  width: 36px;
  height: 36px;
  place-items: center;
  border-radius: 50%;
  background: #e5f1ff;
  color: #2f80ed;
  font-size: 11px;
  font-weight: 700;
}

.profile__info {
  display: flex;
  flex-direction: column;
  line-height: 1.2;
}

.profile__info strong {
  font-size: 12px;
}

.profile__info small {
  margin-top: 3px;
  color: #8a96a9;
  font-size: 10px;
}

.profile__chevron {
  width: 17px;
  height: 17px;
  margin-left: 4px;
  color: #8a96a9;
  transition: transform 180ms ease;
}

.profile[aria-expanded="true"] .profile__chevron {
  transform: rotate(180deg);
}

.profile-menu {
  position: relative;
}

.profile-dropdown {
  position: absolute;
  z-index: 10;
  top: calc(100% + 14px);
  right: 0;
  width: 220px;
  padding: 0.7rem;
  border: 1px solid #e8edf3;
  border-radius: 0.9rem;
  background: #ffffff;
  box-shadow: 0 1rem 2.5rem rgba(37, 50, 74, 0.14);
}

.dropdown-heading {
  display: flex;
  align-items: center;
  gap: 0.65rem;
  padding: 0.35rem 0.35rem 0.65rem;
}

.profile__avatar--small {
  width: 31px;
  height: 31px;
  font-size: 10px;
}

.dropdown-heading strong,
.dropdown-heading small {
  display: block;
}

.dropdown-heading strong {
  color: #25324a;
  font-size: 12px;
}

.dropdown-heading small {
  margin-top: 3px;
  color: #8a96a9;
  font-size: 10px;
}

.dropdown-divider {
  display: block;
  height: 1px;
  margin: 0.15rem 0 0.45rem;
  background: #edf0f4;
}

.logout-button {
  display: flex;
  align-items: center;
  gap: 0.65rem;
  width: 100%;
  padding: 0.65rem 0.5rem;
  border: 0;
  border-radius: 0.55rem;
  background: transparent;
  color: #d04f5f;
  cursor: pointer;
  font: inherit;
  font-size: 12px;
  font-weight: 650;
  text-align: left;
}

.logout-button:hover {
  background: #fff1f2;
}

.logout-button svg {
  width: 17px;
  height: 17px;
  stroke: currentColor;
  stroke-linecap: round;
  stroke-linejoin: round;
  stroke-width: 1.7;
}

@media (max-width: 1100px) {
  .navbar__inner {
    gap: 20px;
    padding: 0 20px;
  }

  .nav-link {
    padding-right: 8px;
    padding-left: 8px;
  }

  .nav-link::after {
    right: 8px;
    left: 8px;
  }

  .nav-link span {
    display: none;
  }
}

@media (max-width: 700px) {
  .navbar__inner {
    min-height: 68px;
    padding: 0 16px;
  }

  .brand__text,
  .profile__info,
  .profile__chevron,
  .action-divider {
    display: none;
  }

  .nav-links {
    gap: 2px;
  }

  .nav-link {
    padding: 3px 9px 0;
  }

  .nav-link::after {
    right: 9px;
    left: 9px;
  }

  .navbar__actions {
    gap: 6px;
  }
}
</style>