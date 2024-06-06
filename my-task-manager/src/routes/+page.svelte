<script>
	import welcome_fallback from '$lib/images/svelte-welcome.png';
	import { PUBLIC_BACKEND_USER, PUBLIC_BACKEND_AUTH } from '$env/static/public';

	let showSignUpForm = false;
	let showSignInForm = false;
	let jwtToken = '';

	let signupData = {
		username: '',
		password: '',
		phone: '',
		mailId: ''
	};

	let signinData = {
		username: '',
		password: ''
	};

	async function handleSignUp() {
		const response = await fetch(`${PUBLIC_BACKEND_USER}`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(signupData)
		});

		if (response.ok) {
			alert('SignUp Successful');
			showSignUpForm = false;
		} else {
			alert('SignUp Failed');
		}
	}

	async function handleSignIn() {
		const response = await fetch(`${PUBLIC_BACKEND_AUTH}`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(signinData)
		});

		if (response.ok) {
			const data = await response.json();
			jwtToken = data.jwtToken;
			showSignInForm = false;
		} else {
			alert('SignIn Failed');
		}
	}

	function copyToClipboard() {
		navigator.clipboard.writeText(jwtToken).then(() => {
			alert('JWT Token copied to clipboard');
		}, () => {
			alert('Failed to copy JWT Token');
		});
	}

	function promptPasteToken(event) {
		event.preventDefault();
		const token = prompt('Please paste your JWT token to access the task manager:');
		if (token) {
			jwtToken = token;
			alert('JWT token Accepted');
			window.location.href = '/task-manager';
		} else {
			alert('No JWT token provided');
		}
	}
</script>

<svelte:head>
	<title>Home</title>
	<meta name="description" content="Svelte demo app" />
</svelte:head>

<section>
	<h1>
		<span class="welcome">
			<picture>
				<img src={welcome_fallback} alt="Welcome" />
			</picture>
		</span>
	</h1>
	<div class="auth-buttons">
		<button on:click={() => { showSignUpForm = !showSignUpForm; showSignInForm = false; }} class="auth-link">Sign Up</button>
		<button on:click={() => { showSignInForm = !showSignInForm; showSignUpForm = false; }} class="auth-link">Sign In</button>
	</div>
	{#if showSignUpForm}
		<form on:submit|preventDefault={handleSignUp} class="auth-form">
			<h2>Sign Up</h2>
			<label>
				Username:
				<input type="text" bind:value={signupData.username} required />
			</label>
			<label>
				Password:
				<input type="password" bind:value={signupData.password} required />
			</label>
			<label>
				Phone:
				<input type="text" bind:value={signupData.phone} required />
			</label>
			<label>
				Email:
				<input type="email" bind:value={signupData.mailId} required />
			</label>
			<button type="submit" class="auth-link">Sign Up</button>
		</form>
	{/if}
	{#if showSignInForm}
		<form on:submit|preventDefault={handleSignIn} class="auth-form">
			<h2>Sign In</h2>
			<label>
				Username:
				<input type="text" bind:value={signinData.username} required />
			</label>
			<label>
				Password:
				<input type="password" bind:value={signinData.password} required />
			</label>
			<button type="submit" class="auth-link">Sign In</button>
		</form>
	{/if}
	{#if jwtToken}
		<div class="token-display">
			<button on:click={copyToClipboard} class="auth-link">Copy JWT Token to Clipboard</button>
		</div>
	{/if}
	<div>
		<h2> </h2>
	</div>
	<a href="/task-manager" class="about-link" on:click={promptPasteToken}>Create your own To-Do list Task Manager</a>
</section>

<style>
	section {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		flex: 0.6;
	}

	h1 {
		width: 100%;
	}

	.welcome {
		display: block;
		position: relative;
		width: 100%;
		height: 0;
		padding: 0 0 calc(100% * 495 / 2048) 0;
	}

	.welcome img {
		position: absolute;
		width: 100%;
		height: 100%;
		top: 0;
		display: block;
	}

	.auth-buttons {
		display: flex;
		gap: 10px;
		margin-bottom: 20px;
	}

	.auth-link {
		font-size: 1.2rem;
		color: #0070f3;
		text-decoration: none;
		padding: 10px 20px;
		border: 1px solid #0070f3;
		border-radius: 5px;
		cursor: pointer;
		align-self: flex-start;
	}

	.auth-link:hover {
		background-color: #0070f3;
		color: #fff;
	}

	.auth-form {
		display: flex;
		flex-direction: column;
		gap: 10px;
		margin-bottom: 20px;
		width: 100%;
		max-width: 400px;
	}

	label {
		display: flex;
		flex-direction: column;
		font-size: 1rem;
		color: #333;
	}

	input {
		padding: 8px;
		font-size: 1rem;
		border: 1px solid #ccc;
		border-radius: 5px;
	}

	.about-link {
		font-size: 1.2rem;
		color: #0070f3;
		text-decoration: none;
	}

	.about-link:hover {
		text-decoration: underline;
	}

	.token-display {
		margin-top: 20px;
		text-align: center;
	}
</style>
