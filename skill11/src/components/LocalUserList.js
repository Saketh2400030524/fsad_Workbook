import React, { useState, useEffect } from "react";

function LocalUserList(){

const [users,setUsers]=useState([])
const [loading,setLoading]=useState(true)
const [error,setError]=useState(null)

useEffect(()=>{
fetch("/users.json")
.then(res=>res.json())
.then(data=>{
setUsers(data)
setLoading(false)
})
.catch(()=>{
setError("Error loading users")
setLoading(false)
})
},[])

if(loading) return <p>Loading...</p>
if(error) return <p>{error}</p>

return(
<div>
<h2>Local Users</h2>

<table border="1">
<thead>
<tr>
<th>Name</th>
<th>Email</th>
<th>Phone</th>
</tr>
</thead>

<tbody>
{users.map(user=>(
<tr key={user.id}>
<td>{user.name}</td>
<td>{user.email}</td>
<td>{user.phone}</td>
</tr>
))}
</tbody>

</table>
</div>
)
}

export default LocalUserList