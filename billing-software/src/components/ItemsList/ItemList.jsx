import { useContext, useState } from "react";
import { AppContext } from "../../context/AppContext";
import { deleteItem } from "../../service/ItemService";
import './ItemList.css'
import toast from "react-hot-toast";

const ItemList = () =>
{
    const{itemsData,setItemsData} = useContext(AppContext);
    const [searchTerm,setSearchTerm] = useState("");
    const filteredItems = itemsData.filter((item)=>
        {
            return item.name.toLowerCase().includes(searchTerm.toLowerCase())
        }
);
console.log("filtered items=>",filteredItems);
console.log("Search Term",searchTerm);
    const removeItem = async (itemId) =>
    {
        try
        {
            const response = await deleteItem(itemId);
            if(response.status===204)
            {
                const updatedItems =itemsData.filter(item=>item.itemId!==itemId);
                setItemsData(updatedItems);
                toast.success("Item Deleted");
            }
            else
            {
                toast.error("Unable to Delete Item 👎👎")
            }
        }
        catch(error)
        {
            console.log(error);
            toast.error("Something Went Wrong👎👎👎");
        }
    }
    return (
        <div className="category-list-container" style={{height:'100vh',overflowX:'hidden',overflowY:'auto'}}>
        <div className="row pe-2">
            <div className="input-group mb-3">
                <input type="text"
                 id="keyword" 
                name="keyword" 
                placeholder="Search By Keyword"
                 className="form-control"
                 onChange={(e)=>setSearchTerm(e.target.value)}
                 value={searchTerm} />
                 <span className="input-group-text bg-warning">
                    <i className="bi bi-search"></i>
                 </span>
            </div>
        </div>
        <div className="row g-3 pe-2">
          {
          filteredItems.map((item,index)=>(
                <div className="col-12" key={index}>
                    <div className="card p-3 bg-dark">
                        <div className="d-flex align-items-center">
                            <div style={{marginRight:'15px'}} >
                                <img className="item-image" src={item.imgUrl} alt={item.name} width={50}/>
                            </div>
                            <div className="flex-grow-1">
                                <h6 className="mb-1 text-white">{item.name}</h6>
                                <p className="mb-0 text-white">
                                    Category: {item.categoryName}
                                </p>
                                <span className="mb-0 text-block badge rounded-pill text-bg-warning">
                                    &#8377;{item.price}
                                </span>
                            </div>
                            <div>
                                <button className="btn btn-danger btn-sm" onClick={()=>removeItem(item.itemId)}>
                                    <i className="bi bi-trash">
                                    </i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>                
          ))
         }
        </div>
    </div>
    )
}
export default ItemList;