<script>
  import { createEventDispatcher } from 'svelte';

  export let task = { title: '', description: '', category: '', dueDate: '', status: '' };

  let title = task.title;
  let description = task.description;
  let category = task.category;
  let dueDate = task.dueDate;
  let status = task.status;

  const dispatch = createEventDispatcher();

  async function submitForm() {
      const newTask = { title, description, category, dueDate, status };

      try {
          const response = await fetch('http://localhost:9090/api/task/add', {
              method: 'POST',
              headers: {
                  'Content-Type': 'application/json'
              },
              body: JSON.stringify(newTask)
          });

          if (response.ok) {
              const createdTask = await response.json();

              // Show alert message
              alert('Task saved successfully');

              // Clear the form fields
              title = '';
              description = '';
              category = '';
              dueDate = '';
              status = 'Pending';  // default to 'pending' or any other default status

              // Dispatch the taskAdded event
              dispatch('taskAdded', createdTask);
          } else {
              alert('Failed to save task');
          }
      } catch (error) {
          console.error('Error:', error);
          alert('An error occurred while saving the task');
      }
  }
</script>

<form on:submit|preventDefault={submitForm}>
  <div class="form-row">
      <label>
          Title:
          <input type="text" bind:value={title} />
      </label>
      <label>
        Category:
        <select bind:value={category}>
          <option value="Personal">Personal</option>
          <option value="Office">Office</option>
          <option value="Family">Family</option>
          <option value="Health">Health</option>
        </select>
      </label>
      <label>
        Description:
        <textarea bind:value={description}></textarea>
      </label>
      <label>
          Due Date:
          <input type="date" bind:value={dueDate} />
      </label>
      <label>
          Status:
          <select bind:value={status}>
              <option value="Pending">Pending</option>
              <option value="InProgress">In Progress</option>
              <option value="Completed">Completed</option>
          </select>
      </label>
  </div>
  <button type="submit">Save Task</button>
</form>

<style>
  form {
      display: flex;
      flex-direction: column;
      align-items: center;
      max-width: 600px;
      margin: auto;
  }
  .form-row {
      display: flex;
      justify-content: space-between;
      width: 100%;
      gap: 1em; /* Adjust the gap value as needed */
      margin-bottom: 1em;
  }
  label {
      display: flex;
      flex-direction: column;
      width: calc(25% - 0.75em); /* Adjust width to account for gap */
  }
  button {
      margin-top: 1em;
  }
</style>
