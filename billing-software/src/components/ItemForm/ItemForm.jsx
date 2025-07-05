import { useContext, useState } from "react";

import { AppContext } from "../../context/AppContext";
import { assets } from "../../assets/assets";
import { addItem } from "../../service/ItemService";
import toast from "react-hot-toast";



const ItemForm = ()=>
{
    
    const [image,setImage] = useState(false);
    const [loading,setLoading] = useState(false);
    const {categories,setCategories,setItemsData,itemsData} = useContext(AppContext);
    const [data,setData] = useState(
        {
            name:"",
            categoryId:"",
            price:"",
            description:"",
        }
    );
    const onChangeHandler =  (e) =>
    {
        const name = e.target.name;
        const value = e.target.value;
        setData((data)=>({...data,[name]:value}));
 
        
    }
    const onSubmitHandler = async (e)=>
    {
        e.preventDefault();
        setLoading(true);
        const formData = new FormData();
        formData.append('item',JSON.stringify(data));
        formData.append('file',image);
        try
        {
            setLoading(true)
            if(!image)
            {
                toast.error("Select Image");
                return;
            }
            const response = await addItem(formData);
            console.log(data);
            if(response.status===201)
            {
                setItemsData([...itemsData,response.data]);
                setCategories(
                    (prevCategories)=> 
                        prevCategories.map((category)=>category.categoryId===data.categoryId?{...category,items:category.items+1}:category));
                //TODO :update the cateogry status.
                toast.success("🎉Item added🎉")
                setData(
                    {
                        name:"",
                        description:"",
                        price:"",
                        categoryId:"",
                        
                    }
                )
                setImage(false)
            }
            else{
                toast.error("Unable to add item👎👎");
            }
        }
        catch(error)
        {
            console.error(error);
            toast.error("Something Went Wrong👎👎👎")
        }
        finally
        {
            setLoading(false);
        }
    }
   return (
<div className="item-form-container" style={{height:'100vh',overflow:'auto',overflowX:'auto'}}>
<div className="mx-2 mt-2">
                <div className="row">
                    <div className="card col-md-12 form-container">
                        <div className="card-body">
                            <form onSubmit={onSubmitHandler}>
                                <div className="mb-3">
                                    <label htmlFor="image" className="form-label">
                                        <img src={image ? URL.createObjectURL(image):assets.upload} alt="" width={48} />
                                    </label>
                                    <input type="file" name="image" id="image" className="form-control" hidden onChange={(e)=>setImage(e.target.files[0])}/>
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="name" className="form-label">
                                        Name
                                    </label>
                                    <input 
                                    type="text" 
                                    name="name" 
                                    id="name" 
                                    className="form-control" 
                                    placeholder="Item Name" 
                                    onChange={onChangeHandler}
                                    value={data.name}
                                    required/>
                                </div>
                                <div className="mb-3">
                                 <label htmlFor="category" className="form-label">Category</label>
                                 <select name="categoryId" id="category" className="form-control"
                                 onChange={onChangeHandler}value={data.categoryId} required>
                                    <option value="">SELECT CATEGORY</option>
                                    {categories.map((category,index)=>(
                                        <option key={index} value ={category.categoryId}>{category.name}</option>
                                    ))}
                                 </select>
                                </div>
                                <div className="mb-3">
                                 <label htmlFor="price" className="form-label">
                                    Price
                                 </label>
                                 <input type="number"
                                  name="price"
                                   id="price" 
                                  placeholder="&#8377;100.00"
                                   className="form-control" 
                                   onChange={onChangeHandler}
                                   value={data.price}
                                   required/>
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="description" className="form-label">
                                        Description
                                    </label>
                                    <textarea 
                                    type="text" 
                                    rows="5"
                                     name="description" 
                                     id="description" 
                                     className="form-control" 
                                     placeholder="Write Description"
                                     onChange={onChangeHandler}
                                     value={data.description}
                                      />
                                </div>
                               
                                <button type="submit"className="btn btn-warning w-100"disabled={loading}>
                                    {loading?"Loading...":"Save"}
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            </div>
               )
}
export default ItemForm;