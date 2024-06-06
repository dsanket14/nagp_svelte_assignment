import { writable } from 'svelte/store';

// Initialize user store with sample data
const initialUser = {
    name: "Sanket Deshmukh",
    mbno: "7798892075",
    address:"145, Devicha mala, Sangamner, 422605",
    email: "dsanket014@gmail.com"
};

// Create writable store for user
export const user = writable(initialUser);

// Method to update user data
export function updateUser(updatedUserData) {
    user.update(currentUser => ({ ...currentUser, ...updatedUserData }));
}
