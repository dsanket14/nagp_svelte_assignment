<script>
  import { onMount } from 'svelte';
  export let task;

  let isEditing = false;
  let updatedTask = { ...task };

  async function handleUpdate() {
    try {
      const response = await fetch(`http://localhost:9090/api/task/update?title=${encodeURIComponent(task.title)}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedTask)
      });

      if (response.ok) {
        alert(`Task "${task.title}" updated successfully.`);
        console.log("Task updated successfully:", updatedTask);
        task = { ...updatedTask };  // Update the task with new values
        isEditing = false;  // Exit edit mode
      } else {
        alert(`Failed to update task "${task.title}".`);
        console.error("Failed to update task:", updatedTask);
      }
    } catch (error) {
      alert(`Error updating task "${task.title}".`);
      console.error("Error updating task:", error);
    }
  }

  async function handleDelete() {
    try {
      const response = await fetch(`http://localhost:9090/api/task/delete?title=${encodeURIComponent(task.title)}`, {
        method: 'DELETE'
      });

      if (response.ok) {
        alert(`Task "${task.title}" deleted successfully.`);
        console.log("Task deleted successfully:", task);
        location.reload();
      } else {
        alert(`Failed to delete task "${task.title}".`);
        console.error("Failed to delete task:", task);
      }
    } catch (error) {
      alert(`Error deleting task "${task.title}".`);
      console.error("Error deleting task:", error);
    }
  }
</script>

<div class="task-card">
  {#if isEditing}
    <form on:submit|preventDefault={handleUpdate}>
      <label>
        Title:
        <input type="text" bind:value={updatedTask.title} />
      </label>
      <label>
        Category:
        <select bind:value={updatedTask.category}>
          <option value="Personal">Personal</option>
          <option value="Office">Office</option>
          <option value="Family">Family</option>
          <option value="Health">Health</option>
        </select>
      </label>
      <label>
        Description:
        <textarea bind:value={updatedTask.description}></textarea>
      </label>
      <label>
        Due Date:
        <input type="date" bind:value={updatedTask.dueDate} />
      </label>
      <label>
        Status:
        <select bind:value={updatedTask.status}>
          <option value="Pending">Pending</option>
          <option value="InProgress">In Progress</option>
          <option value="Completed">Completed</option>
        </select>
      </label>
      <button type="submit">Save</button>
      <button type="button" on:click={() => isEditing = false}>Cancel</button>
    </form>
  {:else}
    <h3>{task.title}</h3>
    <p>{task.description}</p>
    <p>{task.category}</p>
    <p>Due: {task.dueDate}</p>
    <p>Status: {task.status}</p>

    <button on:click={() => isEditing = true}>Update</button>
    <button on:click={handleDelete}>Delete</button>
  {/if}
</div>

<style>
  .task-card {
    border: 1px solid #ee1919;
    padding: 1em;
    margin: 1em 0;
  }
  form {
    display: flex;
    flex-direction: column;
    gap: 1em;
  }
  label {
    display: flex;
    flex-direction: column;
  }
</style>
