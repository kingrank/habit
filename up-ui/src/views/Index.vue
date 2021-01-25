<template>
    <div class="dashboard-container">
        <component :is="'adminDashboard'"/>
    </div>
</template>

<script>
    import adminDashboard from '@/views/dashboard/admin'

    export default {
        name: 'Dashboard',
        components: {adminDashboard},
        data() {
            return {
                currentRole: 'adminDashboard'
            }
        },
        created() {

        }
    }
</script>
