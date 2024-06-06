<script>
    import TaskCard from "../../components/TaskCard.svelte";
    import TaskForm from "../../components/TaskForm.svelte";
    import UserProfile from "../../components/UserProfile.svelte";
    
    // Task data will be fetched from API
    let tasks = [];
    let user = {
        name: "",
        mbno: "",
        email: ""
    };

    // State to control visibility of components
    let visibleComponent = '';
    let selectedCategory = '';
    let selectedStatus = '';

    async function showTaskForm() {
        visibleComponent = 'taskForm';
    }

    async function showUserProfile() {
        visibleComponent = 'userProfile';
        await fetchUserProfile();
    }

    async function showTaskCard() {
        visibleComponent = 'taskCard';
        await fetchTasks();
    }

    async function showCategoryFilter() {
        visibleComponent = 'categoryFilter';
    }

    async function showStatusWise() {
        visibleComponent = 'statusFilter';
    }

    function selectCategory(category) {
        selectedCategory = category;
        visibleComponent = 'categoryTasks';
    }

    function selectStatus(status) {
        selectedStatus = status;
        visibleComponent = 'statusTasks';
    }

    function getFilteredTasks() {
        return tasks.filter(task => task.category === selectedCategory);
    }

    function getFilteredTasksStausWise() {
        return tasks.filter(task => task.status === selectedStatus);
    }

    async function fetchTasks() {
        try {
            const response = await fetch('http://localhost:9090/api/task', {
                method: 'GET'
            });

            if (response.ok) {
                tasks = await response.json();
            } else {
                console.error('Failed to fetch tasks:', response.statusText);
            }
        } catch (error) {
            console.error('Error fetching tasks:', error);
        }
    }

    async function fetchUserProfile() {
        try {
            const response = await fetch('http://localhost:9090/api/users/profile', {
                method: 'GET'
            });

            if (response.ok) {
                const profileData = await response.json();
                user.name = profileData.userName;
                user.mbno = profileData.phone;
                user.email = profileData.mailId;
            } else {
                console.error('Failed to fetch user profile:', response.statusText);
            }
        } catch (error) {
            console.error('Error fetching user profile:', error);
        }
    }
</script>

<style>
    .container {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        height: 100vh;
    }

    .buttons {
        display: flex;
        gap: 10px;
        margin-bottom: 20px;
    }

    .text-column {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
        gap: 20px;
        padding: 20px;
    }

    @media (max-width: 600px) {
        .text-column {
            grid-template-columns: 1fr;
        }
    }
</style>

<div class="container">
    <div class="buttons">
        <button on:click={showUserProfile}>User Profile</button>
        <button on:click={showTaskForm}>Create Task</button>
        <button on:click={showTaskCard}>Show Task Card</button>
        <button on:click={showCategoryFilter}>Show Category Wise</button>
        <button on:click={showStatusWise}>Show Status Wise</button>
    </div>

    <div>
        {#if visibleComponent === 'taskForm'}
            <TaskForm />
        {/if}
        {#if visibleComponent === 'userProfile'}
            <UserProfile user={user} />
        {/if}
        {#if visibleComponent === 'categoryFilter'}
            <div>
                <button on:click={() => selectCategory('Personal')}>Personal</button>
                <button on:click={() => selectCategory('Family')}>Family</button>
                <button on:click={() => selectCategory('Health')}>Health</button>
                <button on:click={() => selectCategory('Office')}>Office</button>
            </div>
        {/if}
        {#if visibleComponent === 'statusFilter'}
            <div>
                <button on:click={() => selectStatus('Completed')}>Completed</button>
                <button on:click={() => selectStatus('InProgress')}>InProgress</button>
                <button on:click={() => selectStatus('Pending')}>Pending</button>
            </div>
        {/if}
    </div>
    <div class="text-column">
        {#if visibleComponent === 'taskCard'}
            {#each tasks as task}
                <TaskCard task={task} />
            {/each}
        {/if}
        {#if visibleComponent === 'categoryTasks'}
            {#each getFilteredTasks() as task}
                <TaskCard task={task} />
            {/each}
        {/if}
        {#if visibleComponent === 'statusTasks'}
            {#each getFilteredTasksStausWise() as task}
                <TaskCard task={task} />
            {/each}
        {/if}
    </div>
</div>
