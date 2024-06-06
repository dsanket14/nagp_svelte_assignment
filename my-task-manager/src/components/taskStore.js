import { writable } from 'svelte/store';

// Initialize tasks store with sample data
const initialTasks = [
    { title: "Read", description: "Read book", category: "Personal", dueDate: "03/06/2023", status: "Pending" },
    { title: "Travel", description: "Tour to Lonavala", category: "Family", dueDate: "07/06/2023", status: "Completed" },
    { title: "Work", description: "Complete project", category: "Office", dueDate: "04/06/2023", status: "In Progress" },
    { title: "Gym", description: "Workout session", category: "Health", dueDate: "05/06/2023", status: "In Progress" }
];

// Create writable store for tasks
export const tasks = writable(initialTasks);

// Methods to manipulate tasks
export function addTask(task) {
    tasks.update(currentTasks => [...currentTasks, task]);
}

export function updateTask(updatedTask) {
    tasks.update(currentTasks => {
        return currentTasks.map(task => {
            if (task.title === updatedTask.title) {
                return { ...task, ...updatedTask };
            }
            return task;
        });
    });
}

export function deleteTask(taskTitle) {
    tasks.update(currentTasks => currentTasks.filter(task => task.title !== taskTitle));
}
