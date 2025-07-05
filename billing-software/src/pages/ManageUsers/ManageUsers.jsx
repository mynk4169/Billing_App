import './ManageUser.css'
import UserForm from '../../components/UserForm/UserForm';
import UsersList from '../../components/UsersList/UserList';
import { useState } from 'react';
import { useEffect } from 'react';
import { fetchUsers } from '../../service/UserService';
import toast from 'react-hot-toast';
const ManageUsers =()=>
{
    const [users,setUsers] = useState([]);
    const [loading,setLoading]=useState(false);
    useEffect(()=>
    {
        async function loadUsers()
       {
        try
        {
            setLoading(true);
            const response = await  fetchUsers();
            setUsers(response.data);
        }
        catch(error)
        {
            console.error(error);
            toast.error("Unable to fetch users");
        }
        finally
        {
            setLoading(false);
        }
    }
    loadUsers();
},[]);
    
    return (
        <div className="users-container text-light">
        <div className="left-column">
            <UserForm setUsers={setUsers}/>
        </div>
        <div className="right-column">
            <UsersList users = {users} setUsers={setUsers}/>
        </div>
    </div>
    )
}
export default ManageUsers;