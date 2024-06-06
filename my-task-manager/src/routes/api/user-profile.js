import { PUBLIC_BACKEND_USER_PROFILE } from '$env/static/public';

export async function GET() {
    try {
        console.log('Fetching user profile from:', PUBLIC_BACKEND_USER_PROFILE);
        const response = await fetch(PUBLIC_BACKEND_USER_PROFILE, {
            method: 'GET'
        });

        if (response.ok) {
            const profileData = await response.json();
            console.log('User profile fetched successfully:', profileData);
            return {
                status: 200,
                body: profileData
            };
        } else {
            console.error('Failed to fetch user profile:', response.statusText);
            return {
                status: response.status,
                body: { error: 'Failed to fetch user profile' }
            };
        }
    } catch (error) {
        console.error('Error fetching user profile:', error);
        return {
            status: 500,
            body: { error: 'Error fetching user profile' }
        };
    }
}
