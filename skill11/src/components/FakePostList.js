import React,{useState,useEffect} from "react";
import axios from "axios";

function FakePostList(){

const [posts,setPosts]=useState([])
const [userId,setUserId]=useState("")

const fetchPosts=()=>{
axios.get("https://dummyjson.com/posts")
.then(res=>{
setPosts(res.data.posts)
})
}

useEffect(()=>{
fetchPosts()
},[])

const filteredPosts=userId
? posts.filter(p=>p.userId===Number(userId))
: posts

return(

<div>

<h2>Fake API Posts</h2>

<select onChange={(e)=>setUserId(e.target.value)}>

<option value="">All Users</option>
<option value="1">User 1</option>
<option value="2">User 2</option>
<option value="3">User 3</option>

</select>

<button onClick={fetchPosts}>Refresh</button>

{filteredPosts.map(post=>(
<div key={post.id}>

<h4>{post.title}</h4>
<p>{post.body}</p>

</div>
))}

</div>

)

}

export default FakePostList