<script>
    export let user;
</script>
  
  <div class="user-profile">
    <h2>{user.name}</h2>
    <p>Email: {user.email}</p>
    <p>MbNo.: {user.mbno}</p>
  </div>
  
  <style>
    .user-profile {
      border: 1px solid #ccc;
      padding: 1em;
      margin: 1em 0;
    }
  </style>
  
  