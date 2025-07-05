import { createContext, useEffect, useState } from "react";
import {fetchCategories} from '../service/CategoryService.js'
import { fetchItems } from "../service/ItemService.js";
// eslint-disable-next-line react-refresh/only-export-components
export const AppContext = createContext(null);

export const AppContextProvider =(props)=>
{
    const [categories,setCategories]=useState([]);
    const [itemsData,setItemsData]= useState([]);
    const [auth,setAuth] = useState({token:null,role:null});
    const [cartItems,setCartItems] = useState([])
    const addToCart =(item) =>
    {
        const existingItem =cartItems.find(cartItems=>cartItems.name === item.name);
        if(existingItem)
        {
            setCartItems(
                (cartItems
                    .map(cartItem=>
                        cartItem.name === item.name?
                        {...cartItem,quantity:cartItem.quantity+1}:cartItem)
                    ));
        }
        else
        {
            setCartItems([...cartItems,{...item,quantity:1}])
        }

    }
    const removeFromCart = (itemId) =>
    {
        setCartItems(
            cartItems.filter(item=>item.itemId!== itemId)
        )
    }
    const updateQuantity = (itemId,newQuantity) =>
    {
        setCartItems(cartItems.map((item)=>item.itemId===itemId ? {...item ,quantity:newQuantity} :item));
    }
    const clearCart = ()=>
    {
        setCartItems([]);
    }
    useEffect(() => {
        async function loadData() {
            if (localStorage.getItem("token") && localStorage.getItem("role")) {
                setAuthData(
                    localStorage.getItem("token"),
                    localStorage.getItem("role")
                );
            }

            if (auth.token) {
                const response = await fetchCategories();
                const itemResponse = await fetchItems();
                setCategories(response.data);
                setItemsData(itemResponse.data);
            }
        }
        loadData();
    }, [auth.token]);

    const setAuthData = (token,role) =>
    {
        setAuth({token,role});
    }
    const contextValue=
    {
        categories,
        setCategories,
        auth,
        setAuthData,
        itemsData,
        setItemsData,
        addToCart,
        cartItems,
        removeFromCart,
        updateQuantity,
        clearCart
    }
    return <AppContext.Provider value={contextValue}>
        {props.children}
    </AppContext.Provider>

}